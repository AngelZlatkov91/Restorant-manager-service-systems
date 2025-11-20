package User.management.user.management.Repositoriestest;

import User.management.user.management.Models.Entitys.User;
import User.management.user.management.Models.Enum.Role;
import User.management.user.management.Repositories.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class UserRepositoriesTest {
    @Autowired
    UserRepository userRepositories;
    private User user;

    @BeforeEach
    public void setUp() {
        user = new User();
        user.setUsername("Angel Ivanov");
        user.setPassword("1234");
        user.setRole(Role.ADMIN);
        userRepositories.save(user);
    }

    @AfterEach
    public void tearDown(){
        userRepositories.delete(user);
    }


    @Test
    public void UserFindByUserName(){
        String username = "Angel Ivanov";
        Optional<User> byEmail = userRepositories.findByUsername(username);
        assertEquals(username, byEmail.get().getUsername());
    }


    @Test
    public void AddSameUser() {
        userRepositories.save(user);
        long count = userRepositories.count();
        assertEquals(1, count);
    }



    @Test
    public void throwUserWithExistEmail(){
        User user1 = difrenUser();
        user1.setUsername("Angel Ivanov");
        Exception exception = assertThrows(DataIntegrityViolationException.class, () -> {
            userRepositories.save(user1);
        });
        String expectedMessage = "could not execute statement";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));

    }





    private User difrenUser(){
        User user1 = new User();
        user1.setUsername("Angel");
        user1.setPassword("1234");
        user1.setRole(Role.MANAGER);
        return user1;
    }
}
