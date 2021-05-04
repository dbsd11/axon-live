package group.bison.axon.live.audience;

import org.axonframework.common.IdentifierFactory;

import java.io.Serializable;

public class AudienceJoinRoomId implements Serializable {
    private static final long serialVersionUID = -1L;

    private String identifier;

    public AudienceJoinRoomId() {
        this(null);
    }

    public AudienceJoinRoomId(String identifier) {
        this.identifier = identifier == null ? IdentifierFactory.getInstance().generateIdentifier() : identifier;
    }

    public String getIdentifier() {
        return identifier;
    }

    @Override
    public String toString() {
        return "AudienceJoinRoomId{" +
                "identifier='" + identifier + '\'' +
                '}';
    }
}
