package menu_service.menu_service.Repositories;

import menu_service.menu_service.Models.Category;
import menu_service.menu_service.Models.MenuItem;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface MenuItemRepository extends MongoRepository<MenuItem, String> {
    List<MenuItem> findByCategory(Category category);
    List<MenuItem> findByActive(boolean active);

   Optional<MenuItem> findByName(String name);
}
