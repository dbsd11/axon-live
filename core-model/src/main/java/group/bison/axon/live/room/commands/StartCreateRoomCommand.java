package group.bison.axon.live.room.commands;

import group.bison.axon.live.room.RoomId;
import group.bison.axon.live.room.commands.base.BaseRoomCommand;

public class StartCreateRoomCommand extends BaseRoomCommand {

    private String name;

    private String config;

    public StartCreateRoomCommand(){}

    public StartCreateRoomCommand(RoomId roomId) {
        super(roomId);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getConfig() {
        return config;
    }

    public void setConfig(String config) {
        this.config = config;
    }
}
