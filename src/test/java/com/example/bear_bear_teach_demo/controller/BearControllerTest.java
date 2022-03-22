package com.example.bear_bear_teach_demo.controller;

import com.example.bear_bear_teach_demo.exception.NotFoundException;
import com.example.bear_bear_teach_demo.model.BearUser;
import com.example.bear_bear_teach_demo.service.BearUserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import info.solidsoft.mockito.java8.api.WithBDDMockito;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(BearController.class)
class BearControllerTest implements WithBDDMockito {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private BearUserService bearUserService;

    private BearUser gen(Long id, String fName, String lName, int age) {
        BearUser bearUser = new BearUser();
        bearUser.setId(id);
        bearUser.setFirstName(fName);
        bearUser.setLastName(lName);
        bearUser.setAge(age);
        return bearUser;
    }

    //obj to string
    private String toJsonString(final Object obj) throws JsonProcessingException {
        return objectMapper.writeValueAsString(obj);
    }

    @Test
    @DisplayName("Should success to find all")
    void Should_SuccessToFindAll() throws Exception {
        //given
        List<BearUser> bearUsers =
                Arrays.asList(
                        gen(1L,"Ice","Bear",20),
                        gen(2L, "Panda", "Bear", 20));
        given(bearUserService.findAll()).willReturn(bearUsers);

        //when
        mvc
                .perform(get("/v1/bear/bearUsers").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                //then
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", Matchers.is(1)))
                .andExpect(jsonPath("$[0].firstName", Matchers.is("Ice")))
                .andExpect(jsonPath("$[0].lastName", Matchers.is("Bear")))
                .andExpect(jsonPath("$[0].age", Matchers.is(20)))
                .andExpect(jsonPath("$[1].id", Matchers.is(2)))
                .andExpect(jsonPath("$[1].firstName", Matchers.is("Panda")))
                .andExpect(jsonPath("$[1].lastName", Matchers.is("Bear")))
                .andExpect(jsonPath("$[1].age", Matchers.is(20)));

    }

    @Test
    @DisplayName("Should success to find by id")
    void Should_SuccessToFindById() throws Exception {
        //given
        BearUser user2 = gen(2L, "Panda", "Bear", 20);
        given(bearUserService.findByBearId(2L))
                .willReturn(user2);

        //when
        mvc
                .perform(get("/v1/bear/bearUsers/2").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                //then
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.id", Matchers.is(2)))
                .andExpect(jsonPath("$.firstName", Matchers.is("Panda")))
                .andExpect(jsonPath("$.lastName", Matchers.is("Bear")))
                .andExpect(jsonPath("$.age", Matchers.is(20)));

    }

    @Test
    @DisplayName("Should success to insert")
    void Should_SuccessToInsert() throws Exception {
        //given
        BearUser user = gen(2L, "Panda", "Bear", 20);
        doNothing().when(bearUserService).addBearUser(user);
        //when
        mvc
                .perform(post("/v1/bear/bearUsers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJsonString(user)))
                .andDo(print())
                //then
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("insert success")));


    }

    @Test
    @DisplayName("Should success to delete")
    void Should_SuccessToDelete() throws Exception {
        //given
        given(bearUserService.deleteBearUser(2L))
                .willReturn(true);
        //when
        mvc
                .perform(delete("/v1/bear/bearUsers/2").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                //then
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("")));

    }

    @Test
    @DisplayName("Should success to update")
    void Should_SuccessToUpdate() throws Exception {
        //given
        BearUser user = gen(1L, "Panda", "Bear", 20);
        given(bearUserService.updateBearUser(user))
                .willReturn(user);
        ObjectMapper mapper = new ObjectMapper();
        //when
        mvc
                .perform(put("/v1/bear/bearUsers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(user)))
                .andDo(print())
                //then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", Matchers.is("Panda")))
                .andExpect(jsonPath("$.lastName", Matchers.is("Bear")))
                .andExpect(jsonPath("$.age", Matchers.is(20)));

    }

    @Test
    @DisplayName("should Request method not supported patch")
    public void Should_Request_Method_Not_Supported_Patch() throws Exception {
        //given
        BearUser user = gen(1L, "Panda", "Bear", 20);
        given(bearUserService.updateBearUser(user))
                .willReturn(user);
        ObjectMapper mapper = new ObjectMapper();
        //when
        mvc
                .perform(patch("/v1/bear/bearUsers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(user)))
                .andDo(print())
                //then
                .andExpect(status().isMethodNotAllowed());
    }

    @Test
    @DisplayName("should throw exception when user doesn't exist delete")
    public void Should_Throw_Exception_When_User_Does_Not_Exist_Delete() throws Exception {
        //given
        BearUser user = gen(1L, "Panda", "Bear", 20);
        Mockito.doThrow(new NotFoundException("Don't have id: " + user.getId() + " to delete")).when(bearUserService).deleteBearUser(user.getId());
        //when
        mvc
                .perform(delete("/bearUsers/" + user.getId().toString()).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                //then
                .andExpect(status().isNotFound());

    }

    @Test
    @DisplayName("should throw exception when user doesn't exist byId")
    public void Should_Throw_Exception_When_User_Does_Not_Exist_ById() throws Exception {
        //given
        BearUser user = gen(1L, "Panda", "Bear", 20);
        Mockito.doThrow(new NotFoundException("findByBearId Service does not exist for " + user.getId() + "!")).when(bearUserService).findByBearId(user.getId());
        //when
        mvc
                .perform(get("/bearUsers/" + user.getId().toString()).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                //then
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("should throw exception when user doesn't exist update")
    public void Should_Throw_Exception_When_User_Does_Not_Exist_Update() throws Exception {
        //given
        BearUser user = gen(1L, "Panda", "Bear", 20);
        Mockito.doThrow(new NotFoundException("findByBearId Service does not exist for " + user.getId() + "!")).when(bearUserService).updateBearUser(user);
        //when
        mvc
                .perform(put("/bearUsers").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                //then
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("should throw exception when user doesn't exist addBearUser firstname")
    public void Should_Throw_Exception_When_User_Does_Not_Exist_AddBearUser_FirstName() throws Exception {
        //given
        BearUser user = gen(1L, "", "Bear", 20);
        Mockito.doThrow(new NotFoundException("First name must be null")).when(bearUserService).addBearUser(user);
        //when
        mvc
                .perform(post("/bearUsers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJsonString(user)))
                .andDo(print())
                //then
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("")));
    }

    @Test
    @DisplayName("should throw exception when user doesn't exist addBearUser lastname")
    public void Should_Throw_Exception_When_User_Does_Not_Exist_AddBearUser_LastName() throws Exception {
        //given
        BearUser user = gen(1L, "Ice", "", 20);
        Mockito.doThrow(new NotFoundException("Last name must be null")).when(bearUserService).addBearUser(user);
        //when
        mvc
                .perform(post("/bearUsers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJsonString(user)))
                .andDo(print())
                //then
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("")));

    }

    @Test
    @DisplayName("should throw exception when user doesn't exist addBearUser age")
    public void Should_Throw_Exception_When_User_Does_Not_Exist_AddBearUser_Age() throws Exception {
        //given
        BearUser user = gen(1L, "Ice", "Bear", 0);
        Mockito.doThrow(new NotFoundException("Age can be a positive integer")).when(bearUserService).addBearUser(user);
        //when
        mvc
                .perform(post("/bearUsers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJsonString(user)))
                .andDo(print())
                //then
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("")));
    }

}