## 1
1. Mapper在spring管理下其实是单例，为什么可以是一个
单例？ SCOPE -> application     
**因为Mapper的主要作用是为了找到sql，其方法由MapperProxy执行**
2. MyBatis在Spring集成下没有mapper的xml文件会不会
报错，为什么？      
**可以不写，因为Mybatis支持annotation和xml两种方式，选其一就行**
3. TypeHandler手写      
**已完成，见代码，并且一旦重写某种类型，会全局生效，同一类型后者覆盖前者**
4. 手写Plugin,多个interceptor到底谁先执行？顺序由谁
决定的？      
**暂时测试发现的是后注册的先执行，注册过程是将其放到数组中的，按理遍历执行的话应该也是顺序取出，未找到倒叙执行的原因**

## 2
1. 怎么验证一级缓存的存在？
2. 验证N+1问题
## 3
1. org.apache.ibatis.binding.MapperProxy#invoke 这个类的53行什么时候执行？
## 4
1. TestMapper 作者为什么要设计这样的形式来做？
为什么不是一个class而是一个interface?
**因为Mapper的作用在于找到对应的sql，而非方法调用**
2. org.apache.ibatis.executor.BaseExecutor#queryFromDatabase 322行这行代码的意义
**并不清楚**
3. MyBatis的plugin实现机制
**操作数据库的是代理对象，在执行查询前，先找到plugin列表，依次执行之后，才会去执行数据库操作**
4. lazy loading 是怎么做到的？
## 5
1. 手写1.0
**见felixu-mybatis这个module的top.felixu.v1**
2. 2.0版本的基础上，用annotation
**见felixu-mybatis这个module的top.felixu.v2**