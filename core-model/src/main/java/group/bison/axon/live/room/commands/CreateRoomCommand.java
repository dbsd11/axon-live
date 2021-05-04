package group.bison.axon.live.room.commands;

import group.bison.axon.live.room.RoomId;

public class CreateRoomCommand extends StartCreateRoomCommand {

    public CreateRoomCommand(){}

    public CreateRoomCommand(RoomId roomId) {
        super(roomId);
    }
}
