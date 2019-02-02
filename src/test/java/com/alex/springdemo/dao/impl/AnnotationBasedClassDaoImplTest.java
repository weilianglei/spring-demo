package com.alex.springdemo.dao.impl;


import com.alex.springdemo.dao.IClassDao;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class AnnotationBasedClassDaoImplTest {
    private static ApplicationContext applicationContext = new AnnotationConfigApplicationContext("com.alex.springdemo");
    private static IClassDao classDao = applicationContext.getBean("annotationBasedClassDao", AnnotationBasedClassDaoImpl.class);
    public static void main(String[] args) {
        System.out.println(classDao.getById(1));
    }

}