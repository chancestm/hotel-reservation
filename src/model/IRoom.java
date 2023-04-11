package model;

public interface IRoom {

    public String getRoomNumber();
    public Double getRoomRate();
    public RoomType getRoomType();
    public boolean isFree();
}
