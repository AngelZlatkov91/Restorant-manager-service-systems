package menu_service.menu_service.Services;

import Inventory.menu.InventoryDTO;
import Inventory.menu.ChangeStatusItem;
import menu_service.menu_service.Event.InventoryEvent;
import menu_service.menu_service.Exception.MenuItemDontExistExp;
import menu_service.menu_service.Models.Category;
import menu_service.menu_service.Models.DTO.MenuItemCreate;
import menu_service.menu_service.Models.DTO.MenuItemRes;
import menu_service.menu_service.Models.DTO.ResStatus;
import menu_service.menu_service.Models.MenuItem;
import menu_service.menu_service.Repositories.CategoryItemRepository;
import menu_service.menu_service.Repositories.MenuItemRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MenuItemServiceImpl implements MenuItemService {

    private final MenuItemRepository menuItemRepository;
    private final CategoryItemRepository categoryItemRepository;
    private final InventoryEvent inventoryEvent;

    public MenuItemServiceImpl(MenuItemRepository menuItemRepository,
                               CategoryItemRepository categoryItemRepository,
                               InventoryEvent inventoryEvent) {
        this.menuItemRepository = menuItemRepository;
        this.categoryItemRepository = categoryItemRepository;
        this.inventoryEvent = inventoryEvent;
    }

    @Override
    public String createMenuItem(MenuItemCreate menuItem) {
        Category category = categoryItemRepository.findByName(menuItem.getCategory())
                .orElseThrow(() -> new MenuItemDontExistExp("Category does not exist"));

        MenuItem newItem = mapData(menuItem);
        newItem.setCategory(category);
        newItem.setCreatedAt(LocalDateTime.now());
        newItem.setUpdatedAt(LocalDateTime.now());

        menuItemRepository.save(newItem);

        if (newItem.getTypeProduct() == menuItem.getTypeProduct() &&
                newItem.getTypeProduct().name().equals("BAR")) {
            inventoryEvent.sendItemCreateEvent(new InventoryDTO(
                    newItem.getName(),
                    newItem.getCategory().getName()
            ));
        }

        return "Created";
    }

    private MenuItem mapData(MenuItemCreate menuItem) {
        MenuItem item = new MenuItem();
        item.setName(menuItem.getName());
        item.setPrice(menuItem.getPrice());
        item.setCostPrice(menuItem.getCostPrice());
        if (menuItem.getCostPrice() != null && menuItem.getCostPrice() > 0) {
            double markup = ((menuItem.getPrice() - menuItem.getCostPrice()) / menuItem.getCostPrice()) * 100;
            item.setMarkupPercentage(Math.round(markup * 100.0) / 100.0); // закръглено до 2 десетични
        } else {
            item.setMarkupPercentage(0.0);
        }

        item.setTypeProduct(menuItem.getTypeProduct());
        item.setActive(true);
        return item;
    }

    @Override
    public List<MenuItemRes> getAll() {
        return menuItemRepository.findAll()
                .stream()
                .map(this::mapToRes)
                .collect(Collectors.toList());
    }

    @Override
    public List<MenuItemRes> getAllByCategory(String category) {
        Category cat = categoryItemRepository.findByName(category)
                .orElseThrow(() -> new MenuItemDontExistExp("This category does not exist"));

        return menuItemRepository.findByCategory(cat)
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
        item.setCategory(menuItem.getCategory() != null ? menuItem.getCategory().getName() : null);
        item.setCostPrice(menuItem.getCostPrice());
        item.setMarkupPercentage(menuItem.getMarkupPercentage());
        item.setCreatedAt(menuItem.getCreatedAt());
        item.setUpdatedAt(menuItem.getUpdatedAt());
        return item;
    }

    @Override
    public MenuItemRes getMenuItem(String menuItemId) {
        MenuItem menuItem = menuItemRepository.findById(menuItemId)
                .orElseThrow(() -> new MenuItemDontExistExp("Menu item not found"));
        return mapToRes(menuItem);
    }

    @Override
    @Transactional
    public ResStatus deleteMenuItem(String menuItemId) {
        MenuItem item = menuItemRepository.findById(menuItemId)
                .orElseThrow(() -> new MenuItemDontExistExp("Menu item not found"));

        if (item.getTypeProduct() != null && item.getTypeProduct().name().equals("BAR")) {
            inventoryEvent.sendItemDeleteEvent(new InventoryDTO(item.getName(), item.getCategory().getName()));
        }

        menuItemRepository.deleteById(menuItemId);
        return new ResStatus("Item is Deleted");
    }

    @Override
    @Transactional
    public MenuItemRes updateMenuItem(MenuItemRes menuItemRes) {
        MenuItem existing = menuItemRepository.findById(menuItemRes.getId())
                .orElseThrow(() -> new MenuItemDontExistExp("Menu item not found"));

        Category category = categoryItemRepository.findByName(menuItemRes.getCategory())
                .orElseThrow(() -> new MenuItemDontExistExp("Category not found"));

        existing.setName(menuItemRes.getName());
        existing.setActive(menuItemRes.isActive());
        existing.setPrice(menuItemRes.getPrice());
        existing.setCostPrice(menuItemRes.getCostPrice());
        if (menuItemRes.getCostPrice() != null && menuItemRes.getCostPrice() > 0) {
            double markup = ((menuItemRes.getPrice() - menuItemRes.getCostPrice()) / menuItemRes.getCostPrice()) * 100;
            existing.setMarkupPercentage(Math.round(markup * 100.0) / 100.0);
        } else {
            existing.setMarkupPercentage(0.0);
        }
        existing.setMarkupPercentage(menuItemRes.getMarkupPercentage());
        existing.setCategory(category);
        existing.setUpdatedAt(LocalDateTime.now());

        menuItemRepository.save(existing);

        return getMenuItem(existing.getId());
    }

    @Override
    @Transactional
    public void changeStatus(ChangeStatusItem checkItemEvent) {
        Optional<MenuItem> byName = menuItemRepository.findByName(checkItemEvent.getItemName());
        if (byName.isPresent()) {
            byName.get().setActive(checkItemEvent.isActive());
            menuItemRepository.save(byName.get());
        }
    }
}
