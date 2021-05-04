package group.bison.axon.live.audience.events.base;

import group.bison.axon.live.audience.AudienceId;

public abstract class BaseAudienceEvent {

    private AudienceId audienceId;

    public BaseAudienceEvent(AudienceId audienceId) {
        this.audienceId = audienceId;
    }

    public AudienceId getAudienceId() {
        return audienceId;
    }

    public void setAudienceId(AudienceId audienceId) {
        this.audienceId = audienceId;
    }
}
