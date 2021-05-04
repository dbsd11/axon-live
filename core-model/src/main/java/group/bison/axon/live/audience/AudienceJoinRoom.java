package group.bison.axon.live.audience;

import group.bison.axon.live.room.RoomId;
import org.axonframework.modelling.command.EntityId;

public class AudienceJoinRoom {

    @EntityId
    private AudienceJoinRoomId audienceJoinRoomId;

    private RoomId roomId;

    private AudienceId audienceId;

    public AudienceJoinRoomId getAudienceJoinRoomId() {
        return audienceJoinRoomId;
    }

    public void setAudienceJoinRoomId(AudienceJoinRoomId audienceJoinRoomId) {
        this.audienceJoinRoomId = audienceJoinRoomId;
    }

    public RoomId getRoomId() {
        return roomId;
    }

    public void setRoomId(RoomId roomId) {
        this.roomId = roomId;
    }

    public AudienceId getAudienceId() {
        return audienceId;
    }

    public void setAudienceId(AudienceId audienceId) {
        this.audienceId = audienceId;
    }
}
