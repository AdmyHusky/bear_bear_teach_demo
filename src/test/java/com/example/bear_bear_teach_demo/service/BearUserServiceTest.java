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

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

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

}
