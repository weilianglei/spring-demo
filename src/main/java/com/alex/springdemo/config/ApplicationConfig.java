package com.alex.springdemo.config;

import com.alex.springdemo.dao.impl.JavaBasedClassDaoImpl;
import com.alex.springdemo.service.impl.JavaBasedConstructBasedClassServiceImpl;
import com.alex.springdemo.service.impl.JavaBasedPropertyBasedClassServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

/**
 * @author weilianglei
 * @version 1.0
 * @date 2019-01-31 20:52
 */
@Configuration
@ImportResource(locations = "classpath:applications.xml")
public class ApplicationConfig {

    @Bean
    public JavaBasedClassDaoImpl javaBasedClassDao() {
        return new JavaBasedClassDaoImpl();
    }

    @Bean
    public JavaBasedConstructBasedClassServiceImpl javaBasedConstructBasedClassService() {
        return new JavaBasedConstructBasedClassServiceImpl(javaBasedClassDao());
    }

    @Bean
    public JavaBasedPropertyBasedClassServiceImpl javaBasedPropertyBasedClassService() {
        JavaBasedPropertyBasedClassServiceImpl javaBasedPropertyBasedClassService = new JavaBasedPropertyBasedClassServiceImpl();
        javaBasedPropertyBasedClassService.setClassDao(javaBasedClassDao());
        return javaBasedPropertyBasedClassService;
    }
}
