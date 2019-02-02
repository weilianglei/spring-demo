package com.alex.springdemo.service.impl;

import com.alex.springdemo.dao.IClassDao;
import com.alex.springdemo.model.ClassModel;
import com.alex.springdemo.service.IClassService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author weilianglei
 * @version 1.0
 * @date 2019-01-31 10:31
 */
@Service(value = "annotationBasedClassService")
public class AnnotationBasedClassServiceImpl implements IClassService {
    @Resource(name = "annotationBasedClassDao")
    private IClassDao classDao;

    @Override
    public ClassModel getById(long id) {
        return classDao.getById(id);
    }
}
