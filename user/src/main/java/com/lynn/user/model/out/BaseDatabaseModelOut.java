package com.lynn.user.model.out;

import com.lynn.user.model.BaseModel;

import java.util.Calendar;

/**
 * 对应数据库每张表必加的字段：id,gmt_create,gmt_modified
 */
public class BaseDatabaseModelOut extends BaseModel{

    private Long id;

    private Calendar create;

    private Calendar modified;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Calendar getCreate() {
        return create;
    }

    public void setCreate(Calendar create) {
        this.create = create;
    }

    public Calendar getModified() {
        return modified;
    }

    public void setModified(Calendar modified) {
        this.modified = modified;
    }
}
