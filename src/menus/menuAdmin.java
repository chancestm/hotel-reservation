package menus;

import api.AdminResource;
import model.Customer;
import model.IRoom;
import model.Room;
import model.RoomType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

public class menuAdmin {

    static final AdminResource adminResource = AdminResource.getInstance();

    public static void printOptions(){
        Scanner scanner = new Scanner(System.in);
        String selection = "";
        do{
            try {
                optionsAdmin[] menuOptions = optionsAdmin.values();

                for (optionsAdmin menuOption : menuOptions) {
                    System.out.println(menuOption.getMenuTitle());
                }
                System.out.println("Please select from one of the above options: ");
                selection = scanner.next();
                int selNum = Integer.parseInt(selection);

                switch (optionsAdmin.values()[selNum-1]) {
                    case seeGuests:
                        seeAllGuests();
                        break;

                    case seeRooms:
                        seeAllRooms();
                        break;

                    case seeReservations:
                        seeAllReservations();
                        break;

                    case addRoom:
                        addARoom();
                        break;

                    case mainMenu:
                        menuMain.printOptions();
                        break;
                    default:
                        System.out.println("Unfortunately, that is not a valid option. Please try again.");
                        break;
                }

            } catch (Exception exp) {
                System.out.println("Please enter an available option.");
            }
            } while (!selection.equals("5"));
        }

    private static void seeAllGuests(){
        Collection<Customer> customers = adminResource.getAllGuests();
        if (!customers.isEmpty()){
            for (Customer customer : customers){
                System.out.println(customer.toString());
            }
        }
        else {
            System.out.println("There are no guests registered.");
        }
    }

    private static void seeAllRooms(){
        Collection<IRoom> rooms = adminResource.getAllRooms();
        if (!rooms.isEmpty()){
            for (IRoom room : rooms){
                System.out.println(room.toString());
            }
        }
        else {
            System.out.println("There are no rooms registered.");
        }
    }

    private static void seeAllReservations(){
        adminResource.displayAllReservations();
    }

    private static void addARoom(){
        List<IRoom> rooms = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter a room number: ");
//        String roomNumber = scanner.next();
//        while (scanner.nextLine() == null){
//            System.out.println("Please enter a value for the room number.");
//        }
        String roomNumber = scanner.next();
        System.out.println("Please enter the occupancy size (either SINGLE or DOUBLE): ");
        String roomString = scanner.next();
        while (!scanner.next().equalsIgnoreCase("SINGLE") && !scanner.next().equalsIgnoreCase("DOUBLE")){
            System.out.println("Please enter either SINGLE or DOUBLE.");
        }
//        String roomString = scanner.next();
        RoomType roomType = null;
        if(roomString == "SINGLE"){
            roomType = RoomType.SINGLE;
        } else{
            roomType = RoomType.DOUBLE;
        }
        System.out.println("Please enter the rate per night: $");
        String doubleFormat = "^[0-9]+/.[0-9]+$";
        String intFormat = "^[0-9]+$";
        while (!scanner.next().matches(doubleFormat) && !scanner.next().matches(intFormat)){
            System.out.println("Please enter only numbers. Ex. #.# or ##");
        }
        Double roomRate = Double.parseDouble(scanner.next());

        Room roomNew = new Room(roomNumber, roomType, roomRate);
        rooms.add(roomNew);
        System.out.println("Would you like to add another room?");
        while (!scanner.next().toUpperCase().equals("YES") && !scanner.next().toUpperCase().equals("NO")){
            System.out.println("Please enter Yes or No.");
        }
        String createAnother = scanner.next().toUpperCase();
        if (createAnother == "YES"){
            addARoom();
        } else{
            menuAdmin.printOptions();
        }

    }

}
