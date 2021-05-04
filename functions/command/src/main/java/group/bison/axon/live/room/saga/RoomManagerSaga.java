package group.bison.axon.live.room.saga;

import group.bison.axon.live.room.RoomId;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;

public abstract class RoomManagerSaga implements Serializable {

    transient CommandGateway commandGateway;

    RoomId roomId;

    public CommandGateway getCommandGateway() {
        return commandGateway;
    }

    @Autowired
    public void setCommandGateway(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }
}
