package com.dawid.overtime.security.repository;

import com.dawid.overtime.security.entity.ApplicationUser;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ApplicationUserRepositoryTest {

    @Autowired
    private ApplicationUserRepository applicationUserRepository;

    @After
    public void tearDown(){
        applicationUserRepository.deleteAll();
    }

    @Test
    public void findByUserNameIsReturningCorrectUser(){

        ApplicationUser testUser = new ApplicationUser();
        testUser.setUsername("test");
        testUser.setPassword("test");

        applicationUserRepository.save(testUser);

        Assert.assertEquals(applicationUserRepository.findByUsername("test"), testUser);
    }

    @Test
    public void findByUserNameIsReturningNullForMissingUser(){

        ApplicationUser testUser = new ApplicationUser();
        testUser.setUsername("test");
        testUser.setPassword("test");

        applicationUserRepository.save(testUser);

        Assert.assertNull(applicationUserRepository.findByUsername("NotExistingUser"));
    }
}
