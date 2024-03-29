package com.cesarcaceres.springchat.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;

@RestController
public class SpringChatController {
    @GetMapping("/")
    public ModelAndView login() {
        return new ModelAndView("login");
    }

    @GetMapping("/home")
    public ModelAndView index(String username, HttpServletRequest request) throws UnknownHostException {
        ModelAndView view = new ModelAndView("chat");
        view.addObject("username", username);
        return view;
    }
}
