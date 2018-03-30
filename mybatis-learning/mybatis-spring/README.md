## Spring和Mybatis整合的项目
### 手写TypeHandler
测试结果与编程式相同，均为同一种JdbcType时，后注册的覆盖前者
### 手写Plugin
也是后注册的先执行。。。