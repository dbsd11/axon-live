package group.bison.axon.live.audience.events;

import group.bison.axon.live.audience.AudienceJoinRoomId;

public class AudienceJoinRoomFailedEvent extends StartAudienceJoinRoomEvent {

    private String errorMsg;

    public AudienceJoinRoomFailedEvent(AudienceJoinRoomId audienceJoinRoomId) {
        super(audienceJoinRoomId);
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
