package menu_service.menu_service.Services;

import menu_service.menu_service.Event.Consumer.CheckItemEvent;
import menu_service.menu_service.Event.InventoryDTO;
import menu_service.menu_service.Event.InventoryEvent;
import menu_service.menu_service.Event.Listener.ChangeStatusItem;
import menu_service.menu_service.Exception.MenuItemDontExistExp;
import menu_service.menu_service.Models.Category;
import menu_service.menu_service.Models.DTO.MenuItemCreate;
import menu_service.menu_service.Models.DTO.MenuItemRes;
import menu_service.menu_service.Models.DTO.ResStatus;
import menu_service.menu_service.Models.MenuItem;
import menu_service.menu_service.Models.TypeProduct;
import menu_service.menu_service.Repositories.CategoryItemRepository;
import menu_service.menu_service.Repositories.MenuItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class MenuItemServiceImplTest {

    private MenuItemRepository menuItemRepository;
    private CategoryItemRepository categoryItemRepository;
    private InventoryEvent inventoryEvent;
    private MenuItemServiceImpl service;

    @BeforeEach
    void setUp() {
        menuItemRepository = mock(MenuItemRepository.class);
        categoryItemRepository = mock(CategoryItemRepository.class);
        inventoryEvent = mock(InventoryEvent.class);
        service = new MenuItemServiceImpl(menuItemRepository, categoryItemRepository, inventoryEvent);
    }

    @Test
    void createMenuItem_whenCategoryMissing_shouldThrow() {
        MenuItemCreate dto = new MenuItemCreate();
        dto.setName("Coke");
        dto.setPrice(2.5);
        dto.setCategory("Drinks");
        dto.setTypeProduct(TypeProduct.BAR); // irrelevant here

        when(categoryItemRepository.findByName("Drinks")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.createMenuItem(dto))
                .isInstanceOf(MenuItemDontExistExp.class)
                .hasMessageContaining("Category does not exist");

        verify(menuItemRepository, never()).save(any());
        verifyNoInteractions(inventoryEvent);
    }

    @Test
    void createMenuItem_whenBar_shouldSendCreateEvent() {
        MenuItemCreate dto = new MenuItemCreate();
        dto.setName("Whisky");
        dto.setPrice(15.0);
        dto.setCategory("Drinks");
        dto.setTypeProduct(TypeProduct.BAR);


        Category cat = new Category("catId", "Drinks");
        when(categoryItemRepository.findByName("Drinks")).thenReturn(Optional.of(cat));

        ArgumentCaptor<MenuItem> captor = ArgumentCaptor.forClass(MenuItem.class);
        when(menuItemRepository.save(any(MenuItem.class))).thenAnswer(invocation -> {
            MenuItem mi = invocation.getArgument(0);
            mi.setId("generatedId");
            return mi;
        });

        String res = service.createMenuItem(dto);

        assertThat(res).isEqualTo("Created");
        verify(menuItemRepository, times(1)).save(captor.capture());
        MenuItem saved = captor.getValue();
        assertThat(saved.getName()).isEqualTo("Whisky").isNotNull();
        assertThat(saved.getName()).isEqualTo("Whisky");
        assertThat(saved.getCategory()).isEqualTo(cat);


    }

    @Test
    void getAll_shouldMapToRes() {
        MenuItem m1 = new MenuItem();
        m1.setId("1");
        m1.setName("Pizza");
        m1.setPrice(10.0);
        m1.setActive(true);
        m1.setCategory(new Category("c1", "Food"));

        MenuItem m2 = new MenuItem();
        m2.setId("2");
        m2.setName("Cola");
        m2.setPrice(2.0);
        m2.setActive(true);
        m2.setCategory(new Category("c2", "Drinks"));

        when(menuItemRepository.findByActive(true)).thenReturn(List.of(m1, m2));

        List<MenuItemRes> res = service.getAll();

        assertThat(res).hasSize(2);
        assertThat(res.get(0).getId()).isEqualTo("1");
        assertThat(res.get(0).getCategory()).isEqualTo("Food");
        assertThat(res.get(1).getCategory()).isEqualTo("Drinks");
    }

    @Test
    void getAllByCategory_whenCategoryMissing_shouldThrow() {
        when(categoryItemRepository.findByName("NonExist")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.getAllByCategory("NonExist"))
                .isInstanceOf(MenuItemDontExistExp.class)
                .hasMessageContaining("This category does not exist");
    }

    @Test
    void getMenuItem_whenNotFound_shouldThrow() {
        when(menuItemRepository.findById("no")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.getMenuItem("no"))
                .isInstanceOf(MenuItemDontExistExp.class)
                .hasMessageContaining("Menu item not found");
    }

    @Test
    void deleteMenuItem_shouldSendDeleteEventAndDelete() {
        Category cat = new Category("c1", "Food");
        MenuItem item = new MenuItem();
        item.setId("5");
        item.setName("Burger");
        item.setTypeProduct(TypeProduct.KITCHEN);
        item.setCategory(cat);

        when(menuItemRepository.findById("5")).thenReturn(Optional.of(item));

        ResStatus status = service.deleteMenuItem("5");

        assertThat(status.getStatus()).isEqualTo("Item is Deleted");

        verify(menuItemRepository, times(1)).deleteById("5");
    }

    @Test
    void updateMenuItem_success() {
        Category catOld = new Category("c1", "Food");
        MenuItem existing = new MenuItem();
        existing.setId("10");
        existing.setName("Old");
        existing.setPrice(5.0);
        existing.setActive(true);
        existing.setCategory(catOld);

        MenuItemRes update = new MenuItemRes();
        update.setId("10");
        update.setName("NewName");
        update.setPrice(6.5);
        update.setActive(false);
        update.setCategory("Food");

        when(menuItemRepository.findById("10")).thenReturn(Optional.of(existing));
        when(categoryItemRepository.findByName("Food")).thenReturn(Optional.of(catOld));
        when(menuItemRepository.save(any(MenuItem.class))).thenReturn(existing);
        when(menuItemRepository.findById("10")).thenReturn(Optional.of(existing)); // for getMenuItem inside update

        MenuItemRes result = service.updateMenuItem(update);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo("10");
        assertThat(result.getName()).isEqualTo("NewName");
        assertThat(result.isActive()).isFalse();
        verify(menuItemRepository, atLeastOnce()).save(any(MenuItem.class));
    }

    @Test
    void changeStatus_whenItemExists_shouldUpdate() {
        MenuItem item = new MenuItem();
        item.setId("x");
        item.setName("Cola");
        item.setActive(true);

        when(menuItemRepository.findByName("Cola")).thenReturn(Optional.of(item));

        ChangeStatusItem ev = new ChangeStatusItem();
        ev.setItemName("Cola");


        service.changeStatus(ev);

        verify(menuItemRepository, times(1)).save(item);
        assertThat(item.isActive()).isFalse();
    }
}
