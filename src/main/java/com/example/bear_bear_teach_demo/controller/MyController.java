package com.example.bear_bear_teach_demo.controller;

import com.example.bear_bear_teach_demo.Repository.BearUserRepository;
import com.example.bear_bear_teach_demo.model.BearUser;
import com.example.bear_bear_teach_demo.service.BearUserService;
import com.example.bear_bear_teach_demo.service.MyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/bearBearTeach")
public class MyController {

    @Autowired
    private MyService myService;

    @GetMapping()
    @RequestMapping("/hiBear")
    public ResponseEntity hiBear () {
        return  ResponseEntity.ok().body("Hi Bear");
    }

    @GetMapping()
    public ResponseEntity calculatorAddNumber (@RequestParam int a, @RequestParam int b) {
        return  ResponseEntity.ok().body(myService.addCalculator(a,b));
    }

}
