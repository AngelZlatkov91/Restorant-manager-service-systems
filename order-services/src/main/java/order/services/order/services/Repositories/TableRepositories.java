package order.services.order.services.Repositories;

import order.services.order.services.Models.Entitys.TableEn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TableRepositories extends JpaRepository<TableEn, Long> {


    Optional<TableEn> findByTableName(String name);
}
