## Mybatis编程式工程
### 手写了MyTypeHandler    
发现只要TypeHandler被注册，并且加了@MappedJdbcTypes注解，那么会对所有的ResultMap生效     
James老师之前说必须显示的在ResultMap里面指定，与本地测试略有不同     
后查官网发现这么一句话      
>  Since Mybatis 3.4.0 however, if a single TypeHandler is registered to handle a Java type, it will be used by default in ResultMaps using this Java type (i.e. even without includeNullJdbcType=true).      

即从3.4.0之后开始，如果只注册了一个TypeHandler，那么将默认对所有ResultMap生效。   
奇怪的是，在mybatis-config.xml文件中，后面被注册的都会是被默认使用的，这样不会很绝望吗？？？    
 
### 手写Plugin
切点有范围，所以不适合做log   
多个interceptor注册的时候，按注册顺序倒叙加载，都会执行。(好像跟James说的不一样)