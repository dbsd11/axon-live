package group.bison.axon.live.audience.saga;

import group.bison.axon.live.audience.AudienceJoinRoomId;
import group.bison.axon.live.audience.commands.JoinRoomAudienceCommand;
import group.bison.axon.live.audience.events.AudienceJoinRoomFailedEvent;
import group.bison.axon.live.audience.events.AudienceJoinedRoomEvent;
import group.bison.axon.live.audience.events.StartAudienceJoinRoomEvent;
import group.bison.axon.live.room.RoomId;
import org.axonframework.modelling.saga.EndSaga;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.spring.stereotype.Saga;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Saga
public class AudienceRoomManagerSaga extends AudienceManagerSaga {

    private static final Logger logger = LoggerFactory.getLogger(AudienceRoomManagerSaga.class);

    RoomId roomId;

    AudienceJoinRoomId audienceJoinRoomId;

    @StartSaga
    @SagaEventHandler(associationProperty = "audienceJoinRoomId")
    public void handle(StartAudienceJoinRoomEvent event) {
        audienceId = event.getAudienceId();
        roomId = event.getRoomId();
        audienceJoinRoomId = event.getAudienceJoinRoomId();

        logger.debug(
                "Start audience:{} join room:{} audienceJoinRoomId:{}",
                audienceId,
                roomId,
                audienceJoinRoomId
        );

        JoinRoomAudienceCommand joinRoomAudienceCommand = new JoinRoomAudienceCommand(event.getAudienceJoinRoomId());
        joinRoomAudienceCommand.setAudienceId(event.getAudienceId());
        joinRoomAudienceCommand.setRoomId(event.getRoomId());
        commandGateway.send(joinRoomAudienceCommand);
    }

    @SagaEventHandler(associationProperty = "audienceJoinRoomId")
    public void handle(AudienceJoinRoomFailedEvent event) {
        audienceId = event.getAudienceId();
        roomId = event.getRoomId();
        audienceJoinRoomId = event.getAudienceJoinRoomId();

        logger.error(
                "Failed audience:{} join room:{} audienceJoinRoomId:{} errorMsg:{}",
                audienceId,
                roomId,
                audienceJoinRoomId,
                event.getErrorMsg()
        );
    }

    @EndSaga
    @SagaEventHandler(associationProperty = "audienceJoinRoomId")
    public void handle(AudienceJoinedRoomEvent event) {
        audienceId = event.getAudienceId();
        roomId = event.getRoomId();
        audienceJoinRoomId = event.getAudienceJoinRoomId();

        logger.debug(
                "An audience:{} joined room:{} audienceJoinRoomId:{} joinRoomTimeTs:{}",
                audienceId,
                roomId,
                audienceJoinRoomId,
                event.getJoinTimeTs()
        );
    }
}
