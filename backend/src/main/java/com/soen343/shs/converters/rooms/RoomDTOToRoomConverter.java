package com.soen343.shs.converters.rooms;

import com.soen343.shs.converters.collections.ConvertCollectionOfDoors;
import com.soen343.shs.converters.collections.ConvertCollectionOfLights;
import com.soen343.shs.converters.collections.ConvertCollectionOfWindows;
import com.soen343.shs.dal.model.Room;
import com.soen343.shs.dto.RoomDTO;
import org.springframework.core.convert.converter.Converter;

public class RoomDTOToRoomConverter implements Converter<RoomDTO, Room> {

    @Override
    public Room convert(final RoomDTO room) {
        return Room.builder()
                .doors(ConvertCollectionOfDoors.convertDoorDTOs(room.getDoors()))
                .lights(ConvertCollectionOfLights.convertLightDTOs(room.getLights()))
                .name(room.getName())
                .temperature(room.getTemperature())
                .houseId(room.getHouseId())
                .houseWindows(ConvertCollectionOfWindows.convertWindowDTOs(room.getWindows()))
                .userIds(room.getUserIds())
                .id(room.getRoomId())
                .build();
    }

}
