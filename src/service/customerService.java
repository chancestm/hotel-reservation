package service;

import model.Customer;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class customerService {
    private static customerService INSTANCE;
    private static final Map<String, Customer> guests = new HashMap<>();
    private customerService(){

    }
    public static customerService getInstance(){
        if(INSTANCE == null) {
            INSTANCE = new customerService();
        }
        return INSTANCE;
    }

    public void addGuest(String email, String firstName, String lastName){
        try{
            Customer customer = new Customer(firstName, lastName, email);
            guests.put(email, customer);
        } catch (IllegalArgumentException exp) {
            System.out.println("Please enter a valid email address.");
        }
    }
    public Customer getGuest(String guestEmail){
        return guests.get(guestEmail);
    };

    public Collection<Customer> getAllGuests() {
        return guests.values();
    }

}
