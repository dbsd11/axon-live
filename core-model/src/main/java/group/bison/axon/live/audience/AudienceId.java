package group.bison.axon.live.audience;

import org.axonframework.common.IdentifierFactory;

import java.io.Serializable;

public class AudienceId implements Serializable {
    private static final long serialVersionUID = -1L;

    private String identifier;

    public AudienceId() {
        this(null);
    }

    public AudienceId(String identifier) {
        this.identifier = identifier == null ? IdentifierFactory.getInstance().generateIdentifier() : identifier;
    }

    public String getIdentifier() {
        return identifier;
    }

    @Override
    public String toString() {
        return "AudienceId{" +
                "identifier='" + identifier + '\'' +
                '}';
    }
}
