package api;

import model.Customer;
import model.IRoom;
import model.Reservation;
import service.customerService;
import service.reservationService;

import java.util.Collection;
import java.util.Date;

public class HotelResource {

    private static HotelResource INSTANCE;
    private static final customerService customerService = customerService.getInstance();
    private static final reservationService reservationService = service.reservationService.getInstance();

    private HotelResource(){
    }
    public static HotelResource getInstance(){
        if (INSTANCE == null) {
            INSTANCE = new HotelResource();
        }
        return INSTANCE;
    }

    public Customer getGuest(String email){
        return customerService.getGuest(email);
    }
    public void createAGuest(String email, String firstName, String lastName){
        customerService.addGuest(email, firstName, lastName);
    }
    public IRoom getARoom(String roomNumber){
        return reservationService.getARoom(roomNumber);
    }
    public Reservation bookARoom(String guestEmail, IRoom room, Date checkInDate, Date checkOutDate){
        Customer customer = this.getGuest(guestEmail);
        Reservation reservationNew = null;
        try {
            reservationNew = reservationService.reserveARoom(customer, room, checkInDate, checkOutDate);
        } catch (IllegalArgumentException exp){
            System.out.println(exp.getLocalizedMessage());
        }
        return reservationNew;
    }
    public Collection<Reservation> getGuestsReservations(String guestEmail){
        Customer customer = this.getGuest(guestEmail);
        return reservationService.getGuestReservation(customer);
    }
    public Collection<IRoom> findARoom(Date checkIn, Date checkOut){
        return reservationService.findRooms(checkIn, checkOut);
    }
}
