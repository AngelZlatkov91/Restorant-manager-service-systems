package menu_service.menu_service.Repositories;

import menu_service.menu_service.Models.Category;
import menu_service.menu_service.Models.MenuItem;
import menu_service.menu_service.Models.TypeProduct;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest
class MenuItemRepositoryTest {

    @Autowired
    private MenuItemRepository menuItemRepository;

    private MenuItem menuItem1;
    private MenuItem menuItem2;
    private Category category1;
    private Category category2;




    @BeforeEach
    void setUp() {
        category1 = new Category(null,"PIZZA");
        category2 = new Category(null,"BEER");
       menuItemRepository.deleteAll();
       menuItem1 = new MenuItem(null,"POLO",10.50,category1, TypeProduct.KITCHEN,true);
       menuItem2 = new MenuItem(null,"MADRI",5.00,category2, TypeProduct.BAR,true);
       menuItemRepository.save(menuItem1);
       menuItemRepository.save(menuItem2);
    }

    @Test
    void testFindAll() {
        List<MenuItem> menuItems = menuItemRepository.findAll();
        assertEquals(2,menuItems.size());
    }

    @Test
    void testFindByCategory() {
        List<MenuItem> byCategory = menuItemRepository.findByCategory(category1);
        MenuItem menuItem = byCategory.get(0);
        assertEquals("POLO",menuItem.getName());
    }

    @Test
    void testFindByMenuItemName() {
        Optional<MenuItem> itemByName = menuItemRepository.findByName("MADRI");
        assertTrue(itemByName.isPresent());
    }

}