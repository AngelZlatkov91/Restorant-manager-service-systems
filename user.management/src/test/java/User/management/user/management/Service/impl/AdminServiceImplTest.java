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
class AdminServiceImplTest {
    @Autowired
    private AdminServiceImpl adminService;

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
    public void TestGetAllUsersOnlyPersonalsAndManager() {
        List<UserDetailsDTO> manager = adminService.getAllUsers("Admin");
        assertEquals(2,manager.size());
    }

    @Test
    public void TestChangeProfileRole() {
        UserChangeRoleDTO userChangeRoleDTO = new UserChangeRoleDTO("personal 1", Role.ADMIN);
        adminService.changeProfileRole(userChangeRoleDTO);
        Optional<User> user = userRepository.findByUsername("personal 1");
        assertEquals(Role.ADMIN, user.get().getRole());
    }

    @Test
    public void TestChangeProfileRoleNotExistProfile() {
        UserChangeRoleDTO userChangeRoleDTO = new UserChangeRoleDTO("Not Exist", Role.MANAGER);


        Exception exception = assertThrows(UserDontExistExp.class, () -> adminService.changeProfileRole(userChangeRoleDTO));

        String expectedMessage = "User don't exist!";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void TestDeleteProfile() {
        List<User> all = userRepository.findAll();
        User user = all.get(1);

        adminService.deleteProfile(user.getId());
        Optional<User> notExist = userRepository.findById(user.getId());

        assertTrue(notExist.isEmpty());
    }




    private List<User> addSomeUsers() {
        User user = new User();
        user.setUsername("Admin");
        user.setPassword("132456");
        user.setRole(Role.ADMIN);
        User user1 = new User();
        user1.setUsername("personal 1");
        user1.setPassword("132456");
        user1.setRole(Role.MANAGER);
        User user2 = new User();
        user2.setUsername("personal 2");
        user2.setPassword("132456");
        user2.setRole(Role.PERSONAL);
        return List.of(user,user1,user2);
    }

}