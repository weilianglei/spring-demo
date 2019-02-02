# Spring探索（二）--配置Bean
Bean是组成项目的基础组件，在[Spring探索（一）-- 基于控制反转的容器（IoC）](https://www.jianshu.com/p/14f8e329bd03)一文中简单介绍了基于XML的bean的注册。Spring提供了多种途径的bean注册，包括`基于XML配置`、`基于Java配置`、`基于注解配置`以及`自动配置（只适用于SpringBoot）`。

本文将以下两个方面展开介绍：
- 配置方式的介绍及实例
- 组合使用不同配置方式

> 本文相关代码参看[GitHub](https://github.com/weilianglei/spring-demo/tree/config_bean)

## Bean的配置方式
为了方便演示，我们通过创建一个**班级**服务来比较不同配置方式之间的区别，`ClassMode`表示班级实例，`IClassDao`和`IClassService`分别是数据层和服务层的接口。

```java
public class ClassModel {
    private long id;
    private String name;
    // 省略getter setter toString等方法
}

public interface IClassDao {
    ClassModel getById(long id);
}

public interface IClassService {
    ClassModel getById(long id);
}
```

### 基于XML配置

首先创建基于XML的班级服务所需要的类`XmlBasedClassDao`和`XmlBasedClassService`。

```java
public class XmlBasedClassDaoImpl implements IClassDao {
    @Override
    public ClassModel getById(long id) {
        return new ClassModel(1, "XmlBasedClassDaoImpl");
    }
}

public class XmlBasedConstructBasedClassServiceImpl implements IClassService {
    private IClassDao classDao;

    @Override
    public ClassModel getById(long id) {
        return classDao.getById(id);
    }

}

public class XmlBasedPropertyBasedClassServiceImpl implements IClassDao {
    private IClassDao classDao;

    @Override
    public ClassModel getById(long id) {
        return classDao.getById(id);
    }
}
```
配置的步骤如下：
1. 将`XmlBasedClassDaoImpl`、`XmlBasedConstructBasedClassServiceImpl`和`XmlBasedPropertyBasedClassServiceImpl`注册为IoC容器中的bean
2. 将`XmlBasedClassDaoImpl`注入到`XmlBasedConstructBasedClassServiceImpl`和`XmlBasedPropertyBasedClassServiceImpl`中

#### XML文件中注册Bean
在项目的`applications.xml`文件中添加如下内容

```xml
<bean id="xmlBasedClassDaoImpl" class="com.alex.springdemo.dao.impl.XmlBasedClassDaoImpl"></bean>
<bean id="xmlBasedConstructBasedClassService" class="com.alex.springdemo.service.impl.XmlBasedConstructBasedClassServiceImpl"></bean>
<bean id="xmlBasedPropertyBasedClassService" class="com.alex.springdemo.service.impl.XmlBasedPropertyBasedClassServiceImpl"></bean>
```
其中`id`指bean的标志，class是bean对应的对象的类型（类全名）

#### Bean的注入
bean的注入有如下三种方式：
- 构造函数
- 属性
- 静态工厂方法（不常见，不做介绍本文）

1. 基于构造函数的注入
首先需要在类中定义一个该类型的变量，然后将该变量通过构造函数赋值，最后在`applications.xml`中配置该类的注入的方式为构造函数方式。

    `XmlBasedConstructBasedClassServiceImpl`的修改如下：
    
    ```java
    public class XmlBasedConstructBasedClassServiceImpl implements IClassService {
        private IClassDao classDao;
    
          // 添加构造函数
        public XmlBasedConstructBasedClassServiceImpl(IClassDao classDao) {
            this.classDao = classDao;
        }
        // 省略其他
    }
    ```
    
    `applications.xml`做如下修改：
    
    ```xml
    <bean id="xmlBasedConstructBasedClassService" class="com.alex.springdemo.service.impl.XmlBasedConstructBasedClassServiceImpl">
        <constructor-arg name="classDao" ref="xmlBasedClassDao"/>
    </bean>
    ```
    `constructor-arg`表示采用构造函数的方式注入bean，构造函数中变量的名字为`name`的值，需要注入的bean通过`ref`指定，`ref`的值为其他bean的`id`或`name`。

1. 基于属性的注入
基于属性的注入通过类变量对应的setter方法将bean注入到类中。修改`applications.xml`中`xmlBasedPropertyBasedClassService`注入的方式为基于属性注入，同时在`XmlBasedPropertyBasedClassServiceImpl`中提供`classDao`的setter方法。

```java
public class XmlBasedPropertyBasedClassServiceImpl implements IClassService {
    private IClassDao classDao;
    
    // 省略其他
    public void setClassDao(IClassDao classDao) {
        this.classDao = classDao;
    }
}
```


```xml
<bean id="xmlBasedPropertyBasedClassService" class="com.alex.springdemo.service.impl.XmlBasedPropertyBasedClassServiceImpl">
    <property name="classDao" ref="xmlBasedClassDao"/>
</bean>
```


### 基于Java编码的配置
基于Java编码的配置方式在类上添加`@Configuration`注解、在类方法上添加`@Bean`表明该方法用与注册bean、在方法体中创建bean对象并处理相关依赖项。方法的返回值类型是bean的类型，方法名默认是bean的名称。`@Configuration`相当于XML文件中的`beans`节点，`@Bean`相当于XML文件中的`beans`节点。

还是从`基于构造函数`和`基于属性`两种方法演示基于Java的bean的配置方式。
1. 基于构造函数
通过构造函数注入`IClassDao`对象
```java
public class JavaBasedConstructBasedClassServiceImpl implements IClassService {
    private IClassDao classDao;

    public JavaBasedConstructBasedClassServiceImpl(IClassDao classDao) {
        this.classDao = classDao;
    }

    @Override
    public ClassModel getById(long id) {
        return classDao.getById(1);
    }
}
```

    在类`ApplicationConfig`中配置`JavaBasedClassDaoImpl`和`JavaBasedConstructBasedClassServiceImpl`。
    
    ```java
    @Configuration
    public class ApplicationConfig {
    
        @Bean
        public JavaBasedClassDaoImpl javaBasedClassDao() {
            return new JavaBasedClassDaoImpl();
        }
    
        @Bean
        public JavaBasedConstructBasedClassServiceImpl javaBasedConstructBasedClassService() {
            return new JavaBasedConstructBasedClassServiceImpl(javaBasedClassDao());
        }
    }
    ```
    当需要注入bean到其他bean的时候，可以通过待注入bean对应的函数调用进行注入，例如这里通过构造函数将`javaBasedClassDao()`注入到`JavaBasedConstructBasedClassServiceImpl`中。

1. 基于属性注入
唯一和基于构造函数的方式不同的是----通过类字段对应的`setter`方式注入依赖依赖而不是构造函数。

    通过setter方法注入依赖
    ```java
    public class JavaBasedPropertyBasedClassServiceImpl implements IClassService {
        private IClassDao classDao;
        @Override
        public ClassModel getById(long id) {
            return classDao.getById(1);
        }
    
        public void setClassDao(IClassDao classDao) {
            this.classDao = classDao;
        }
    }
    ```

    注入`JavaBasedClassDaoImpl`到`JavaBasedPropertyBasedClassServiceImpl`
    ```java
    @Configuration
    public class ApplicationConfig {
    
        @Bean
        public JavaBasedClassDaoImpl javaBasedClassDao() {
            return new JavaBasedClassDaoImpl();
        }
    
        @Bean
        public JavaBasedPropertyBasedClassServiceImpl javaBasedPropertyBasedClassService() {
            JavaBasedPropertyBasedClassServiceImpl javaBasedPropertyBasedClassService = new JavaBasedPropertyBasedClassServiceImpl();
            javaBasedPropertyBasedClassService.setClassDao(javaBasedClassDao());
            return javaBasedPropertyBasedClassService;
        }
    }
    ```
    若不做其他配置，spring并不能直接识别出`@Configuration`和`@Bean
    `标注，可以开启spring的自动扫描让spring能够识别`@Configuration`和`@Bean
    `注解。
    
    
    ```xml
    <beans xmlns="http://www.springframework.org/schema/beans"
           xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:aop="http://www.springframework.org/schema/aop"
           xmlns:context="http://www.springframework.org/schema/context"
           xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd http://www.springframework.org/schema/aop http://www.springframework.org/schema/aop/spring-aop.xsd http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd">
    
        <context:component-scan base-package="com.alex.springdemo"/>
    </beans>
    ```

    在`applications.xml`中添加`context:component-scan`即可开启自动扫描注册，`base-package`表示要扫描的基础包，将扫描该包下所有spring能够识别的标注。然后再通过`ClassPathXmlApplicationContext`获取容器来获取bean。
    
    ```java
    public class JavaBasedConstructBasedClassServiceImplTest {
        private static ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:applications.xml");
        private static IClassService javaBasedConstructBasedClassService = applicationContext.getBean("javaBasedConstructBasedClassService", JavaBasedConstructBasedClassServiceImpl.class);
    
        public static void main(String[] args) {
            System.out.println(javaBasedConstructBasedClassService.getById(1));
        }
    }
    ```


### 基于注解配置
在**基于Java编码的配置**一节中提到了spring的自动扫描注册功能，基于注解的配置同样依赖于这个功能。`@Service`、`@Repository`、`@Component`和`@Controller`等标注可以被spring自动扫描注册（前提是设定了扫描的基础包`base package`，可以同时设置多个包），`@Resource`和。通过`AnnotationConfigApplicationContext`而不是`ClassPathXmlApplicationContext`获取容器对象，`AnnotationConfigApplicationContext`的构造函数`basePackages`的参数可以需要扫描的包名。

1. `@Repository`标注dao
    ```java
    @Repository(value = "annotationBasedClassDao")
    public class AnnotationBasedClassDaoImpl implements IClassDao {
        @Override
        public ClassModel getById(long id) {
            return new ClassModel(3, "AnnotationBasedClassDaoImpl");
        }
    }
    ```
    `@Repository`的`value`类似于xml中bean的id属性，用于指明bean的名称

1. `@Service`注册Service并且通过`@Resource`注入依赖
    ```java
    @Service(value = "annotationBasedClassService")
    public class AnnotationBasedClassServiceImpl implements IClassService {
        @Resource(name = "annotationBasedClassDao")
        private IClassDao classDao;
        @Override
        public ClassModel getById(long id) {
            return classDao.getById(id);
        }
    }
    ```
    `@Service`的`value`类似于xml中bean的id属性，用于指明bean的名称。`@Resource`的`name`指需要注入的bean的名称。
    
2. `AnnotationConfigApplicationContext`获取bean

    ```java
    public class AnnotationBasedClassServiceImplTest {
        private static ApplicationContext applicationContext = new AnnotationConfigApplicationContext("com.alex.springdemo");
        private static IClassService classService = applicationContext.getBean("annotationBasedClassService", AnnotationBasedClassServiceImpl.class);
    
        public static void main(String[] args) {
            System.out.println(classService.getById(1));
        }
    
    }
    ```

### 自动配置
自动配置是指当spring能够自动的注册maven中引入的第三方依赖，这个特性目前只存在于`SpringBoot`项目中，这里不做介绍。

## 混合配置
一般情况下项目中只需要一个`ApplicationContext`对象，上文介绍了`ClassPathXmlApplicationContext`和`AnnotationConfigApplicationContext`两种容器的实现。由于XML配置是spring的传统配置方式，而java和注解是spring推荐的配置方式。因此在这些项目中，可能出现xml和java并存的情况，需要混合使用上诉配置方式。
常见的混合配置的情况有：
- 基于`AnnotationConfigApplicationContext`的配置导入基于XML的配置
- 基于`ClassPathXmlApplicationContext`的配置导入基于java的配置

> 1. 对于基于注解的配置，只需要在xml或者AnnotationConfigApplicationContext中指定base package即可。
> 2. 基于`ClassPathXmlApplicationContext`的配置只需要开启包自动扫描即可导入基于java和注解的配置
> 3. 因此下面只介绍基于`AnnotationConfigApplicationContext`的配置导入基于XML的配置

### 基于`AnnotationConfigApplicationContext`的配置导入基于XML的配置
1. 首先需要将xml文件中的bean导入到`@Configuration`注解的类中

    ```java
    @Configuration
    @ImportResource(locations = "classpath:applications.xml")
    public class ApplicationConfig {
        // 省略其他
    }
    ```
    `@ImportResource`用来导入xml文件中的配置，从`locations`可以看出可以同时导入多个文件。

1. 然后就可以在其他bean中注入xml中配置的bean了


    ```java
    @Service(value = "fixAnnotationAndXmlClassService")
    public class FixAnnotationAndXmlClassServiceImpl implements IClassService {
    
        @Resource(name = "studentDao")
        private StudentDao studentDao;
    
        @Resource(name = "annotationBasedClassDao")
        private IClassDao classDao;
    
        @Override
        public ClassModel getById(long id) {
            System.out.println(studentDao.getById(1));
            return classDao.getById(id);
        }
    }
    ```
   
3.最后通过`AnnotationConfigApplicationContext`获取bean

## 总结
本文首先分别介绍了**基于XML**、**基于java**和**基于注解**三种bean的配置方式，并且讨论并分析了如何混合使用这三种配置方式。