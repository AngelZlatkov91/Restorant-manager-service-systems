package menu_service.menu_service.Exception;

public class MenuItemDontExistExp extends RuntimeException {
    public MenuItemDontExistExp(String message) {
        super(message);
    }
}
