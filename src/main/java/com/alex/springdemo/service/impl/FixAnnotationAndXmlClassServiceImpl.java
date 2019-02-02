package com.alex.springdemo.service.impl;

import com.alex.springdemo.dao.IClassDao;
import com.alex.springdemo.dao.StudentDao;
import com.alex.springdemo.model.ClassModel;
import com.alex.springdemo.service.IClassService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author weilianglei
 * @version 1.0
 * @date 2019-02-02 17:06
 */
@Service(value = "fixAnnotationAndXmlClassService")
public class FixAnnotationAndXmlClassServiceImpl implements IClassService {

    @Resource(name = "studentDao")
    private StudentDao studentDao;

    @Resource(name = "annotationBasedClassDao")
    private IClassDao classDao;

    @Override
    public ClassModel getById(long id) {
        System.out.println(studentDao.getById(1));
        return classDao.getById(id);
    }
}
