package Restaurant.service.managment.Inventory.Service.Repo;

import Restaurant.service.managment.Inventory.Service.Models.Inventory;
import Restaurant.service.managment.Inventory.Service.Models.InventorytODTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InventoryRepositories extends JpaRepository<Inventory, Long> {


    Optional<Inventory> findByName(String name);
}
