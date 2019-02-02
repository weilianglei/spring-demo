package com.alex.springdemo.dao.impl;

import com.alex.springdemo.dao.IClassDao;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


class JavaBasedClassDaoImplTest {
    private static ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:applications.xml");
    private static IClassDao javaBasedClassDao = applicationContext.getBean("javaBasedClassDao", JavaBasedClassDaoImpl.class);

    public static void main(String[] args) {
        System.out.println(javaBasedClassDao.getById(1));
    }
}