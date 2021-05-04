package group.bison.axon.live.room;

import org.axonframework.modelling.command.EntityId;

public class Room {

    @EntityId
    private RoomId roomId;

    public RoomId getRoomId() {
        return roomId;
    }

    public void setRoomId(RoomId roomId) {
        this.roomId = roomId;
    }
}
