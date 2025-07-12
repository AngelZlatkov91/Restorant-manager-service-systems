package order.services.order.services.Repositories;

import order.services.order.services.Models.Entitys.Personal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonalRepositories extends JpaRepository<Personal , Long> {

    Optional<Personal> findByName(String name);
}
