package com.alex.springdemo.dao.impl;

import com.alex.springdemo.dao.IClassDao;
import com.alex.springdemo.model.ClassModel;
import org.springframework.stereotype.Repository;

/**
 * @author weilianglei
 * @version 1.0
 * @date 2019-01-31 10:28
 */
@Repository(value = "annotationBasedClassDao")
public class AnnotationBasedClassDaoImpl implements IClassDao {
    @Override
    public ClassModel getById(long id) {
        return new ClassModel(3, "AnnotationBasedClassDaoImpl");
    }
}
