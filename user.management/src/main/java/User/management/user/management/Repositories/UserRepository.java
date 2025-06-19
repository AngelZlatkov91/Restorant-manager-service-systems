package User.management.user.management.Repositories;

import User.management.user.management.Models.Entitys.User;
import User.management.user.management.Models.Enum.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    List<User> getAllByUsernameNotContaining(String username);

    List<User> getAllByUsernameNotContainingAndRoleIn(String username, Collection<Role> roles);
}

