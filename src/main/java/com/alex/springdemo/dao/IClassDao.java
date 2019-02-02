package com.alex.springdemo.dao;

import com.alex.springdemo.model.ClassModel;

/**
 * @author weilianglei
 * @version 1.0
 * @date 2019-01-31 10:24
 */
public interface IClassDao {
    ClassModel getById(long id);
}
