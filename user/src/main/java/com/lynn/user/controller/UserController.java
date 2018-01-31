package com.lynn.user.controller;

import com.lynn.user.model.in.LoginIn;
import com.lynn.user.result.Code;
import com.lynn.user.result.SingleResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RequestMapping("user")
@RestController
public class UserController extends BaseController {

    @Value("${self.data.openid_session_attribute}")
    private String sessionOpenIdAttribute;

    @PostMapping("login")
    public SingleResult<String> login(@Valid LoginIn loginIn, BindingResult ret, HttpServletRequest request, HttpServletResponse response){
        validate(ret);
        SingleResult<String> result = userService.login(loginIn.getMobile(),loginIn.getPassword());
        if(result.getCode() == Code.SUCCESS.getStatus()){
            WebUtils.setSessionAttribute(request,sessionOpenIdAttribute,result.getData());
        }
        return result;
    }
}
