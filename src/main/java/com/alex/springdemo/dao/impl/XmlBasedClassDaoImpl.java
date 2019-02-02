package com.alex.springdemo.dao.impl;

import com.alex.springdemo.dao.IClassDao;
import com.alex.springdemo.model.ClassModel;

/**
 * @author weilianglei
 * @version 1.0
 * @date 2019-01-31 10:27
 */
public class XmlBasedClassDaoImpl implements IClassDao {
    @Override
    public ClassModel getById(long id) {
        return new ClassModel(1, "XmlBasedClassDaoImpl");
    }
}
