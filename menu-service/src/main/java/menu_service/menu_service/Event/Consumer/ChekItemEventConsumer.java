package menu_service.menu_service.Event.Consumer;


import lombok.extern.slf4j.Slf4j;
import menu_service.menu_service.Services.MenuItemService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ChekItemEventConsumer {
    private final MenuItemService menuItemService;

    public ChekItemEventConsumer(MenuItemService menuItemService) {
        this.menuItemService = menuItemService;
    }

    @KafkaListener(topics = "chek-item-quantity", groupId = "item")
    public void consume(CheckItemEvent message){
        if (!message.getItemName().isEmpty()) {
            menuItemService.changeStatus(message);
        }
    }
}
