package group.bison.axon.live.room.events;

import group.bison.axon.live.room.RoomId;

public class RoomCreatedEvent extends StartCreateRoomEvent {

    private long createdTimeTs;

    public RoomCreatedEvent(RoomId roomId) {
        super(roomId);
    }

    public long getCreatedTimeTs() {
        return createdTimeTs;
    }

    public void setCreatedTimeTs(long createdTimeTs) {
        this.createdTimeTs = createdTimeTs;
    }
}
