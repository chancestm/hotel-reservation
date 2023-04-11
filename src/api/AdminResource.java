package api;

import model.Customer;
import model.IRoom;
import service.customerService;
import service.reservationService;

import java.util.Collection;
import java.util.List;

public class AdminResource {
    private static AdminResource INSTANCE;
    private static final customerService customerService = customerService.getInstance();
    private static final reservationService reservationService = service.reservationService.getInstance();

    private AdminResource(){

    }
    public static AdminResource getInstance(){
        if (INSTANCE == null) {
            INSTANCE = new AdminResource();
        }
        return INSTANCE;
    }

    public Customer getGuest(String email){
       return customerService.getGuest(email);
    }
    public void addRoom(List<IRoom> rooms){
        for (IRoom room : rooms){
            reservationService.addRoom(room);
        }
    }
    public Collection<IRoom> getAllRooms(){
       return reservationService.printAllRooms();
    }
    public Collection<Customer> getAllGuests(){ return customerService.getAllGuests();}
    public void displayAllReservations(){
        reservationService.printAllReservations();
    }
}
