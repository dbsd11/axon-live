package group.bison.axon.live.room.events;

import group.bison.axon.live.room.RoomId;
import group.bison.axon.live.room.events.base.BaseRoomEvent;

public class StartCreateRoomEvent extends BaseRoomEvent {

    private String name;

    private String config;

    public StartCreateRoomEvent(RoomId roomId) {
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
