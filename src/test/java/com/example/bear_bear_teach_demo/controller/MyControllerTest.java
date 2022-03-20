package com.example.bear_bear_teach_demo.controller;

import com.example.bear_bear_teach_demo.service.MyService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class MyControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    MyController myController;

    @Mock
    MyService myService;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(myController).build();
    }

    @Test
    public void successCallCalculatorAddNumberTest() throws Exception {
        Mockito.when(myService.addCalculator(Mockito.anyInt(),Mockito.anyInt())).thenReturn(3);
        mockMvc.perform(get("/v1/bearBearTeach?a=1&b=2")
                        .contentType("application/json"))
                        .andExpect(status().isOk())
                        .andExpect(content().string(containsString("3")));
    }

    @Test
    public void badRequestCallCalculatorAddNumberTest() throws Exception {
        mockMvc.perform(get("/v1/bearBearTeach")
                        .contentType("application/json"))
                        .andExpect(status().isBadRequest());
    }

    @Test
    public void successHiBearTest() throws Exception {
        mockMvc.perform(get("/v1/bearBearTeach/hiBear")
                        .contentType("application/json"))
                        .andExpect(status().isOk())
                        .andExpect(content().string(containsString("Hi Bear")));
    }

}
