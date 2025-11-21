package User.management.user.management.Service.impl;

import User.management.user.management.Exception.UserIsAlreadyExistExp;
import User.management.user.management.Models.DTO.UserRegisterDTO;
import User.management.user.management.Repositories.UserRepository;
import org.hibernate.exception.DataException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserServiceTest {
    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private UserRepository userRepositoriesTest;

        @BeforeEach
    void setUp(){
        userRepositoriesTest.deleteAll();
    }
    @AfterEach

    void cleanUp(){
        userRepositoriesTest.deleteAll();
    }
    @Test
    public void testRegisterUser(){
        UserRegisterDTO userRegisterDTo = userDTO();
         userService.registerUser(userRegisterDTo);
        assertEquals(1, userRepositoriesTest.count());
    }
    @Test
    public void testUserRegisterWithIncorrectData(){
        UserRegisterDTO userRegisterDTo = new UserRegisterDTO();

        Exception exception = assertThrows(NullPointerException.class, () -> {
            userService.registerUser(userRegisterDTo);
        });
        String expectedMessage = "Cannot invoke";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
    @Test
    public void UserRegisterWithExistingData(){
       userService.registerUser(userDTO());
        assertThrows(UserIsAlreadyExistExp.class, () -> {
            userService.registerUser(userDTO());
        });


    }



    private UserRegisterDTO userDTO(){
        UserRegisterDTO userRegisterDTo = new UserRegisterDTO();
        userRegisterDTo.setUsername("Angel zlatkov");
        userRegisterDTo.setPassword("1324");
        userRegisterDTo.setConfirmPassword("1324");
        return userRegisterDTo;
    }

}
