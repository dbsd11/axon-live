package group.bison.axon.live.room;

import org.axonframework.common.IdentifierFactory;

import java.io.Serializable;

public class RoomId implements Serializable {
    private static final long serialVersionUID = -1L;

    private String identifier;

    public RoomId() {
        this(null);
    }

    public RoomId(String identifier) {
        this.identifier = identifier == null ? IdentifierFactory.getInstance().generateIdentifier() : identifier;
    }

    public String getIdentifier() {
        return identifier;
    }

    @Override
    public String toString() {
        return "RoomId{" +
                "identifier='" + identifier + '\'' +
                '}';
    }
}
