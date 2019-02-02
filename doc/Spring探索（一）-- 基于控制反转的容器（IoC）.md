# Spring探索（一）-- 基于控制反转的容器（IoC）
项目的搭建就像是积木的拼接----对象好比是零散的积木组件，而一个完整的项目就是将这些组件合理地组合到一起的成品。在基于Spring的项目中，Spring通过**容器**来管理项目中的对象，这些对象称之为**bean**。

> 本文相关代码参看[GitHub](https://github.com/weilianglei/spring-demo/tree/add_spring_core_framwork)

## bean
在java中有`JavaBean`的概念，简单地说就是只有类字段和**getter**、**setter**等方法的简单的类。而在spring中，bean的定义更为广义，下面给出官方文档的定义

> A bean is an object that is instantiated, assembled, and otherwise managed by a Spring IoC container. ---- 引自官方文档

简单的说就是被spring容器管理的对象都称之为**bean**对象，而不管类的具体定义方式。

## 容器
从bean的定义中不能看出，容器能够创建bean、管理bean。spring中的容器实现了**控制反转（IoC）/依赖注入（DI）**，因此也称之为**IoC容器**。spring的`BeanFactory`接口定义了管理bean的一些常见方法，其子接口`ApplicationContext`提供了事件等更多的方法，而`WebApplicationContext`接口则又提供了web项目中常见的方法。也就是说，我们可以通过实现这些接口来管理bean，实际上spring已经提供了不少这些接口的实现，能够满足一般项目的需求。

控制反转的实现，让我们无需在代码中直接创建依赖对象的实例，spring能够帮助我们完成这个任务。

## 实战
下面我们将通过一个实例来演示：
- 如何将项目中对象注册到IoC容器中
- 如何通过容器注入依赖项
- 如何通过容器获取到已经注册的bean

我们将创建一个`StudentModel`表示学生实体，创建一个`StudentDao`获取学生数据，创建一个`StudentService`对外提供学生服务，通过IoC容器将`StudentDao`对象注入到`StudentService`中，最后再通过`ClassPathXmlApplicationContext`获取到IoC容器中的`StudentService`对象，调用学生服务。

### 依赖项
在项目的`pom.xml`中添加`spring-context`依赖

```
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-context</artifactId>
    <version>5.1.4.RELEASE</version>
</dependency>
```

### 类的创建
- StudentModel


```java
public class StudentModel {

    private Long id;
    private String name;
    private int age;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", StudentModel.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("name='" + name + "'")
                .add("age=" + age)
                .toString();
    }
}
```

- StudentDao


```java
public class StudentDao {
    public StudentModel getById(long id) {
        StudentModel studentModel = new StudentModel();
        studentModel.setId(1L);
        studentModel.setName("test");
        studentModel.setAge(28);
        return studentModel;
    }
}
```

- StudentService


```java
public class StudentService {

    private StudentDao studentDao;

    public StudentService(StudentDao studentDao) {
        this.studentDao = studentDao;
    }

    public StudentModel getById(long id) {
        return studentDao.getById(id);
    }
}
```

### 注册`StudentDao`和`StudentService`
bean注册方式有多种，本文采用传统的`XML`的方式注册。
> 在[Spring中的Bean配置方式](https://www.jianshu.com/p/197eb78faf54)一文中详细地介绍了如何在spring中配置bean

在项目的`applications.xml`中作如下配置：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
    <bean id="studentDao" class="com.alex.springdemo.dao.StudentDao"/>
    <bean id="studentService" class="com.alex.springdemo.service.StudentService">
    </bean>
</beans>
```

`beans`表示该XML中所有注册的bean，`bean`注册一个具体的bean，其中`id`是bean的名字，`class`指出该对象的具体类型。

### `StudentDao`注入到`StudentService`
依赖注入一般可以通过`属性`或者`构造函数`的方式进行注入，这里采用构造函数的方式。
在`StudentService`类中有一个`StudentDao`类型的`studentDao`字段，对应有一个有参构造函数，在`applications.xml`中id为`studentDao`的bean通过构造函数注入到id为`studentService`的bean中。具体修改如下：

```
<bean id="studentService" class="com.alex.springdemo.service.StudentService">
    <constructor-arg name="studentDao" ref="studentDao"></constructor-arg>
</bean>
```

`constructor-arg`指明通过构造函数的方式注入对象，`ref`指出需要注入的具体的对象。

### 获取bean对象
通过实现`ApplicationContext`接口的对象能够从容器中获取到指定的bean对象，spring中提供了几种实现，这里通过`ClassPathXmlApplicationContext`指定bean的配置XML文件来注册和管理bean。
1. 获取没有依赖关系的`StudentDao`对象

    ```java
    public class StudentDaoTest {
        public static void main(String[] args) {
            ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:applications.xml");
            StudentDao studentDao = (StudentDao)applicationContext.getBean("studentDao");
            System.out.println(studentDao.getById(1L));
        }
    }
    ```

1. 获取没有依赖关系的`StudentService`对象


    ```java
    public class StudentServiceTest {
        private static ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:applications.xml");
        private static StudentService studentService = (StudentService)applicationContext.getBean("studentService");
        public static void main(String[] args) {
            System.out.println(studentService.getById(1L));
        }
    }
    ```


## 总结
本文简单地介绍了spring中的IoC容器，并通过一个具体的实例演示了如何在项目中使用IoC容器，包括Bean的注册、Bean的注入以及Bean的获取。Bean的注册以及容器的实现多有种方式，本文只演示其中的一种用法，其他具体的使用会在后续文章中专门介绍。

    


















