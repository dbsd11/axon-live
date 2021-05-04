package group.bison.axon.live.audience.commands;

import group.bison.axon.live.audience.AudienceJoinRoomId;

public class JoinRoomAudienceCommand extends StartJoinRoomAudienceCommand {

    public JoinRoomAudienceCommand() {
    }

    public JoinRoomAudienceCommand(AudienceJoinRoomId audienceJoinRoomId) {
        super(audienceJoinRoomId);
    }
}
