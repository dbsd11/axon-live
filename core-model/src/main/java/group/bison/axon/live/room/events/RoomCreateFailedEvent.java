package group.bison.axon.live.room.events;

import group.bison.axon.live.room.RoomId;

public class RoomCreateFailedEvent extends StartCreateRoomEvent {

    private String errorMsg;

    public RoomCreateFailedEvent(RoomId roomId) {
        super(roomId);
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
