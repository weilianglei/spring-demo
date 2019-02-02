package com.alex.springdemo.service;

import com.alex.springdemo.model.ClassModel;

/**
 * @author weilianglei
 * @version 1.0
 * @date 2019-01-31 10:25
 */
public interface IClassService {
    ClassModel getById(long id);
}
