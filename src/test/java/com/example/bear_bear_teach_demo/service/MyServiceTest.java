package com.example.bear_bear_teach_demo.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.Assert.*;


@ExtendWith(MockitoExtension.class)
public class MyServiceTest {

    @Spy
    private MyService myService;

    @Test
    @DisplayName("Should success calculate")
    void addCalculatorCalculateTest () {

        int assertResult = myService.addCalculator(2,3);
        assertEquals(5,assertResult);

    }
}
