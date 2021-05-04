package group.bison.axon.live.room.events.base;

import group.bison.axon.live.room.RoomId;

public abstract class BaseRoomEvent {

    private RoomId roomId;

    public BaseRoomEvent(RoomId roomId) {
        this.roomId = roomId;
    }

    public RoomId getRoomId() {
        return roomId;
    }

    public void setRoomId(RoomId roomId) {
        this.roomId = roomId;
    }
}
