package com.example.bear_bear_teach_demo.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MyService {

    public int addCalculator(int a, int b) {
        return a + b ;
    }

}
