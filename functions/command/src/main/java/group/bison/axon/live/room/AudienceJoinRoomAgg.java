package group.bison.axon.live.room;

import group.bison.axon.live.audience.AudienceJoinRoom;
import group.bison.axon.live.audience.AudienceJoinRoomId;
import group.bison.axon.live.audience.commands.JoinRoomAudienceCommand;
import group.bison.axon.live.audience.commands.StartJoinRoomAudienceCommand;
import group.bison.axon.live.audience.events.AudienceJoinRoomFailedEvent;
import group.bison.axon.live.audience.events.AudienceJoinedRoomEvent;
import group.bison.axon.live.audience.events.StartAudienceJoinRoomEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateMember;
import org.axonframework.spring.stereotype.Aggregate;

import java.util.HashMap;
import java.util.Map;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Aggregate
public class AudienceJoinRoomAgg {

    @AggregateIdentifier
    private AudienceJoinRoomId audienceJoinRoomId;

    @AggregateMember(type = AudienceJoinRoom.class)
    private Map roomMap = new HashMap();

    @SuppressWarnings("UnusedDeclaration")
    public AudienceJoinRoomAgg() {
        // Required by Axon Framework
    }

    @CommandHandler
    public AudienceJoinRoomAgg(StartJoinRoomAudienceCommand cmd) {
        StartAudienceJoinRoomEvent startAudienceJoinRoomEvent = new StartAudienceJoinRoomEvent(cmd.getAudienceJoinRoomId());
        startAudienceJoinRoomEvent.setAudienceId(cmd.getAudienceId());
        startAudienceJoinRoomEvent.setRoomId(cmd.getRoomId());
        apply(startAudienceJoinRoomEvent);
    }

    @CommandHandler
    public void handle(JoinRoomAudienceCommand cmd) {
        if (!roomMap.containsKey(cmd.getAudienceId().getIdentifier()) && roomMap.size() >= 1) {
            AudienceJoinRoomFailedEvent audienceJoinRoomFailedEvent = new AudienceJoinRoomFailedEvent(cmd.getAudienceJoinRoomId());
            audienceJoinRoomFailedEvent.setAudienceId(cmd.getAudienceId());
            audienceJoinRoomFailedEvent.setRoomId(cmd.getRoomId());
            audienceJoinRoomFailedEvent.setErrorMsg("roomJoinedCount >= 1");
            apply(audienceJoinRoomFailedEvent);
            return;
        }

        AudienceJoinedRoomEvent audienceJoinedRoomEvent = new AudienceJoinedRoomEvent(cmd.getAudienceJoinRoomId());
        audienceJoinedRoomEvent.setAudienceId(cmd.getAudienceId());
        audienceJoinedRoomEvent.setRoomId(cmd.getRoomId());
        audienceJoinedRoomEvent.setJoinTimeTs(System.currentTimeMillis());
        apply(audienceJoinedRoomEvent);
    }

    @EventSourcingHandler
    public void on(StartAudienceJoinRoomEvent event) {
        audienceJoinRoomId = event.getAudienceJoinRoomId();
        if (roomMap == null) {
            roomMap = new HashMap();
        }
    }

    @EventSourcingHandler
    public void on(AudienceJoinedRoomEvent event) {
        roomMap.put(event.getAudienceId().getIdentifier(), event.getRoomId());
    }
}
