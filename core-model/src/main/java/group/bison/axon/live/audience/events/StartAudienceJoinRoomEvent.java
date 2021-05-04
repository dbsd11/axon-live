package group.bison.axon.live.audience.events;

import group.bison.axon.live.audience.AudienceId;
import group.bison.axon.live.audience.AudienceJoinRoomId;
import group.bison.axon.live.room.RoomId;

public class StartAudienceJoinRoomEvent {

    private AudienceId audienceId;

    private RoomId roomId;

    private AudienceJoinRoomId audienceJoinRoomId;

    public StartAudienceJoinRoomEvent(AudienceJoinRoomId audienceJoinRoomId) {
        this.audienceJoinRoomId = audienceJoinRoomId;
    }

    public AudienceId getAudienceId() {
        return audienceId;
    }

    public void setAudienceId(AudienceId audienceId) {
        this.audienceId = audienceId;
    }

    public RoomId getRoomId() {
        return roomId;
    }

    public void setRoomId(RoomId roomId) {
        this.roomId = roomId;
    }

    public AudienceJoinRoomId getAudienceJoinRoomId() {
        return audienceJoinRoomId;
    }
}
