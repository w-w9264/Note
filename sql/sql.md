#基本sql

* select

* update
>1. 批量更新
>```
>UPDATE [table1]
>   SET name=b.name,sex=b.sex
>   FROM [table1] a INNER JOIN [table2] b ON a.id=b.id
>```

