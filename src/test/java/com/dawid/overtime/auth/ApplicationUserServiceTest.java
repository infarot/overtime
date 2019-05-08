package com.dawid.overtime.auth;

import com.dawid.overtime.auth.entity.ApplicationUser;
import com.dawid.overtime.auth.repository.ApplicationUserRepository;
import com.dawid.overtime.auth.service.ApplicationUserService;
import com.dawid.overtime.auth.service.ApplicationUserServiceImpl;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
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
