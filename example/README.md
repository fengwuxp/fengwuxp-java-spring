#### 整体思路

- 作用：负责管理java 对象，创建、销毁
- 创建对象
- 对象依赖：谁先创建

- class byte code jvm 规定的格式 ==> 运行时解释 ==>JIT =>机器码

- CPU 执行机器码
- Java是一门跨平台的语言
- @PostContr
- jvm是一个跨语言的平台 class
- 循环依赖
- BeanFactory FactoryBean A ->B\C==new A(null,C)
  B-> A @Lazy --> A --> 单例

- scale
- js py --> class
- class loader --> 区分一个类是不是唯一的 全类限定名是不能冲突的
- 双亲委派
- 写了自定义的类加载器

- 包扫描 -> Bean定义 --> 增加BeanFactory--> BeanFactory -> 增强Bean --> getBean -->实例

- Bean 作用范围
- Spring Web--> web 容器
- Spring 容器


- 控制器、filter 拦截器 都在web容器里面， Service 在父容器
- 子容器可以访问父容器的对象

- 类似类加载的双亲委派 -->父级
