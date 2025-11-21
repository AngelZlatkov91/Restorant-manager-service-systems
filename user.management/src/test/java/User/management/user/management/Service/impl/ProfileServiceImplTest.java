package User.management.user.management.Service.impl;

import User.management.user.management.Exception.UserDontExistExp;
import User.management.user.management.Models.DTO.UserDetailsDTO;
import User.management.user.management.Models.Entitys.User;
import User.management.user.management.Models.Enum.Role;
import User.management.user.management.Repositories.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class ProfileServiceImplTest {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProfileServiceImpl profileService;

    @BeforeEach
    public void setUp() {
        userRepository.save(saveData());
    }

    @AfterEach
    public void clean () {
        userRepository.deleteAll();
    }

    @Test
    public void getUserDetailsWithExistUserTest() {
        UserDetailsDTO ivan = profileService.getUserDetails("Ivan");
        assertEquals("Ivan", ivan.getUsername());
    }

    @Test
    public void getUserDetailsWithNotExistUserTest() {
        Exception exception = assertThrows(UserDontExistExp.class, () -> profileService.getUserDetails("Test"));

        String expectedMessage = "User with email Test does not exist";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }


    private User saveData() {
        User user = new User();
        user.setUsername("Ivan");
        user.setPassword("1234");
        user.setRole(Role.PERSONAL);
        return user;
    }

}