package com.dawid.overtime.security.service;

import com.dawid.overtime.entity.ApplicationUser;
import com.dawid.overtime.security.repository.ApplicationUserRepository;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class ApplicationUserServiceTest {

    private UserDetailsService instance;

    @Mock
    private ApplicationUserRepository applicationUserRepository;


    @Before
    public void setup(){
        instance = new UserDetailsServiceImpl(applicationUserRepository);
    }

    @Test
    public void isAbleToFindUserByUsername(){
        ApplicationUser testApplicationUser = new ApplicationUser();
        testApplicationUser.setUsername("test");
        testApplicationUser.setPassword("test");

        String testUsername = "test";

        Mockito.when(applicationUserRepository.findByUsername(testUsername)).thenReturn(Optional.of(testApplicationUser));

        Assert.assertEquals(instance.loadUserByUsername("test").getUsername(), testApplicationUser.getUsername());
    }
}
