package com.example.bear_bear_teach_demo.service;

import com.example.bear_bear_teach_demo.Repository.BearUserRepository;
import com.example.bear_bear_teach_demo.model.BearUser;
import info.solidsoft.mockito.java8.api.WithBDDMockito;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityNotFoundException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static org.aspectj.bridge.MessageUtil.fail;

@ExtendWith(MockitoExtension.class)
public class BearUserServiceTest implements WithBDDMockito {

    @Mock
    private BearUserRepository repo;

    @InjectMocks
    private BearUserService bearUserService;

    private BearUser gen(Long id, String fName, String lName, int age) {
        BearUser bearUser = new BearUser();
        bearUser.setId(id);
        bearUser.setFirstName(fName);
        bearUser.setLastName(lName);
        bearUser.setAge(age);
        return bearUser;
    }

    @Test
    @DisplayName("Should success to find all")
    void Should_SuccessToFindAll() {
        //given
        BearUser user1 = gen(1L, "Ice", "Bear", 22);
        BearUser user2 = gen(2L, "Panda", "Bear", 20);
        List<BearUser> bearUsers = Arrays.asList(user1,user2);
        bearUsers.sort(Comparator.comparing(BearUser::getId));
        given(repo.findAll()).willReturn(bearUsers);

        //when
        List<BearUser> actual = bearUserService.findAll();

        //then
        then(repo).should(times(1)).findAll();
        MatcherAssert.assertThat(actual, Matchers.hasSize(2));
        MatcherAssert.assertThat(actual.get(0), Matchers.hasProperty("id",Matchers.is(1L)));
        MatcherAssert.assertThat(actual.get(0), Matchers.hasProperty("firstName",Matchers.is("Ice")));
        MatcherAssert.assertThat(actual.get(0), Matchers.hasProperty("lastName",Matchers.is("Bear")));
        MatcherAssert.assertThat(actual.get(0), Matchers.hasProperty("age",Matchers.is(22)));
        MatcherAssert.assertThat(actual.get(1), Matchers.hasProperty("id",Matchers.is(2L)));
        MatcherAssert.assertThat(actual.get(1), Matchers.hasProperty("firstName",Matchers.is("Panda")));
        MatcherAssert.assertThat(actual.get(1), Matchers.hasProperty("lastName",Matchers.is("Bear")));
        MatcherAssert.assertThat(actual.get(1), Matchers.hasProperty("age",Matchers.is(20)));
    }

    @Test
    @DisplayName("Should success to find by id")
    void Should_SuccessToFindById() {
        //given
        BearUser user = gen(1L, "Ice", "Bear", 22);
        given(repo.findById(1L)).willReturn(Optional.of(user));

        //when
        BearUser actual = bearUserService.findByBearId(1L);

        //then
        then(repo).should(times(1)).findById(1L);
        MatcherAssert.assertThat(actual, Matchers.hasProperty("id",Matchers.is(1L)));
        MatcherAssert.assertThat(actual, Matchers.hasProperty("firstName",Matchers.is("Ice")));
        MatcherAssert.assertThat(actual, Matchers.hasProperty("lastName",Matchers.is("Bear")));
        MatcherAssert.assertThat(actual, Matchers.hasProperty("age",Matchers.is(22)));
    }

    @Test
    @DisplayName("Should success to insert")
    void Should_SuccessToInsert() throws Exception {
        //given
        BearUser user = gen(1L, "Ice", "Bear", 22);
        given(repo.save(user)).willReturn(user);

        //when
        bearUserService.addBearUser(user);

        //then
        then(repo).should(times(1)).save(user);
        then(repo).should(never()).saveAndFlush(user);

    }

    @Test
    @DisplayName("Should success to delete")
    void Should_SuccessToDelete() {
        //given
        willDoNothing().given(repo).deleteById(anyLong());

        //when
        Boolean actual = bearUserService.deleteBearUser(anyLong());

        //then
        then(repo).should(times(1)).deleteById(anyLong());
        then(repo).should(never()).deleteAll();
        Assertions.assertEquals(true, actual);

    }

    @Test
    @DisplayName("Should success to update")
    void Should_SuccessToUpdate() {
        //given
        BearUser user = gen(1L, "Ice", "Bear", 22);
        BearUser updateUser = gen(1L, "Panda", "Bear", 22);

        given(repo.findById(anyLong())).willReturn(Optional.of(user));
        given(repo.save(any(BearUser.class))).willReturn(updateUser);

        //when
        BearUser actual = bearUserService.updateBearUser(updateUser);

        //then
        then(repo).should(times(1)).findById(anyLong());
        then(repo).should(times(1)).save(updateUser);
        MatcherAssert.assertThat(actual, Matchers.hasProperty("id",Matchers.is(1L)));
        MatcherAssert.assertThat(actual, Matchers.hasProperty("firstName",Matchers.is("Panda")));
        MatcherAssert.assertThat(actual, Matchers.hasProperty("lastName",Matchers.is("Bear")));
        MatcherAssert.assertThat(actual, Matchers.hasProperty("age",Matchers.is(22)));

    }

    @Test
    @DisplayName("Should can not findById to update")
    void Should_Can_Not_FindById_To_Update() {
        //given
        BearUser updateUser = gen(1L, "Panda", "Bear", 22);

        given(repo.findById(anyLong())).willReturn(Optional.ofNullable(null));

        //when
        try {
            bearUserService.updateBearUser(updateUser);
        } catch (EntityNotFoundException foundException) {
            fail("updateBearUser Service does not exist for id = "+updateUser.getId()+"!");
        }
    }

    @Test
    @DisplayName("Should fail findById")
    void Should_Fail_FindById() {
        //given
        given(repo.findById(anyLong())).willReturn(Optional.ofNullable(null));

        //when
        try {
            bearUserService.findByBearId(anyLong());
        } catch (EntityNotFoundException foundException) {

        }
    }

    @Test
    @DisplayName("Should fail to insert first name null")
    void Should_FailToInsert_FirstName_Null() throws Exception {
        //given
        BearUser user1 = gen(1L, null, "Bear", 22);

        //when
        try {
            bearUserService.addBearUser(user1);
        } catch (Exception ex) {
            fail("First name must be null");
        }
    }

    @Test
    @DisplayName("Should fail to insert first name empty")
    void Should_FailToInsert_FirstName_Empty() throws Exception {
        //given
        BearUser user2 = gen(1L, "", "Bear", 22);

        //when
        try {
            bearUserService.addBearUser(user2);
        } catch (Exception ex) {
            fail("First name must be null");
        }
    }

    @Test
    @DisplayName("Should fail to insert first name blank")
    void Should_FailToInsert_FirstName_Blank() throws Exception {
        //given
        BearUser user3 = gen(1L, " ", "Bear", 22);

        //when
        try {
            bearUserService.addBearUser(user3);
        } catch (Exception ex) {
            fail("First name must be null");
        }
    }

    @Test
    @DisplayName("Should fail to insert last name null")
    void Should_FailToInsert_LastName_Null() throws Exception {
        //given
        BearUser user1 = gen(1L, "Ice", null, 22);

        //when
        try {
            bearUserService.addBearUser(user1);
        } catch (Exception ex) {
            fail("Last name must be null");
        }
    }

    @Test
    @DisplayName("Should fail to insert last name empty")
    void Should_FailToInsert_LastName_Empty() throws Exception {
        //given
        BearUser user2 = gen(1L, "Ice", "", 22);

        //when
        try {
            bearUserService.addBearUser(user2);
        } catch (Exception ex) {
            fail("Last name must be null");
        }
    }

    @Test
    @DisplayName("Should fail to insert last name blank")
    void Should_FailToInsert_LastName_Blank() throws Exception {
        //given
        BearUser user3 = gen(1L, "Ice", " ", 22);

        //when
        try {
            bearUserService.addBearUser(user3);
        } catch (Exception ex) {
            fail("Last name must be null");
        }
    }

    @Test
    @DisplayName("Should fail to insert age")
    void Should_FailToInsert_Age() throws Exception {
        //given
        BearUser user3 = gen(1L, "Ice", "Bear", 0);

        //when
        try {
            bearUserService.addBearUser(user3);
        } catch (Exception ex) {
            fail("Age can be a positive integer");
        }
    }

}
