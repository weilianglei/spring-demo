package com.alex.springdemo.dao;

import com.alex.springdemo.model.StudentModel;

/**
 * @author weilianglei
 * @version 1.0
 * @date 2019-01-27 13:13
 */
public class StudentDao {
    public StudentModel getById(long id) {
        StudentModel studentModel = new StudentModel();
        studentModel.setId(1L);
        studentModel.setName("test");
        studentModel.setAge(28);
        return studentModel;
    }
}
