package com.fireBnb.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/Country")
public class CountryController {
    @PostMapping("/addCountry")
    public String AddCountry(){
        return "done";
    }
}
