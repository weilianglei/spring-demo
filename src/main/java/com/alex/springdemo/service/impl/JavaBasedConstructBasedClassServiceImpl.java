package com.alex.springdemo.service.impl;

import com.alex.springdemo.dao.IClassDao;
import com.alex.springdemo.model.ClassModel;
import com.alex.springdemo.service.IClassService;

/**
 * @author weilianglei
 * @version 1.0
 * @date 2019-01-31 10:30
 */
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
