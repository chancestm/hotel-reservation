package model;

public class FreeRoom extends Room{

    public FreeRoom(String roomNumber, RoomType enumeration) {
        super(roomNumber, enumeration, 0.0);
    }
    @Override
    public String toString(){
        return "In direct contradiction to standard business practices, this room is free.";
    }
}
