package com.lynn.user.controller;

import com.lynn.user.model.in.AuthorizeIn;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("")
@Controller
public class PageController extends BaseController {

    @GetMapping("login")
    public String login(AuthorizeIn authorize, Model model){
        model.addAttribute("authorize",authorize);
        return "login";
    }
}
