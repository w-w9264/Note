# Cypher

Cypher是一种图数据库查询语言，表现力丰富，能高效地查询和更新图数据。

[TOC]

## Cypher概述

### Cypher是什么

Cypher是一种图数据库查询语言，表现力丰富，能高效地查询和更新图数据。

### 模式(Patterns)

Neo4j图由节点和关系构成。节点可能还有标签和属性，关系可能还有类型和属性。节点表达的是实体，关系连接一对节点。节点可以看作关系数据库中的表，但又不完全一样。节点的标签可以看作是表名，属性可以看作是表的列。拥有相同标签的节点通常具有类似的属性，但不必完全一样，这与关系数据库中同一张表的行数据拥有相同的列是不一样的。

节点和关系都是图的简单构建块。模式可以将很多节点和关系编码为任意复杂的想法

#### 节点语法

Cypher采用一对圆括号来表示节点

| 表达式                                                | 描述                                                         |
| ----------------------------------------------------- | ------------------------------------------------------------ |
| ()                                                    | 匿名节点                                                     |
| (matrix)                                              | 赋有变量的节点，将匹配到的节点赋值给matrix，这个变量可以在其他地方进行引用，变量的可见范围局限于单个语句 |
| (:Movie)                                              | 匹配标签为Movie的匿名节点                                    |
| (matrix:Movie)                                        | 匹配标签为Movie的节点并赋值给matrix                          |
| (matrix:Movie{title : "The Matrix"})                  | 匹配标签为Movie并且属性title为The Matrix的节点并赋值给matrix |
| (matrix:Movie{title : "The Matrix", released : 1997}) | 匹配标签为Movie并且属性title为The Matrix以及属性released为1997的节点并赋值给matrix |

#### 关系语法

Cypher使用一对短横线(--)表示一个无方向的关系，有方向的关系在其中一段加上一个箭头(-->或者<--),方括号表达式[...]可用于添加详情，里面可以包含变量、属性和类型信息

| 表达式                              | 描述                                                         |
| ----------------------------------- | ------------------------------------------------------------ |
| --                                  | 表示无方向关系                                               |
| -->                                 | 表示一个有方向的关系                                         |
| -[role]->                           | 赋有变量的关系，将匹配到的关系赋值给role，这个变量可以在其他地方进行引用，变量的可见范围局限于单个语句 |
| -[:ACTED_IN]->                      | 匹配类型为ACTED_IN的关系                                     |
| -[role:ACTED_IN]->                  | 匹配类型为ACTED_IN的关系并赋值给role                         |
| -[role:ACTED_IN{roles : ["Neo"]}]-> | 匹配类型为ACTED_IN并且属性roles为["Neo"]的关系并赋值给role   |

#### 模式语法

将节点和关系的语法组合在一起可以表达模式，例如

```cypher
(n:Person:Actor{name:"孙红雷"})-[r:ACTED_IN]->(b:Movie{title:"好先生"})
```

#### 模式变量

为了增强模块性和减少重复，Cypher允许将模式赋给一个变量，这使得匹配到的路径可以用于其他表达式，例如

```cypher
n = (:Persion)-[:ACTED_IN]->(:Movie)
```

### 查询和更新图

Cypher既可用于查询又可用于更新图数据

#### 更新语句的结构

一个Cypher查询部分不能同时匹配和更新图数据，每个部分要么读取要么更新它。在更新查询语句中，所有的读取操作必须在任何的写操作发生之前完成。

#### 返回数据

任何查询都可以返回数据。return语句有三个子语句，分别微skip、limit和order by。如果返回的图元素是刚刚删除的数据，需要注意的是这时的数据的指针不在有效，针对它们的任何操作都是未定义的

### 事务

任何更新图的查询都运行在一个事务中。一个更新查询要么全部成功，要么全部失败。Cypher或者创建一个新的事务，或者运行一个已有的事务中。

### 唯一性

当进行模式匹配时，Neo4j将确保单个模式中不会包含匹配到多次的同一个图关系。例如查找一个用户的朋友的朋友时不应该返回该用户。

当然有时又未必一直希望这样，如果查询应该返回该用户，可以通过多个match语句延申匹配关系来实现。

### 兼容性

Cypher不是一尘不变的语言。新版本引入了很多新的功能，一些旧的功能可能会被移除。如果需要的话，旧版本依然可以访问得到。有两种方式可以在查询中选择使用哪个版本：

1. 为所有的查询设置版本：可以通过neo4j.conf中cypher.default_language_version参数来配置Neo4j数据库使用哪个版本的Cypher语言
2. 在查询中指定版本：简单的在查询开始的时候写上版本，如Cypher 2.3.

## 基本语法

### 类型

Cypher处理的所有值都有一个特定的类型。支持的类型有：数值型、字符串、布尔型、节点、关系、路径、映射(Map)、列表(List)。null是任何类型的值

### 表达式

#### 概述

Cypher中的表达式如下：

> 十进制(整型和双精度型)的字面值：13、-13333、3.14、6.022E34
>
> 十六进制整型字面值(以0x开头)：0x13zf、0xFC9A0、-0x66eff
>
> 八进制整型字面值(以0开头)：0223、06546、-023432
>
> 字符串字面值：'Hello'、"World"
>
> 布尔字面值：true、false、TRUE、FALSE
>
> 变量：n、x、rel、'a friend'
>
> 属性：n.prop、x.prop、rel.thisProperty、'a friend'.'(property name)'
>
> 动态属性：n["prop"]、rel[n.city + n.zip]、map[coll[0]]
>
> 参数：$param、$0
>
> 表达式列表：['a','b']、[1,2,3]、['a',3,n.property,$param]、[]
>
> 函数调用：length(p)、nodes(p)
>
> 聚合函数：avg(x.prop)、count(*)
>
> 路径-模式：(a)-->()<--(b)
>
> 计算式：1=2 and 3<4
>
> 返回true或者false的断言表达式：a.prop = 'Hello'、length(p)>10
>
> 正则表达式：a.name=~'Tob.*'
>
> 大小写敏感的字符串匹配表达式：a.surname starts with 'sven'、a.surname ends with 'son' or a.surname contains 'son'
>
> case 表达式

#### 转义字符

| 字符       | 含义                                                  |
| ---------- | ----------------------------------------------------- |
| \t         | 制表符                                                |
| \b         | 退格                                                  |
| \n         | 换行                                                  |
| \r         | 回车                                                  |
| \f         | 换页                                                  |
| \\'        | 单引号                                                |
| \\"        | 双引号                                                |
| \\\        | 反斜杠                                                |
| \uxxxx     | Unicode UTF-16编码点(4位的十六进制数字必须以"\u"开头) |
| \uxxxxxxxx | Unicode UTF-32编码点(8位的十六进制数字必须以"\u"开头) |

#### case表达式

1. 简单的case表达式

   计算表达式的值，然后依次与when语句中的表达式进行比较，直到匹配上为止。如果未匹配上，则else中的表达式将作为结果。如果else语句不存在，那么将返回null。

   语法：

   >**case** test
   >
   >**when** value **then** result
   >
   >[**when** ...]
   >
   >[**else** default]
   >
   >**end**

   test:一个有效的表达式

   value:一个表达式，他的结果将与test表达式的结果进行比较

   result:如果value表达式能够与test表达式匹配，他将作为结果表达式

   default:没有匹配的情况下的默认返回式

2. 一般的case表达式

   按顺序判断断言，直到找到一个为true，然后对应的结果被返回。如果没有找到就返回else的值，如果没有else语句则返回null

   语法：

   > **case**
   >
   > **when** predicate **then** result
   >
   > [**when** ...]
   >
   > [**else** default]
   >
   > **end**

   predicate:判断的断言，已找到一个有效的可选项

   result:如果predicate匹配到，result将作为结果表达式

   default:没有匹配的情况下的默认返回表达式

### 变量

当需要引用模式(Pattern)或者查询某一部分的时候，可以对其进行命名。支队不同部分的这些命名被称为变量

### 参数



### 运算符

### 注释

### 模式(Patterns)

### 列表

### 空值

## 语句

### MATCH

### OPTINAL MATCH

### WHERE

### START

### AGGREGATION

### LOAD CSV

### CREATE

### MERGE

### SET

### DELETE

### REMOVE

### FOREACH

### CREATE UNIQUE

### RETURN

### ORDER BY

### LIMIT

### SKIP

### WITH

### UNWIND

### UNION

### CALL

call语句用于调用数据库中的过程(Procedure)

```cypher
call dbms.procedures	//查询所有过程
call db.labels	//查询所有标签
call db.propertyKeys	//查询所有属性键
call db.relationshipTypes	//查询所有关系类型
```

### YIELD

yield字句用于显式地选择返回结果集中的那些部分并绑定到一个变量以供后续查询使用

```cypher
call db.labels() yield label return count(label) as numLabels	//查询所有标签数量
call db.propertyKeys() yield propertyKey as prop match (n) where n[prop] is not null return prop,count(n) as numNodes	//查询数据库中包含所有属性键的节点数
```



## 函数

### Predicate(断言函数)

#### all()

判断是否一个断言适用于列表中的所有函数

语法：**all(variable in list where predicate)**

list:返回列表的表达式

variable:用于断言的变量

predicate:用于测试列表中所有元素的断言

```cypher
match p = (a)-[*1..3]->(b) where a.name = 'Alice' and b.name = 'Daniel' and all(x in nodes(p) where x.age > 30) return p	//返回路径中的所有节点都有一个至少大于30的age属性
```

#### any()

判断是否一个断言至少适用于列表中的一个元素

语法：**any(variable in list where predicate)**

list:返回列表的表达式

variable:用于断言的变量

predicate:用于测试列表中所有元素的断言

```cypher
match (a) where a.name = 'Eskill' and any(x in a.array where x = 'one') return a	//返回路径中的所有节点的array数组属性中至少有一个值为'one'
```

#### none()

如果断言不适用于列表中的任何元素，则返回true

语法：**none(variable in list where predicate)**

list:返回列表的表达式

variable:用于断言的变量

predicate:用于测试列表中所有元素的断言

```cypher
match p = (a)-[*1..3]->(b) where a.name = 'Alice' and none(x in nodes(p) where x.age = 25) return p	//返回路径中没有节点的age属性为25
```

#### single()

如果断言刚好只适用于列表中的某一个元素则返回true

语法：**single(variable in list where predicate)**

list:返回列表的表达式

variable:用于断言的变量

predicate:用于测试列表中所有元素的断言

```cypher
match p = (a)-[*1..3]->(b) where a.name = 'Alice' and single(x in nodes(p) where x.eyes = 'blue') return p	//返回路径中刚好只用一个节点的eyes属性为blue
```

#### exists()

如果数据库中存在该模式或者节点中存在该属性时，返回true

语法：**exists(pattern-or-property)**

pattern-or-property:模式或者属性

```cypher
match (n) where exists(n.name) return n.name as name, exists((n)-[:MARRIED]->()) as is_married	//查询所有节点的name属性和指示是否已婚的true/false值
```

### Scalar(标量函数)

#### size()

1. 使用size()返回列表中元素的个数

   语法：**size(list)**

   list:返回列表的表达式

2. size的参数不是一个列表而是一个模式匹配到的查询结果集，计算的是结果集元素的个数，而不是表达式本身的长度。

   语法：**size(pattern expression)**

   pattern expression:返回列表的模式表达式

#### length()

1. 使用length()返回路径的长度

   语法：**length(path)**

   path:返回路径的表达式

2. 返回字符串的长度

   语法：**length(string)**

   string:返回字符串的表达式

#### type()

返回字符串代表的关系类型

语法：**type(relationship)**

relationship:一个关系

#### id()

返回节点或者关系的id

语法：**id(property-container)**

property-container:一个节点或者关系

#### coalesce()

返回表达式列表中的第一个非空的值，如果所有的实参都为空，则返回null

语法：**coalesce(expression[,expression]*)**

expression:表达式，可能返回null

```cypher
match (a) where a.name = 'Alice' return coalesce(a.ahircoler, a.eyes)	
```

#### head()

返回列表中的第一个元素

语法：**head(expression)**

expression:返回列表的表达式

#### last()

返回列表中的第一个元素

语法：**last(expression)**

expression:返回列表的表达式

#### timestamp()

返回当前时间与1970-1-1午夜之间的差值，即时间戳

语法：**timestamp()**

#### startNode()

返回一个关系的开始节点

语法：**startNode(relationship)**

relationship:一个关系

#### endNode()

返回一个关系的开始节点

语法：**endNode(relationship)**

relationship:一个关系

#### properties()

将实参转为属性值的map。如果实参是一个节点或者关系，返回的就是节点或者关系的属性的map。如果实参已经是一个map了，则原样返回

语法：**properties(expression)**

expression:返回节点、关系或者map的表达式

#### toInt()

将实参转换为一个整数，字符串会被解析为一个整数，如果解析失败，将返回null，浮点数将被强制转换为整数。

语法：**toInt(expression)**

expression:返回任意值的表达式

#### toFloat()

将实参转换为一个浮点数，字符串会被解析为一个点数，如果解析失败，将返回null，整数将被强制转换为点数。

语法：**toFloat(expression)**

expression:返回任意值的表达式

### List(列表函数)

#### nodes()

返回一条路径中的所有节点

语法：nodes(path)

path:一条路径

#### relationships()

返回一条路径中的所有关系

语法：relationships(path)

path:一条路径

#### labels()

以字符串列表的形式返回一个节点的所有标签

语法：labels(node)

node:返回单个节点的任意表达式

#### keys()

以字符串列表的形式返回一个节点、关系或者map的所有属性的名称

语法：keys(property-container)

property-container:一个节点、关系或者字面值的map

#### extract()

从节点或者关系列表中返回单个属性或者某个函数的值，他将遍历整个列表，针对列表中的每一个元素运行一个表达式，然后以列表的形式返回这些结果。他的工作方式类似于Lisp和Scala等函数式语言中的map方法

语法：extract(variable in list | expression)

list:返回列表的表达式

variable:引用list中的元素的变量，他在expression中会用到

expression:针对列表中的每一个元素所运行的表达式，并产生一个结果列表。

```cypher
match p = (a)-->(b)-->(c) where a.name = 'Alice' and b.name = 'Daniel' return extract(n in nodes(p) | n.age) as extracted	//返回了路径中所有节点的age属性
```

#### filter()

返回列表中的满足断言要求的所有元素

语法：filter(variable in list where predicate)

list:返回列表的表达式

variable:断言中引用列表元素所用到的变量

predicate:针对列表中的每一个元素进行测试的断言

```cypher
match (a) where a.name = 'Eskill' return a.array, filter(x in a.array where size(x) = 3)	//返回array属性，及其元素的字符数为3的元素列表
```

#### tail()

返回列表中除首元素之外的所有元素

语法：tail(expression)

expression:返回某个类型列表的表达式

#### range()

返回某个范围之内的数值，值之间的步长默认为1，范围包含起始边界值，即集合为全毕

语法：range(start,end [,step])

start:起始数值的表达式

end:结束数值的表达式

step:数值间隔的步长

```cypher
return range(0, 10), range(2, 18, 3)	//第一个返回0到10之间步长为1的所有值，第二个返回从2到18之间的步长为3的所有值。
```

```
结果：
range(0, 10)
[0,1,2,3,4,5,6,7,8,9,10]
range(2, 18, 3)
[2,5,8,11,14,17]
```

#### reduce()

对列表中的每一个元素执行一个表达式，将表达式的结果存入一个累加器。他的工作机制类似Lisp和Scala等函数式语言中的fold或者reduce方法

语法：reduce(accumulator = initial, variable in list | expression)

accumulator:用于累加每次迭代的部分结果

initial:累加器的初始值

list:返回列表的表达式

variable:引用列表中的每个元素的变量

expression:针对列表中每个元素执行的表达式

```cypher
match p = (a)-->(b)-->(c) where a.name = 'Alice' and b.name = 'Bob' and c.name = 'Daniel' return reduce(totalAge = 0, n in nodes(p) | totalAge + n.age) as reduction	//将路径中每一个节点的age数值加起来，然后返回一个单值
```

### 数学函数

### 字符串函数

### 自定义函数

## 模式(Schema)

### 索引

### 约束

### 统计

## 查询调优

### 查询如何执行

### 查询性能分析

### 查询调优举例

### USING

## 执行计划

### 开始点运算符

### Expand运算符

### 组合运算符

### 行运算符

### 更新运算符

### 最短路径规划

