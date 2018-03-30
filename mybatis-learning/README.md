## First Day
1. Mapper在spring管理下其实是单例，为什么可以是一个
单例？ SCOPE -> application     
**???不知道**
2. MyBatis在Spring集成下没有mapper的xml文件会不会
报错，为什么？      
**可以不写，因为Mybatis支持annotation和xml两种方式，选其一就行**
3. TypeHandler手写      
**已完成，见代码，并且一旦重写某种类型，会全局生效，同一类型后者覆盖前者**
4. 手写Plugin,多个interceptor到底谁先执行？顺序由谁
决定的？      
**暂时测试发现的是后注册的先执行**

## 找源码解决3，4的疑惑
