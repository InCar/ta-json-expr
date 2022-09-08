# Json tree expression for TensorAnalyzor

我们使用Json来表示如何使用数据、如何进行分析运算。

## 数据引用
用户在开始进行分析工作时,需要知道可以使用哪些数据,
因此,我们需要向用户展示一个界面,在这个界面中,用户可以选择用哪些数据进行分析。

[schema-fields.json](./schema-fields.json) 中定义了数据引用的规范。

[sample-fields.json](./sample-fields.json) 展示了一个范例。

+ `dataSources` 数据源, 数据源可以是一个关系型数据库或者是一个非关系型数据库中的表级单元,数据源可以是关系型数据库或mongodb hbase。如果是一个关系型数据库,则这个数据源表示的是数据库中的一张具体的表;如果是mongodb,则表示的是一个`collection`;如果是一个hbase,则表示的是一个`schema`。
  
+ `fields` 字段
  - `data` 表示数据源中的一列数据
  - `desc` 字段的简短描述,会展示在界面上
  - `description` 字段的详细说明,帮助用户理解字段的含义
  

## 数据运算
TODO: 用户表述需要对数据进行怎么样的运算过程


