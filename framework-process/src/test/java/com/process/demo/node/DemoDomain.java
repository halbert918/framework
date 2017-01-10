package com.process.demo.node;

import com.framework.process.domain.Domain;

/**
 * Created by admin on 2017/1/9.
 */
public class DemoDomain implements Domain {

    private String id;

    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
