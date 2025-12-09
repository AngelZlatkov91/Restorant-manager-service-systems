package menu_service.menu_service.Event.Listener;

import Inventory.menu.ChangeStatusItem;
import lombok.extern.slf4j.Slf4j;
import menu_service.menu_service.Services.MenuItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ListenerEvent {
    private final MenuItemService menuItemService;

    @Autowired
    public ListenerEvent(MenuItemService menuItemService) {
        this.menuItemService = menuItemService;
    }

    @KafkaListener(topics = "inventory-change-status",groupId = "menu-service")
    public void itemChangeStatus(ChangeStatusItem item) {
        menuItemService.changeStatus(item);
    }

}
