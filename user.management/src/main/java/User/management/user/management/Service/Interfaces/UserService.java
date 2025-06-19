package User.management.user.management.Service.Interfaces;

import User.management.user.management.Models.DTO.UserDetailsDTO;
import User.management.user.management.Models.DTO.UserRegisterDTO;

public interface UserService {

    void registerUser(UserRegisterDTO userRegisterDTO);



    void updateProfile(UserDetailsDTO userDetailsDTO);




}
