package menus;

public enum optionsMain {
    findRoom("1. Find and reserve a room"),
    seeReservations("2. See my reservations"),
    createAccount("3. Create an account"),
    admin("4. Admin Tools"),
    exit("5. Exit");

    private String menuTitle;
    private optionsMain(String menuTitle){
        this.menuTitle = menuTitle;
    }
    public String getMenuTitle() {
        return menuTitle;
    }
}
