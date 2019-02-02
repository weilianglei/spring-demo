package com.alex.springdemo.model;

import java.util.StringJoiner;

/**
 * @author weilianglei
 * @version 1.0
 * @date 2019-01-31 10:21
 */
public class ClassModel {
    private long id;
    private String name;

    public ClassModel(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", ClassModel.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("name='" + name + "'")
                .toString();
    }
}
