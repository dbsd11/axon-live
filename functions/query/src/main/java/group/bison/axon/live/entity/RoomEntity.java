package group.bison.axon.live.entity;

import org.springframework.data.annotation.Id;

import javax.persistence.Entity;

@Entity
public class RoomEntity {

    @Id
    @javax.persistence.Id
    private String identifier;
    private String name;
    private String config;
    private long created;

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getConfig() {
        return config;
    }

    public void setConfig(String config) {
        this.config = config;
    }

    public long getCreated() {
        return created;
    }

    public void setCreated(long created) {
        this.created = created;
    }
}
