package com.alex.springdemo.dao;


import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class StudentDaoTest {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:applications.xml");
        StudentDao studentDao = (StudentDao)applicationContext.getBean("studentDao");
        System.out.println(studentDao.getById(1L));
    }
}