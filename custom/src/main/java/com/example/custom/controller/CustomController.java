package com.example.custom.controller;

import com.example.custom.annotation.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CustomController {

    @GetMapping("/")
    public void custom() {
    }
}
