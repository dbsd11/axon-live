package group.bison.axon.live.service;

import group.bison.axon.live.dao.RoomQueryRepository;
import group.bison.axon.live.entity.RoomEntity;
import group.bison.axon.live.room.events.RoomCreatedEvent;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@ProcessingGroup("roomQueryModel")
public class RoomEventHandler {

    @Autowired
    private RoomQueryRepository roomRepository;

    @EventHandler
    public void on(RoomCreatedEvent event) {
        RoomEntity roomEntity = new RoomEntity();

        roomEntity.setIdentifier(event.getRoomId().getIdentifier());
        roomEntity.setName(event.getName());
        roomEntity.setConfig(event.getConfig());
        roomEntity.setCreated(event.getCreatedTimeTs());

        roomRepository.save(roomEntity);
    }
}
