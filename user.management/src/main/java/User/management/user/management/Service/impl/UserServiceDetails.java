package User.management.user.management.Service.impl;

import User.management.user.management.Models.Entitys.User;
import User.management.user.management.Repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceDetails implements UserDetailsService {

    private final UserRepository userRepository;

    public UserServiceDetails(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> user = userRepository.findByUsername(username);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("User not found with " + username);
        }
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.get().getUsername())
                .password(user.get().getPassword())
                .roles(user.get().getRole().toString())
                .build();
    }
}
