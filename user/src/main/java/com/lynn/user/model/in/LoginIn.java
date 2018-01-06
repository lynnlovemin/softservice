package com.lynn.user.model.in;

import com.lynn.user.model.BaseModel;
import org.hibernate.validator.constraints.NotBlank;

public class LoginIn extends BaseModel {

    @NotBlank(message = "请输入手机号")
    private String mobile;

    @NotBlank(message = "请输入密码")
    private String password;

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
}
