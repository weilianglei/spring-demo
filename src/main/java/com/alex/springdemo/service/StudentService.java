package com.alex.springdemo.service;

import com.alex.springdemo.dao.StudentDao;
import com.alex.springdemo.model.StudentModel;

/**
 * @author weilianglei
 * @version 1.0
 * @date 2019-01-30 19:49
 */
public class StudentService {

    private StudentDao studentDao;

    public StudentService(StudentDao studentDao) {
        this.studentDao = studentDao;
    }

    public StudentModel getById(long id) {
        return studentDao.getById(id);
    }
}
