package com.alex.springdemo.service.impl;

import com.alex.springdemo.dao.IClassDao;
import com.alex.springdemo.model.ClassModel;

/**
 * @author weilianglei
 * @version 1.0
 * @date 2019-01-31 13:40
 */
public class XmlBasedPropertyBasedClassServiceImpl implements IClassDao {
    private IClassDao classDao;


    @Override
    public ClassModel getById(long id) {
        return classDao.getById(id);
    }

    public void setClassDao(IClassDao classDao) {
        this.classDao = classDao;
    }
}
