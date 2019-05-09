package com.dawid.overtime.security.service;

import com.dawid.overtime.security.entity.ApplicationUser;
import com.dawid.overtime.security.repository.ApplicationUserRepository;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ApplicationUserServiceTest {

    private ApplicationUserService instance;

    @Mock
    private ApplicationUserRepository applicationUserRepository;


    @Before
    public void setup(){
        instance = new ApplicationUserServiceImpl(applicationUserRepository);
    }

    @Test
    public void isAbleToFindUserByUsername(){
        ApplicationUser testApplicationUser = new ApplicationUser();
        testApplicationUser.setId(1);
        testApplicationUser.setUsername("test");
        testApplicationUser.setPassword("test");

        String testUsername = "test";

        Mockito.when(applicationUserRepository.findByUsername(testUsername)).thenReturn(testApplicationUser);

        Assert.assertEquals(instance.findByUsername("test"), testApplicationUser);
    }
}
