package com.bibek.hms.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class Test {

    @GetMapping
    public String test(){
        return "All Good 😌";
    }
}
