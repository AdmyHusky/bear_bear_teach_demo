package com.example.bear_bear_teach_demo.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;


@RunWith(MockitoJUnitRunner.class)
public class MyServiceTest {

    @InjectMocks
    private MyService myService;

    @Test
    public void addCalculatorCalculateTest () {

        int assertResult = myService.addCalculator(2,3);
        assertEquals(5,assertResult);

    }
}
