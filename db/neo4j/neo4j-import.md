# neo4j数据导入

[TOC]

## neo4j-import

```powershell
root@77e175efb552:/var/lib/neo4j/bin# ./neo4j-import --help
WARNING: neo4j-import is deprecated and support for it will be removed in a future
version of Neo4j; please use neo4j-admin import instead.
Neo4j Import Tool
        neo4j-import is used to create a new Neo4j database from data in CSV files. See
        the chapter "Import Tool" in the Neo4j Manual for details on the CSV file format
        - a special kind of header is required.
Usage:
--f <file name>
        File containing all arguments, used as an alternative to supplying all arguments
        on the command line directly.Each argument can be on a separate line or multiple
        arguments per line separated by space.Arguments containing spaces needs to be
        quoted.Supplying other arguments in addition to this file argument is not
        supported.
--into <store-dir>
        Database directory to import into. Must not contain existing database.
--database <database-name>
        Database name to import into. Must not contain existing database.
--nodes[:Label1:Label2] "<file1>,<file2>,..."
        Node CSV header and data. Multiple files will be logically seen as one big file
        from the perspective of the importer. The first line must contain the header.
        Multiple data sources like these can be specified in one import, where each data
        source has its own header. Note that file groups must be enclosed in quotation
        marks. Each file can be a regular expression and will then include all matching
        files. The file matching is done with number awareness such that e.g.
        files:'File1Part_001.csv', 'File12Part_003' will be ordered in that order for a
        pattern like: 'File.*'
--relationships[:RELATIONSHIP_TYPE] "<file1>,<file2>,..."
        Relationship CSV header and data. Multiple files will be logically seen as one
        big file from the perspective of the importer. The first line must contain the
        header. Multiple data sources like these can be specified in one import, where
        each data source has its own header. Note that file groups must be enclosed in
        quotation marks. Each file can be a regular expression and will then include all
        matching files. The file matching is done with number awareness such that e.g.
        files:'File1Part_001.csv', 'File12Part_003' will be ordered in that order for a
        pattern like: 'File.*'
--delimiter <delimiter-character>
        Delimiter character, or 'TAB', between values in CSV data. The default option is
        ,.
--array-delimiter <array-delimiter-character>
        Delimiter character, or 'TAB', between array elements within a value in CSV
        data. The default option is ;.
--quote <quotation-character>
        Character to treat as quotation character for values in CSV data. The default
        option is ". Quotes inside quotes escaped like """Go away"", he said." and "\"Go
        away\", he said." are supported. If you have set "'" to be used as the quotation
        character, you could write the previous example like this instead: '"Go away",
        he said.'
--multiline-fields <true/false>
        Whether or not fields from input source can span multiple lines, i.e. contain
        newline characters. Default value: false
--trim-strings <true/false>
        Whether or not strings should be trimmed for whitespaces. Default value: false
--input-encoding <character set>
        Character set that input data is encoded in. Provided value must be one out of
        the available character sets in the JVM, as provided by
        Charset#availableCharsets(). If no input encoding is provided, the default
        character set of the JVM will be used.
--ignore-empty-strings <true/false>
        Whether or not empty string fields, i.e. "" from input source are ignored, i.e.
        treated as null. Default value: false
--id-type <id-type>
        One out of [STRING, INTEGER, ACTUAL] and specifies how ids in node/relationship
        input files are treated.
        STRING: arbitrary strings for identifying nodes.
        INTEGER: arbitrary integer values for identifying nodes.
        ACTUAL: (advanced) actual node ids. The default option is STRING. Default value:
        STRING
--processors <max processor count>
        (advanced) Max number of processors used by the importer. Defaults to the number
        of available processors reported by the JVM (in your case 2). There is a certain
        amount of minimum threads needed so for that reason there is no lower bound for
        this value. For optimal performance this value shouldn't be greater than the
        number of available processors.
--stacktrace <true/false>
        Enable printing of error stack traces. Default value: false
--bad-tolerance <max number of bad entries, or true for unlimited>
        Number of bad entries before the import is considered failed. This tolerance
        threshold is about relationships referring to missing nodes. Format errors in
        input data are still treated as errors. Default value: 1000
--skip-bad-entries-logging <true/false>
        Whether or not to skip logging bad entries detected during import. Default
        value: false
--skip-bad-relationships <true/false>
        Whether or not to skip importing relationships that refers to missing node ids,
        i.e. either start or end node id/group referring to node that wasn't specified
        by the node input data. Skipped nodes will be logged, containing at most number
        of entities specified by bad-tolerance, unless otherwise specified by
        skip-bad-entries-logging option. Default value: true
--skip-duplicate-nodes <true/false>
        Whether or not to skip importing nodes that have the same id/group. In the event
        of multiple nodes within the same group having the same id, the first
        encountered will be imported whereas consecutive such nodes will be skipped.
        Skipped nodes will be logged, containing at most number of entities specified by
        bad-tolerance, unless otherwise specified by skip-bad-entries-loggingoption.
        Default value: false
--ignore-extra-columns <true/false>
        Whether or not to ignore extra columns in the data not specified by the header.
        Skipped columns will be logged, containing at most number of entities specified
        by bad-tolerance, unless otherwise specified by skip-bad-entries-loggingoption.
        Default value: false
--db-config <path/to/neo4j.conf>
        (advanced) Option is deprecated and replaced by 'additional-config'.
--additional-config <path/to/neo4j.conf>
        (advanced) File specifying database-specific configuration. For more information
        consult manual about available configuration options for a neo4j configuration
        file. Only configuration affecting store at time of creation will be read.
        Examples of supported config are:
        dbms.relationship_grouping_threshold
        unsupported.dbms.block_size.strings
        unsupported.dbms.block_size.array_properties
--legacy-style-quoting <true/false>
        Whether or not backslash-escaped quote e.g. \" is interpreted as inner quote.
        Default value: true
--read-buffer-size <bytes, e.g. 10k, 4M>
        Size of each buffer for reading input data. It has to at least be large enough
        to hold the biggest single value in the input data. Default value: 4194304
--max-memory <max memory that importer can use>
        (advanced) Maximum memory that importer can use for various data structures and
        caching to improve performance. If left as unspecified (null) it is set to 90%
        of (free memory on machine - max JVM memory). Values can be plain numbers, like
        10000000 or e.g. 20G for 20 gigabyte, or even e.g. 70%.
--cache-on-heap Whether or not to allow allocating memory for the cache on heap
        (advanced) Whether or not to allow allocating memory for the cache on heap. If
        'false' then caches will still be allocated off-heap, but the additional free
        memory inside the JVM will not be allocated for the caches. This to be able to
        have better control over the heap memory. Default value: false
--high-io Assume a high-throughput storage subsystem
        (advanced) Ignore environment-based heuristics, and assume that the target
        storage subsystem can support parallel IO with high throughput.
--detailed-progress true/false
        Use the old detailed 'spectrum' progress printing. Default value: false
Example:
        bin/neo4j-import --into retail.db --id-type string --nodes:Customer customers.csv
        --nodes products.csv --nodes orders_header.csv,orders1.csv,orders2.csv
        --relationships:CONTAINS order_details.csv
        --relationships:ORDERED customer_orders_header.csv,orders1.csv,orders2.csv
root@77e175efb552:/var/lib/neo4j/bin#
```

```
root @ 77e175efb552：/ var / lib / neo4j / bin＃。/ neo4j-import --help
警告：不推荐使用neo4j-import，将来会删除对它的支持
Neo4j的版本;请改用neo4j-admin import。
Neo4j导入工具
        neo4j-import用于从CSV文件中的数据创建新的Neo4j数据库。看到
        有关CSV文件格式的详细信息，请参阅Neo4j手册中的“导入工具”一章
         - 需要一种特殊的标题。
用法：
--f <文件名>
        包含所有参数的文件，用作提供所有参数的替代方法
        直接在命令行上。每个参数可以在一个单独的行或多个行上
        每行由空格分隔的参数。包含空格的参数需要
        除了这个文件参数之外，还提供其他参数
        支持的。
--into <store-dir>
        要导入的数据库目录。不得包含现有数据库。
--database <database-name>
        要导入的数据库名称。不得包含现有数据库。
--nodes [：Label1：Label2]“<file1>，<file2>，......”
        节点CSV标头和数据。多个文件在逻辑上被视为一个大文件
        从进口商的角度来看。第一行必须包含标题。
        可以在一个导入中指定多个这样的数据源，其中包含每个数据
        source有自己的标题。请注意，文件组必须用引号括起来
        分数。每个文件都可以是正则表达式，然后包含所有匹配项
        文件。文件匹配是通过数字识别完成的，例如，
        文件：'File1Part_001.csv'，'File12Part_003'将按此顺序订购
        模式如：'文件。*'
--relationships [：RELATIONSHIP_TYPE]“<file1>，<file2>，......”
        关系CSV标头和数据。多个文件在逻辑上被视为一个
        从进口商的角度来看大文件。第一行必须包含
        头。像这样的多个数据源可以在一个导入中指定，其中
        每个数据源都有自己的标头。请注意，必须包含文件组
        引号。每个文件都可以是正则表达式，然后包含所有文件
        匹配文件。文件匹配是通过数字识别完成的，例如，
        文件：'File1Part_001.csv'，'File12Part_003'将按此顺序订购
        模式如：'文件。*'
--delimiter <delimiter-character>
        CSV数据中值之间的分隔符或“TAB”。默认选项是
        。
--array-delimiter <array-delimiter-character>
        CSV中值中数组元素之间的分隔符或“TAB”
        数据。默认选项是;。
--quote <quotation-character>
        将字符视为CSV数据中值的引号字符。默认
        选项是“。引用内部报价逃脱像”“走开”，“他说。”和“\”去
        离开了，“他说。”得到支持。如果您已将“'”设置为用作引号
        你可以用这样的方式写下前面的例子：'“走开”，
        他说。'
--multiline-fields <true / false>
        输入源中的字段是否可以跨越多行，即包含
        换行符。默认值：false
--trim-strings <true / false>
        是否应该为空格修剪字符串。默认值：false
--input-encoding <字符集>
        输入数据编码的字符集。提供的值必须是一个
        JVM中可用的字符集，由提供
        字符集＃availableCharsets（）。如果未提供输入编码，则为默认值
        将使用JVM的字符集。
--ignore-empty-strings <true / false>
        是否忽略空字符串字段，即来自输入源的“”，即
        视为null。默认值：false
--id-type <id-type>
        [STRING，INTEGER，ACTUAL]中的一个，指定节点/关系中的ID
        输入文件被处理。
        STRING：用于标识节点的任意字符串。
        INTEGER：用于标识节点的任意整数值。
        ACTUAL :(高级）实际节点ID。默认选项为STRING。默认值：
        串
 - 处理器<max processor count>
        （高级）导入程序使用的最大处理器数。默认为数字
        JVM报告的可用处理器数量（在您的情况下为2）。有一定的
        所需的最小线程数量，因此没有下限
        这个价值。为获得最佳性能，此值不应大于
        可用处理器数量。
--stacktrace <true / false>
        启用错误堆栈跟踪的打印。默认值：false
--bad-tolerance <最大错误条目数，或对于无限制条件为真>
        导入前的错误条目数被视为失败。这种宽容
        阈值是关于缺少节点的关系。格式错误
        输入数据仍被视为错误。默认值：1000
--skip-bad-entries-logging <true / false>
        是否跳过导入导入期间检测到的错误条目。默认
        值：false
--skip-bad-relationships <true / false>
        是否跳过导入缺少节点ID的关系，
        即，开始或结束节点id / group引用未指定的节点
        由节点输入数据。将记录跳过的节点，最多包含数字
        除非另有说明，否则由不良容限指定的实体
        skip-bad-entries-logging选项。默认值：true
--skip-duplicate-nodes <true / false>
        是否跳过导入具有相同id / group的节点。在这种情况下
        具有相同id的同一组内的多个节点，第一个
        将导入遇到的，而将跳过连续的此类节点。
        将记录跳过的节点，其中包含最多指定的实体数
        除非skip-bad-entries-loggingoption另有规定，否则容错。
        默认值：false
--ignore-extra-columns <true / false>
        是否忽略标头未指定的数据中的额外列。
        将记录跳过的列，其中包含最多指定的实体数
        除非由skip-bad-entries-loggingoption指定，否则通过容错。
        默认值：false
--db-config <path / to / neo4j.conf>
        （高级）选项已弃用，并替换为“additional-config”。
--additional-config <path / to / neo4j.conf>
        （高级）指定特定于数据库的配置的文件。欲获得更多信息
        有关neo4j配置的可用配置选项，请参阅手册
        文件。仅读取在创建时影响商店的配置。
        支持的配置示例如下：
        dbms.relationship_grouping_threshold
        unsupported.dbms.block_size.strings
        unsupported.dbms.block_size.array_properties
--legacy-style-quoting <true / false>
        是否反斜杠转义引用，例如\“被解释为内部引用。
        默认值：true
--read-buffer-size <bytes，例如10k，4M>
        用于读取输入数据的每个缓冲区的大小。它必须至少足够大
        保持输入数据中最大的单个值。默认值：4194304
--max-memory <导入程序可以使用的最大内存>
        （高级）导入程序可用于各种数据结构的最大内存
        缓存以提高性能。如果保留为未指定（null），则设置为90％
        （机器上的可用内存 - 最大JVM内存）。值可以是普通数字，例如
        10000000或者例如20G，20G，甚至20G 70％。
--cache-on-heap是否允许在堆上为缓存分配内存
        （高级）是否允许在堆上为缓存分配内存。如果
        'false'然后缓存仍然会在堆外分配，但额外的免费
        JVM中的内存不会被分配给缓存。这样就可以了
        可以更好地控制堆内存。默认值：false
--high-io假设一个高吞吐量的存储子系统
        （高级）忽略基于环境的启发式，并假设目标
        存储子系统可以支持高吞吐量的并行IO。
--detailed-progress true / false
        使用旧的详细“频谱”进度打印。默认值：false
例：
        bin / neo4j-import --into retail.db --id-type string --nodes：Customer customers.csv
        --nodes products.csv --nodes orders_header.csv，orders1.csv，orders2.csv
        --relationships：CONTAINS order_details.csv
        --relationships：ORDERED customer_orders_header.csv，orders1.csv，orders2.csv
```

