package service;

import model.Customer;
import model.IRoom;
import model.Room;
import model.Reservation;

import java.util.*;

public class reservationService {

    private static reservationService INSTANCE;
    private static final Set<Reservation> reservations = new HashSet<>();
    private static final Map<String, IRoom> rooms = new HashMap<>();
    private static final Set<IRoom> roomsOnly = new HashSet<>();
    private static Map<String, IRoom> roomsAvailable = new HashMap<>(rooms);
    private reservationService(){
    }
    public static reservationService getInstance(){
        if(INSTANCE == null) {
            INSTANCE = new reservationService();
        }
        return INSTANCE;
    }


    public void addRoom(IRoom room){
        Room roomNew = new Room(room.getRoomNumber(),room.getRoomType(),room.getRoomRate());
        if (!roomsOnly.contains(room)) {
            rooms.put(roomNew.getRoomNumber(), roomNew);
            roomsOnly.add(roomNew);
        } //got help from Raul during study session. Make sure to cite during submission.
    }
    public IRoom getARoom(String roomId){
        return rooms.get(roomId);
    }
    public Reservation reserveARoom(Customer customer, IRoom room, Date checkInDate, Date checkOutDate){
        Reservation reservationNew = new Reservation(customer, room, checkInDate, checkOutDate);
        if (!reservations.contains(reservationNew)){
            reservations.add(reservationNew);
        }
        else {
            throw new IllegalArgumentException("A reservation already exists.");
        }
        return reservationNew;
    }
    public Collection<IRoom> findRooms(Date checkInDate, Date checkOutDate){
        for (Reservation reservationNew : reservations){
            boolean checkConflict = dateConflict(checkInDate, checkOutDate);
            if (checkConflict){
                roomsAvailable.remove(reservationNew.getRoom().getRoomNumber());
            }
        }
        return new ArrayList<>(roomsAvailable.values());
    }
    public Collection<Reservation> getGuestReservation(Customer customer){
        List<Reservation> reservationGuest = new ArrayList<>();
        for (Reservation res : reservations){
            if (res.getGuest().equals(customer)){
                reservationGuest.add(res);
            }
        }
        return reservationGuest;
    } // I got help for this during a StudyConnect session. I cannot remember if it was a student or Rachel who helped.
    public void printAllReservations(){
        if (!reservations.isEmpty()){
            for (Reservation res : reservations){
                System.out.println(res.toString());
            }
        }
        else {
            System.out.println("No reservations were found.");
        }
    }

    public Collection<IRoom> printAllRooms(){
        return rooms.values();
    }

    public boolean dateConflict(Date checkIn, Date checkOut){
        for (Reservation reservationNew : reservations) {
            return reservationNew.getCheckoutDate().compareTo(checkIn) > 0 && reservationNew.getCheckinDate().compareTo(checkOut) < 0;
            }
        return false;
    }

//    public boolean dateRecommend(Date checkIn, Date checkOut){
//        Calendar c1 = Calendar.getInstance();
//        Calendar c2 = Calendar.getInstance();
//        c1.setTime(checkIn);
//        c2.setTime(checkOut);
//        Date recDateIn = c1.add(Calendar.DATE, 7);
//        Date recDateOut = c2.add(Calendar.DATE, 7);
//        for (RoomReservation reservationNew : reservations) {
//            return reservationNew.getCheckoutDate().compareTo(recDateIn) > 0 && reservationNew.getCheckinDate().compareTo(recDateOut) < 0;
//        }
//        return false;
//    }

}
