## 定义
Specify the kinds of objects to create using a prototypical instance,and create new objects by copying this prototype.（用原型实例指定创建对象的种类，并且通过拷贝这些原型创建新的对象。）
其实就是对象clone
## 复制对象和复制引用
对象是存储在堆中的，而引用是存储在栈中的，引用通过地址找到真实的对象
复制引用只是将对象的地址赋值给一个新的变量，完成引用的复制
而复制对象是需要在堆中重新分配一块与目标对象一模一样大小的空间，将其地址赋值给新的变量
## 浅拷贝
clone方法实现的是对对象的浅拷贝，目标对象中的引用变量并没有被拷贝，只是将引用复制过来了
## 深拷贝
实现深拷贝，需要目标对象在实现clone方法的时候，同时复制其引用型变量
这也要求其引用型变量必须实现Cloneable接口，同时实现clone方法
所以实际中，我们很难真正的实现深拷贝