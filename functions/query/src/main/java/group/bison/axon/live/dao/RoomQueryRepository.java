package group.bison.axon.live.dao;

import group.bison.axon.live.entity.RoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomQueryRepository extends JpaRepository<RoomEntity, String> {

}