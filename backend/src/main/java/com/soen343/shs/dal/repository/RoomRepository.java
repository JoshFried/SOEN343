package com.soen343.shs.dal.repository;

import com.soen343.shs.dal.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Long> {
}
