package group.bison.axon.live.audience.saga;

import group.bison.axon.live.audience.AudienceId;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;

public abstract class AudienceManagerSaga implements Serializable {

    transient CommandGateway commandGateway;

    AudienceId audienceId;

    public CommandGateway getCommandGateway() {
        return commandGateway;
    }

    @Autowired
    public void setCommandGateway(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }
}
