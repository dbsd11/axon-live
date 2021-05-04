package group.bison.axon.live.audience.commands.base;

import group.bison.axon.live.audience.AudienceId;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

public abstract class BaseAudienceCommand {

    @TargetAggregateIdentifier
    private AudienceId audienceId;

    public BaseAudienceCommand() {
    }

    public BaseAudienceCommand(AudienceId audienceId) {
        this.audienceId = audienceId;
    }

    public AudienceId getAudienceId() {
        return audienceId;
    }

    public void setAudienceId(AudienceId audienceId) {
        this.audienceId = audienceId;
    }
}
