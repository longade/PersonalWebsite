package com.longade.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApplicationController {

    @RequestMapping("/")
    public String index() {
        return "Index page!";
    }

    @RequestMapping("/home")
    public String home() {
        return "Hello, World!";
    }

    @RequestMapping("/dave")
    public String dave() {
        return "Hi Dave!";
    }
}
