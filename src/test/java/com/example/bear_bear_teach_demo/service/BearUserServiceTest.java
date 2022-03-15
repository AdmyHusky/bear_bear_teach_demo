package com.example.bear_bear_teach_demo.service;

import com.example.bear_bear_teach_demo.Repository.BearUserRepository;
import com.example.bear_bear_teach_demo.model.BearUser;
import info.solidsoft.mockito.java8.api.WithBDDMockito;
import org.hamcrest.Matcher;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static org.junit.Assert.assertEquals;

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
        List<BearUser> actual = bearUserService.findlistAll();

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
}
