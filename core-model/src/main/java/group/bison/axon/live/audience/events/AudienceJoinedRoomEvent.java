package group.bison.axon.live.audience.events;

import group.bison.axon.live.audience.AudienceJoinRoomId;

public class AudienceJoinedRoomEvent extends StartAudienceJoinRoomEvent {

    private long joinTimeTs;

    public AudienceJoinedRoomEvent(AudienceJoinRoomId audienceJoinRoomId) {
        super(audienceJoinRoomId);
    }

    public long getJoinTimeTs() {
        return joinTimeTs;
    }

    public void setJoinTimeTs(long joinTimeTs) {
        this.joinTimeTs = joinTimeTs;
    }
}
