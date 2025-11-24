package User.management.user.management.Service.impl;

import User.management.user.management.Exception.UserDontExistExp;
import User.management.user.management.Models.DTO.UserChangeRoleDTO;
import User.management.user.management.Models.DTO.UserDetailsDTO;
import User.management.user.management.Models.Entitys.User;
import User.management.user.management.Repositories.UserRepository;
import User.management.user.management.Service.Interfaces.AdminService;
import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminServiceImpl implements AdminService {

    private final UserRepository userRepository;


    public AdminServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;

    }
    @Override
    public List<UserDetailsDTO> getAllUsers(String username) {
        return   userRepository
                .getAllByUsernameNotContaining(username)
                .stream()
                .map(this::mapTo)
                .toList();
    }

    @Override
    @Transactional
    public void changeProfileRole(UserChangeRoleDTO userChangeRoleDTO) {
        Optional<User> byEmail = userRepository.findByUsername(userChangeRoleDTO.getUsername());
        if (byEmail.isEmpty()) {
            throw new UserDontExistExp("User don't exist!");
        }
        byEmail.get().setRole(userChangeRoleDTO.getRole());
        userRepository.save(byEmail.get());
    }

    @Override
    @Transactional
    public void deleteProfile(Long id) {
        userRepository.deleteById(id);
    }

    private UserDetailsDTO mapTo(User user) {
        return new UserDetailsDTO(user.getId(),user.getUsername(),user.getRole().name());
    }
}
