package com.lynn.user.model.out;

import com.lynn.user.model.BaseModel;

import java.util.Calendar;

public class UserOut extends BaseModel {

    private Long id;

    private String mobile;

    private String password;

    private Calendar create;

    private Calendar modified;

    private String openid;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }
}
