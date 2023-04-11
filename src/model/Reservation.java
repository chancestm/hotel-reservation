package model;

import java.util.Date;

public class Reservation {

    private Customer customer;
    private IRoom room;
    private Date checkinDate;
    private Date checkoutDate;

    public Reservation(Customer customer, IRoom room, Date checkinDate, Date checkoutDate) {
        this.customer = customer;
        this.room = room;
        this.checkinDate = checkinDate;
        this.checkoutDate = checkoutDate;
    }

    public Customer getGuest() {
        return customer;
    }

    public IRoom getRoom() {
        return room;
    }

    public Date getCheckinDate() {
        return checkinDate;
    }

    public Date getCheckoutDate() {
        return checkoutDate;
    }

    @Override
    public String toString(){
        return "Room #: " + room +
                " | " + customer +
                " | Check In:" + checkinDate +
                " Checkout: " + checkoutDate;
    }

}
