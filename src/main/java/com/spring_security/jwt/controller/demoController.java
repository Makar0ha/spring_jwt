package com.spring_security.jwt.controller;

import org.apache.tomcat.util.http.parser.Authorization;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/demo-controller/")
public class demoController {
    @GetMapping
    public ResponseEntity<String> sayHello(@RequestHeader("Authorization") String authorizationHeader){
        System.out.println(authorizationHeader);
        return ResponseEntity.ok("Hello from secured endpoint");
    }
}
