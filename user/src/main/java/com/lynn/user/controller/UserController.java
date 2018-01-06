package com.lynn.user.controller;

import com.lynn.user.model.in.LoginIn;
import com.lynn.user.result.SingleResult;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequestMapping("user")
@RestController
public class UserController extends BaseController {

    @PostMapping("login")
    public SingleResult<String> login(@Valid LoginIn loginIn,BindingResult ret){
        validate(ret);
        return userService.login(loginIn.getMobile(),loginIn.getPassword());
    }
}
