package com.soen343.shs.dal.service;

import com.google.common.collect.ImmutableSet;
import com.soen343.shs.dal.model.Door;
import com.soen343.shs.dal.model.House;
import com.soen343.shs.dal.model.HouseWindow;
import com.soen343.shs.dal.model.Light;
import com.soen343.shs.dal.model.Room;
import com.soen343.shs.dal.repository.HouseRepository;
import com.soen343.shs.dto.HouseDTO;
import com.soen343.shs.dto.LoadDoorDTO;
import com.soen343.shs.dto.LoadExteriorDoorDTO;
import com.soen343.shs.dto.LoadHouseDTO;
import com.soen343.shs.dto.LoadInteriorDoorDTO;
import com.soen343.shs.dto.LoadRoomDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LoadSimulationService {

    private final HouseRepository houseRepository;
    private final ConversionService mvcConversionService;

    /**
     * @param loadHouseDTO data transfer object representing the house layout
     * @return HouseDTO object reflecting the changes made to the object
     */
    public HouseDTO loadHouse(final LoadHouseDTO loadHouseDTO) {

        return mvcConversionService.convert(
                houseRepository.save(House.builder()
                        .rooms(loadRooms(loadHouseDTO.getRooms()))
                        .build()),
                HouseDTO.class);
    }

    /**
     * @param rooms list of DTO reflecting rooms
     * @return list of rooms
     */
    private Set<Room> loadRooms(final Set<LoadRoomDTO> rooms) {
        return rooms.stream()
                .map(room -> Room.builder()
                        .doors(splitDoors(room.getDoors()))
                        .houseWindows(loadData(room.getHouseWindows(), HouseWindow.class))
                        .lights(loadData(room.getLights(), Light.class))
                        .name(room.getName())
                        .build())
                .collect(Collectors.toSet());
    }

    private Set<Door> splitDoors(final Set<LoadDoorDTO> loadDoorDTOS) {
        return loadDoorDTOS
                .stream()
                .map(door -> loadData((door instanceof LoadExteriorDoorDTO) ? ImmutableSet.of((LoadExteriorDoorDTO) door)
                        : ImmutableSet.of((LoadInteriorDoorDTO) door), Door.class)).flatMap(Set::stream).collect(Collectors.toSet());
    }

    /**
     * @param dtoSet    set of dto
     * @param classType the class type
     * @param <DTO>     generic of dto
     * @param <Entity>  the entity
     * @return set of entity
     */
    private <DTO, Entity> Set<Entity> loadData(final Set<DTO> dtoSet, final Class<Entity> classType) {
        return dtoSet.stream()
                .map(dto -> mvcConversionService.convert(dto, classType))
                .collect(Collectors.toSet());
    }
}
