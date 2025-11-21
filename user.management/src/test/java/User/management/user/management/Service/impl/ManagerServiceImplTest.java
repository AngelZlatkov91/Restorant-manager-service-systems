package User.management.user.management.Service.impl;

import User.management.user.management.Exception.UserDontExistExp;
import User.management.user.management.Models.DTO.UserChangeRoleDTO;
import User.management.user.management.Models.DTO.UserDetailsDTO;
import User.management.user.management.Models.Entitys.User;
import User.management.user.management.Models.Enum.Role;
import User.management.user.management.Repositories.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ManagerServiceImplTest {
    @Autowired
    private ManagerServiceImpl managerService;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {

        userRepository.saveAll(addSomeUsers());
    }

    @AfterEach
    public void cleanUp() {
        userRepository.deleteAll();
    }

    @Test
    public void TestGetAllUsersOnlyPersonals() {
        List<UserDetailsDTO> manager = managerService.getAllUsers("Manager");
        assertEquals(2,manager.size());
    }

    @Test
    public void TestChangeProfileRole() {
        UserChangeRoleDTO userChangeRoleDTO = new UserChangeRoleDTO("personal 1", Role.MANAGER);
        managerService.changeProfileRole(userChangeRoleDTO);
        Optional<User> user = userRepository.findByUsername("personal 1");
        assertEquals(Role.MANAGER, user.get().getRole());
    }

    @Test
    public void TestChangeProfileRoleNotExistProfile() {
        UserChangeRoleDTO userChangeRoleDTO = new UserChangeRoleDTO("Not Exist", Role.MANAGER);


        Exception exception = assertThrows(UserDontExistExp.class, () -> managerService.changeProfileRole(userChangeRoleDTO));

        String expectedMessage = "User don't exist!";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void TestDeleteProfile() {
        List<User> all = userRepository.findAll();
        User user = all.get(1);

        managerService.deleteProfile(user.getId());
        Optional<User> notExist = userRepository.findById(user.getId());

        assertTrue(notExist.isEmpty());
    }




    private List<User> addSomeUsers() {
        User user = new User();
        user.setUsername("Manager");
        user.setPassword("132456");
        user.setRole(Role.MANAGER);
        User user1 = new User();
        user1.setUsername("personal 1");
        user1.setPassword("132456");
        user1.setRole(Role.PERSONAL);
        User user2 = new User();
        user2.setUsername("personal 2");
        user2.setPassword("132456");
        user2.setRole(Role.PERSONAL);
        return List.of(user,user1,user2);
    }

}