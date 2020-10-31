package com.longade.controller;

import com.longade.connection.HttpConnection;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ApplicationController {

    HttpConnection httpConnection;

    /*@RequestMapping("/")
    public String index() {
        return "Index page!";
    }*/

    /*@RequestMapping("/home")
    public String home() {
        return "Hello, World!";
    }*/

    @RequestMapping("/dave")
    public String dave() {
        return "Hi Dave!";
    }

    @GetMapping("/geturls")
    public ResponseEntity<List<String>> getUrls() {
        try {
            httpConnection = new HttpConnection();
            List<String> urls = httpConnection.getHTML("https://longade.github.io");
            return new ResponseEntity<>(urls, HttpStatus.OK);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
