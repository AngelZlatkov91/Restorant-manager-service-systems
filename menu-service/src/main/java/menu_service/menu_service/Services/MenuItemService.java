package menu_service.menu_service.Services;

import menu_service.menu_service.Event.Consumer.CheckItemEvent;
import menu_service.menu_service.Event.Listener.ChangeStatusItem;
import menu_service.menu_service.Models.DTO.MenuItemCreate;
import menu_service.menu_service.Models.DTO.MenuItemRes;
import menu_service.menu_service.Models.DTO.ResStatus;

import java.util.List;


public interface MenuItemService {

    String createMenuItem(MenuItemCreate menuItem);

    List<MenuItemRes> getAll();

    List<MenuItemRes> getAllByCategory(String category);


    MenuItemRes getMenuItem(String menuItemId);

    ResStatus deleteMenuItem(String menuItemId);

    MenuItemRes updateMenuItem( MenuItemRes menuItemRes);

    void changeStatus(ChangeStatusItem checkItemEvent);
}
