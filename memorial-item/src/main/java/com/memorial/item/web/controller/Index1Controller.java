package com.memorial.item.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/MP_verify_SZDwalYykeMz7SqJ.txt")
public class Index1Controller {
    @GetMapping
    public String index(){
        return "SZDwalYykeMz7SqJ";
    }
}