# Module描述
**felixu-mybatis**是自己手动实现V1、V2、V3模块
**generator**是mybatis代码自动生成模块
**jdbc**是原生jdbc连接数据库操作
**mybatis**为mybatis编程式操作
**mybatis-spring**是与Spring集成使用
# 问题
## 1
1. Mapper在spring管理下其实是单例，为什么可以是一个
单例？ SCOPE -> application     
**其实就是问为什么生命周期发生了变化，本身是session级别，而在Spring中是application级别。因为Mapper的主要作用是为了找到sql**
2. MyBatis在Spring集成下没有mapper的xml文件会不会
报错，为什么？      
**可以不写，因为Mybatis支持annotation和xml两种方式，选其一就行**
3. TypeHandler手写      
**已完成，见代码，并且一旦重写某种类型，会全局生效，同一类型后者覆盖前者**
4. 手写Plugin,多个interceptor到底谁先执行？顺序由谁
决定的？      
**暂时测试发现的是后注册的先执行，注册过程是将其放到数组中的，按理遍历执行的话应该也是顺序取出，未找到倒叙执行的原因(后发现其实是产生MapperProxy的时候层层代理的，所以是倒序)**

## 2
1. 怎么验证一级缓存的存在？
2. 验证N+1问题

## 3
1. org.apache.ibatis.binding.MapperProxy#invoke 这个类的53行什么时候执行？
**默认方法调用**

## 4
1. TestMapper 作者为什么要设计这样的形式来做？
为什么不是一个class而是一个interface?
**因为Mapper的作用在于找到对应的sql，而非方法调用**
2. org.apache.ibatis.executor.BaseExecutor#queryFromDatabase 322行这行代码的意义
**并不清楚(占位符，用于懒加载)**
3. MyBatis的plugin实现机制
**通过对MapperProxy做了层层代理**
4. lazy loading 是怎么做到的？
**代理**
## 5       
1. 手写1.0
**见felixu-mybatis这个module的top.felixu.v1**
2. 2.0版本的基础上，用annotation
**见felixu-mybatis这个module的top.felixu.v2**
3. 3.0版本，增加plugin
**见felixu-mybatis这个module的top.felixu.v3**