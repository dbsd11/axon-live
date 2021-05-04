package group.bison.axon.live.audience.commands;

import group.bison.axon.live.audience.AudienceId;
import group.bison.axon.live.audience.AudienceJoinRoomId;
import group.bison.axon.live.room.RoomId;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

public class StartJoinRoomAudienceCommand {

    private AudienceId audienceId;

    private RoomId roomId;

    @TargetAggregateIdentifier
    private AudienceJoinRoomId audienceJoinRoomId;

    public StartJoinRoomAudienceCommand() {
    }

    public StartJoinRoomAudienceCommand(AudienceJoinRoomId audienceJoinRoomId) {
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
