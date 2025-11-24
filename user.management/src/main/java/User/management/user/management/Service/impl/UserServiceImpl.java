package User.management.user.management.Service.impl;

import User.management.user.management.Exception.UserIsAlreadyExistExp;
import User.management.user.management.Models.DTO.UserRegisterDTO;
import User.management.user.management.Models.Entitys.User;
import User.management.user.management.Models.Enum.Role;
import User.management.user.management.Repositories.UserRepository;
import User.management.user.management.Service.Interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private  final PasswordEncoder passwordEncoder;


     @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
         this.passwordEncoder = passwordEncoder;

     }

    @Override
    public void registerUser(UserRegisterDTO userRegisterDTO) {
        User user = registerUserMap(userRegisterDTO);
        userRepository.save(user);
    }

    private User registerUserMap(UserRegisterDTO userRegisterDTO) {
        User user = new User();
        if (userRepository.findByUsername(userRegisterDTO.getUsername()).isPresent()) {
            throw new UserIsAlreadyExistExp("User already exist");
        }
       user.setUsername(userRegisterDTO.getUsername());
        user.setPassword(passwordEncoder.encode(userRegisterDTO.getPassword()));
        if (userRepository.count() == 0) {
            user.setRole(Role.ADMIN);
        } else {
            user.setRole(Role.PERSONAL);
        }
        return user;
    }
}
