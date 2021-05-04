package group.bison.axon.live.room;

import group.bison.axon.live.room.commands.CreateRoomCommand;
import group.bison.axon.live.room.commands.StartCreateRoomCommand;
import group.bison.axon.live.room.events.RoomCreatedEvent;
import group.bison.axon.live.room.events.StartCreateRoomEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Aggregate
public class RoomAgg {

    @AggregateIdentifier
    private RoomId roomId;

    private String name;

    private String config;

    private long created;

    @SuppressWarnings("UnusedDeclaration")
    public RoomAgg() {
        // Required by Axon Framework
    }

    @CommandHandler
    public RoomAgg(StartCreateRoomCommand cmd) {
        StartCreateRoomEvent startCreateRoomEvent = new StartCreateRoomEvent(cmd.getRoomId());
        startCreateRoomEvent.setName(cmd.getName());
        startCreateRoomEvent.setConfig(cmd.getConfig());
        apply(startCreateRoomEvent);
    }

    @CommandHandler
    public void handle(CreateRoomCommand cmd) {
        RoomCreatedEvent roomCreatedEvent = new RoomCreatedEvent(cmd.getRoomId());
        roomCreatedEvent.setName(cmd.getName());
        roomCreatedEvent.setConfig(cmd.getConfig());
        roomCreatedEvent.setCreatedTimeTs(System.currentTimeMillis());
        apply(roomCreatedEvent);
    }

    @EventSourcingHandler
    public void on(StartCreateRoomEvent event) {
        roomId = event.getRoomId();
    }

    @EventSourcingHandler
    public void on(RoomCreatedEvent event) {
        name = event.getName();
        config = event.getConfig();
        created = event.getCreatedTimeTs();
    }
}
