package menus;

import api.HotelResource;
import model.Customer;
import model.IRoom;
import model.Reservation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Scanner;

public class menuMain {

    static final HotelResource hotelResource = HotelResource.getInstance();
    private static final String DEFAULT_DATE_FORMAT = "MM/dd/yyyy";

    public static void printOptions(){
        Scanner scanner = new Scanner(System.in);
        String selection = "";
        do {
            try {
                optionsMain[] menuOptions = optionsMain.values();

                for (optionsMain menuOption : menuOptions) {
                    System.out.println(menuOption.getMenuTitle());
                }
                System.out.println("Please select from one of the above options: ");
                selection = scanner.next();
                int selNum = Integer.parseInt(selection);

                switch (optionsMain.values()[selNum-1]) {
                    case findRoom:
                        bookARoom();
                        break;

                    case seeReservations:
                        findMyReservation();
                        break;

                    case createAccount:
                        createAccount();
                        break;

                    case admin:
                        menuAdmin.printOptions();
                        break;

                    case exit:
                        System.out.println("Thank you and have a great day!");
                        break;

                    default:
                        System.out.println("Unfortunately, that is not a valid option. Please try again.");
                        break;
                }
            } catch (Exception exp) {
                System.out.println("Please enter an available option.");
            }
        }while (!selection.equals("4") && !selection.equals("5"));
    }

    private static void bookARoom(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter Check-In Date (mm/dd/yyy): ");
        Date checkIn = getValidDate(scanner);
        System.out.println("Please enter Check-Out Date (mm/dd/yyy): ");
        Date checkOut = getValidDate(scanner);
        Collection<IRoom> roomsAvailable = hotelResource.findARoom(checkIn,checkOut);
        boolean wantsBooking = false;
        if (roomsAvailable.isEmpty()){
            Date checkInAlt = getAltDate(checkIn);
            Date checkoutAlt = getAltDate(checkOut);
            roomsAvailable = hotelResource.findARoom(checkInAlt, checkoutAlt);
            if (roomsAvailable.isEmpty()){
                System.out.println("There are no rooms available for your dates or within 7 days of your desired check in.");
            }
            else {
                System.out.println("There are no available rooms for your desired check in. However, there are" +
                        "rooms available starting on " + checkInAlt);
                wantsBooking = askToBook(scanner, roomsAvailable);
                checkIn = checkInAlt;
                checkOut = checkoutAlt;
            }
        } else {
            System.out.println("There are available rooms between " + checkIn + " and " + checkOut);
            wantsBooking = askToBook(scanner, roomsAvailable);
        }
        if (!wantsBooking) {
            menuMain.printOptions();
        }
        System.out.println("Please enter your email address: ");
        Customer customer = accountCheck(scanner);
        IRoom room = validRoom(scanner, roomsAvailable);
        Reservation reservationNew = hotelResource.bookARoom(customer.getEmail(),room,checkIn,checkOut);
        if (reservationNew==null){
            System.out.println("Could not complete booking. Please contact Support.");
        } else {
            System.out.println("Thank you for your reservation. Please see details:\n");
            System.out.println(reservationNew.toString());
        }


    }

    private static void findMyReservation(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter your email: ");
        String email = scanner.next();
        hotelResource.getGuestsReservations(email);
    }
    private static void createAccount(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter your email: ");
        String email = scanner.next();
        System.out.println("Please enter your first name: ");
        String firstName = scanner.next();
        System.out.println("Please enter your last name: ");
        String lastName = scanner.next();
        hotelResource.createAGuest(email, firstName, lastName);
    }

    private static Date getValidDate(Scanner scanner){
        SimpleDateFormat dateFormat = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
        try{
            return dateFormat.parse(scanner.next());
        } catch (ParseException exp){
            System.out.println("Please enter a valid date.");
            bookARoom();
        }
        return null;
    }

    private static Date getAltDate(Date date){
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, 7);
        return c.getTime();
    } //heavily relied on this source: https://mkyong.com/java/java-how-to-add-days-to-current-date/

    private static boolean askToBook(Scanner scanner, Collection<IRoom> roomsAvailable){
        for (IRoom room : roomsAvailable) {
            System.out.println(room.toString());
        }
        System.out.println("\n Would you like to book? Please enter Yes or No: ");
        while (!scanner.next().toUpperCase().equals("Yes") || !scanner.next().toUpperCase().equals("NO")){
            System.out.println("Please enter Yes or No.");
        }
        String book = scanner.next().toUpperCase();
        if (book.equals("YES")){
            return true;
        } else{
            return false;
        }
    }
    private static Customer accountCheck(Scanner scanner){
        boolean hasAccount = false;
        String email = scanner.next();
        if (hotelResource.getGuest(email)==null){
            System.out.println("Please enter your first name: ");
            String firstName = scanner.next();
            System.out.println("Please enter your last name: ");
            String lastName = scanner.next();
            hotelResource.createAGuest(email, firstName, lastName);
        }
        return hotelResource.getGuest(email);
    }

    private static IRoom validRoom(Scanner scanner, Collection<IRoom> roomsAvailable){
        boolean validRoomNum = false;
        IRoom room = null;
        while(!validRoomNum){
            System.out.println("What room would you like? Please enter the number: ");
            String roomNumber = scanner.next();
            room = hotelResource.getARoom(roomNumber);
            if (room == null){
                System.out.println("Please enter a valid room number.");
            } else {
                if (!roomsAvailable.contains(room)){
                    System.out.println("That room is no longer available. Please try again.");
                } else {
                    validRoomNum = true;
                }
            }
        }
        return room;
    }

}
