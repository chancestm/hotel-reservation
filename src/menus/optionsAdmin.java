package menus;

public enum optionsAdmin {
    seeGuests("1. See all guests"),
    seeRooms("2. See all rooms"),
    seeReservations("3. See all Reservations"),
    addRoom ("4. Add a room"),
    mainMenu("5. Back to main menu");

    private String menuTitle;
    private optionsAdmin(String menuTitle){
        this.menuTitle = menuTitle;
    }
    public String getMenuTitle() {
        return menuTitle;
    }
}
