package group.bison.axon.live.room.saga;

import group.bison.axon.live.room.commands.CreateRoomCommand;
import group.bison.axon.live.room.events.RoomCreateFailedEvent;
import group.bison.axon.live.room.events.RoomCreatedEvent;
import group.bison.axon.live.room.events.StartCreateRoomEvent;
import org.axonframework.modelling.saga.EndSaga;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.spring.stereotype.Saga;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Saga
public class CommonRoomManagerSaga extends RoomManagerSaga {

    private static final Logger logger = LoggerFactory.getLogger(CommonRoomManagerSaga.class);

    @StartSaga
    @SagaEventHandler(associationProperty = "roomId")
    public void handle(StartCreateRoomEvent event) {
        roomId = event.getRoomId();

        logger.debug(
                "Start create room:{} name:{} config:{}",
                roomId,
                event.getName(),
                event.getConfig()
        );

        CreateRoomCommand createRoomCommand = new CreateRoomCommand(roomId);
        createRoomCommand.setName(event.getName());
        createRoomCommand.setConfig(event.getConfig());
        commandGateway.send(createRoomCommand);
    }

    @SagaEventHandler(associationProperty = "roomId")
    public void handle(RoomCreateFailedEvent event) {
        logger.error(
                "Failed create room:{} name:{} config:{} errorMsg:{}",
                roomId,
                event.getName(),
                event.getConfig(),
                event.getErrorMsg()
        );
    }

    @EndSaga
    @SagaEventHandler(associationProperty = "roomId")
    public void handle(RoomCreatedEvent event) {
        roomId = event.getRoomId();

        logger.debug(
                "End create room:{} name:{} config:{} createdTimeTs:{}",
                roomId,
                event.getName(),
                event.getConfig(),
                event.getCreatedTimeTs()
        );
    }
}
