package com.longade.controller;

import com.longade.connection.HttpConnection;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@RestController
public class ApplicationController {

    HttpConnection httpConnection;
    RestTemplate restTemplate;

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
