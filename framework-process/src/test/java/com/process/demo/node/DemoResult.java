package com.process.demo.node;

import com.framework.process.result.Result;

/**
 * @Vesrion 1.0
 * @Author heyinbo
 * @Description
 */
public class DemoResult implements Result {

    private String status;

    public String getStatus() {
        return status;
    }

    public String getCode() {
        return null;
    }

    public String getDescription() {
        return null;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String getMessage() {
        return null;
    }
}
