package com.lynn.user.controller;

import com.lynn.user.service.OAuthService;
import com.lynn.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;

public abstract class BaseController {

    @Autowired
    protected UserService userService;
    @Autowired
    protected OAuthService oAuthService;

    /**
     * 参数的合法性校验
     * @param result
     */
    protected void validate(BindingResult result){
        if(result.hasFieldErrors()){
            List<FieldError> errorList = result.getFieldErrors();
            errorList.stream().forEach(item -> Assert.isTrue(false,item.getDefaultMessage()));
        }
    }

}
