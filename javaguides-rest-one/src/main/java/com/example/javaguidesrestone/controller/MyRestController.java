package com.example.javaguidesrestone.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyRestController {

    @GetMapping("/hello-world")
    public String helloWorld(){
        return "yellow";
    }
}
