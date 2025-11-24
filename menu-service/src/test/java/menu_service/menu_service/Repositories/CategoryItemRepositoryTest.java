package menu_service.menu_service.Repositories;

import menu_service.menu_service.Models.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest
class CategoryItemRepositoryTest {

    @Autowired
    private CategoryItemRepository categoryItemRepository;

    private Category category1;
    private Category category2;

    @BeforeEach
    void setUp() {
        categoryItemRepository.deleteAll();
        category1 = new Category(null,"PIZZA");
        category2 = new Category(null,"BEER");
        categoryItemRepository.save(category1);
        categoryItemRepository.save(category2);
    }

    @Test
    void testFindAll() {
        List<Category> categories = categoryItemRepository.findAll();
        assertEquals(2,categories.size());
    }

    @Test
    void testFindByName() {
        Optional<Category> pizza = categoryItemRepository.findByName("PIZZA");
        assertTrue(pizza.isPresent());
    }

    @Test
    void testIsExistCategory() {
        boolean beer = categoryItemRepository.existsByName("BEER");
        assertTrue(beer);
    }

    @Test
    void testNotExistCategory() {
        boolean notExist = categoryItemRepository.existsByName("NotExist");
        assertFalse(notExist);
    }



}