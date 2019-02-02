package com.alex.springdemo.service.impl;

import com.alex.springdemo.dao.IClassDao;
import com.alex.springdemo.dao.StudentDao;
import com.alex.springdemo.model.ClassModel;
import com.alex.springdemo.service.IClassService;

/**
 * @author weilianglei
 * @version 1.0
 * @date 2019-01-31 21:19
 */
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
