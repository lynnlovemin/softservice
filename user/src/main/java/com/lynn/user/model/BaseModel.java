package com.lynn.user.model;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;

public abstract class BaseModel implements Serializable {

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
