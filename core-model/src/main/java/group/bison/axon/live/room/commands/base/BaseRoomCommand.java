package group.bison.axon.live.room.commands.base;

import group.bison.axon.live.room.RoomId;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

public abstract class BaseRoomCommand {

    @TargetAggregateIdentifier
    private RoomId roomId;

    public BaseRoomCommand() {
    }

    public BaseRoomCommand(RoomId roomId) {
        this.roomId = roomId;
    }

    public RoomId getRoomId() {
        return roomId;
    }

    public void setRoomId(RoomId roomId) {
        this.roomId = roomId;
    }
}
