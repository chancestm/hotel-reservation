package model;

import java.util.Objects;

public class Room implements IRoom {

    private final String roomNumber;
    private final Double roomRate;
    private final RoomType roomType;
    private final boolean isFree;


    public Room(String roomNumber, RoomType roomType, Double roomRate){
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.roomRate = roomRate;
        this.isFree = roomRate == 0.0;
    }

    public String getRoomNumber() {return roomNumber;}
    //public void setRoomNumber(String roomNumber){this.roomNumber = roomNumber;}

    public Double getRoomRate() {return roomRate;}
    //public void setRoomRate(Double roomRate) {this.roomRate = roomRate;}

    public RoomType getRoomType() {return roomType;}
    //public void setRoomType(RoomType roomType){this.roomType = roomType;}

    public boolean isFree() {return isFree;}

    @Override
    public String toString(){
        return "Room #: " + roomNumber + " | " + roomType + " | Rate: $" + roomRate + "/Night";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Room room)) return false;
        return roomNumber.equals(room.roomNumber) && roomType == room.roomType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(roomNumber, roomType);
    }
}
