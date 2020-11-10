package com.soen343.shs.converters.collections;

import com.soen343.shs.dal.model.Room;
import com.soen343.shs.dto.RoomDTO;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Component
public class ConvertCollectionOfRooms {

    public static Set<Room> convertRoomDTOs(final Set<RoomDTO> rooms) {
        return rooms.stream()
                .map(room -> Room.builder()
                        .id(room.getRoomId())
                        .name(room.getName())
                        .userIds(room.getUserIds())
                        .temperature(room.getTemperature())
                        .doors(ConvertCollectionOfDoors.convertDoorDTOs(room.getDoors()))
                        .houseWindows(ConvertCollectionOfWindows.convertWindowDTOs(room.getWindows()))
                        .lights(ConvertCollectionOfLights.convertLightDTOs(room.getLights()))
                        .houseId(room.getHouseId())
                        .build())
                .collect(Collectors.toSet());
    }

    public static Set<RoomDTO> convertRooms(final Set<Room> rooms) {
        return rooms.stream()
                .map(room -> RoomDTO.builder()
                        .roomId(room.getId())
                        .name(room.getName())
                        .temperature(room.getTemperature())
                        .userIds(room.getUserIds())
                        .doors(ConvertCollectionOfDoors.convertDoors(room.getDoors()))
                        .windows(ConvertCollectionOfWindows.convertWindows(room.getHouseWindows()))
                        .lights(ConvertCollectionOfLights.convertLights(room.getLights()))
                        .build())
                .collect(Collectors.toSet());
    }
}
