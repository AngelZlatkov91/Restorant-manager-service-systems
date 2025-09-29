package menu_service.menu_service.Services;

import menu_service.menu_service.Event.Consumer.CheckItemEvent;
import menu_service.menu_service.Event.InventoryDTO;
import menu_service.menu_service.Event.InventoryEvent;
import menu_service.menu_service.Exception.MenuItemDontExistExp;
import menu_service.menu_service.Models.Category;
import menu_service.menu_service.Models.DTO.MenuItemCreate;
import menu_service.menu_service.Models.DTO.MenuItemRes;
import menu_service.menu_service.Models.MenuItem;
import menu_service.menu_service.Repositories.CategoryItemRepository;
import menu_service.menu_service.Repositories.MenuItemRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service

public class MenuItemServiceImpl implements MenuItemService {

    private final MenuItemRepository menuItemRepository;

    private final CategoryItemRepository categoryItemRepository;
    private final InventoryEvent inventoryEvent;

    public MenuItemServiceImpl(MenuItemRepository menuItemRepository, CategoryItemRepository categoryItemRepository, InventoryEvent inventoryEvent) {
        this.menuItemRepository = menuItemRepository;
        this.categoryItemRepository = categoryItemRepository;
        this.inventoryEvent = inventoryEvent;
    }

    @Override
    public String createMenuItem(MenuItemCreate menuItem) {

        Optional<Category> category = categoryItemRepository.findByName(menuItem.getCategory());;
        MenuItem newItem = mapData(menuItem);
        newItem.setCategory(category.get());
        menuItemRepository.save(newItem);
        inventoryEvent.sendEvent(new InventoryDTO(newItem.getName(), newItem.getCategory().getName()));
        return "Saved";
    }


    private MenuItem mapData(MenuItemCreate menuItem) {
        MenuItem item = new MenuItem();
        item.setName(menuItem.getName());
        item.setPrice(menuItem.getPrice());
        item.setTypeProduct(menuItem.getTypeProduct());
        item.setActive(true);
        return item;
    }

    @Override
    public List<MenuItemRes> getAll() {
        return this.menuItemRepository
                .findByActive(true)
                .stream()
                .map(this::mapToRes)
                .collect(Collectors.toList());
    }

    @Override
    public List<MenuItemRes> getAllByCategory(String category) {
        Optional<Category> category1 = categoryItemRepository.findByName(category);
        if (category1.isEmpty()) {
            throw new MenuItemDontExistExp("This category does not exist");
        }
        return this.menuItemRepository
                .findByCategory(category1.get())
                .stream()
                .map(this::mapToRes)
                .collect(Collectors.toList());
    }

    private MenuItemRes mapToRes(MenuItem menuItem) {
        MenuItemRes item = new MenuItemRes();
        item.setId(menuItem.getId());
        item.setName(menuItem.getName());
        item.setPrice(menuItem.getPrice());
        item.setTypeProduct(menuItem.getTypeProduct());
        item.setActive(menuItem.isActive());
        item.setCategory(menuItem.getCategory().getName());
        return item;
    }

    @Override
    public MenuItemRes getMenuItem(String menuItemId) {
        MenuItem menuItem = this.menuItemRepository.findById(menuItemId).orElse(null);
        if (menuItem == null) {
            throw  new MenuItemDontExistExp("Menu item not found");
        }

        return mapToRes(menuItem);
    }

    @Override
    @Transactional
    public void deleteMenuItem(String menuItemId) {
       menuItemRepository.deleteById(menuItemId);
    }

    @Override
    @Transactional
    public MenuItemRes updateMenuItem( MenuItemRes menuItemRes) {
        Optional<MenuItem> byId =
                menuItemRepository.findById(menuItemRes.getId());
        Optional<Category> byName = categoryItemRepository.findByName(menuItemRes.getCategory());
        if (byId.isEmpty() || byName.isEmpty()) {
            throw new MenuItemDontExistExp("Menu item not found");
        }
           byId.get().setName(menuItemRes.getName());
           byId.get().setActive(menuItemRes.isActive());
           byId.get().setPrice(menuItemRes.getPrice());
           byId.get().setCategory(byName.get());
           menuItemRepository.save(byId.get());
        return getMenuItem(byId.get().getId());
    }

    @Override
    @Transactional
    public void changeStatus(CheckItemEvent checkItemEvent) {
        Optional<MenuItem> byName = menuItemRepository.findByName(checkItemEvent.getItemName());
        if (byName.isPresent()) {
            byName.get().setActive(checkItemEvent.isStatus());
            menuItemRepository.save(byName.get());
        }
    }
}
