package com.memorial.greenroom.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author kgy
 * @version 1.0
 * @date 2020/4/29 13:57
 */
@Controller
@RequestMapping("user")
public class UserController {

    @RequestMapping("tz")
    public String tz(){

        return "system/qdAPI";
    }
}
