import menuApi from "../api/menu-api";

export function useCreateMenuItem() {
    const menuItem = (data) => menuApi.createMenuItem(data);
    
    return menuItem;
}