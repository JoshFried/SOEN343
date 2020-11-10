package com.soen343.shs.dal.service;

import com.soen343.shs.dal.model.Room;
import com.soen343.shs.dal.repository.RoomRepository;
import com.soen343.shs.dal.service.exceptions.state.SHSNotFoundException;
import com.soen343.shs.dal.service.validators.helper.ErrorMessageGenerator;
import com.soen343.shs.dto.RoomDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

@RequiredArgsConstructor
@Service
public class RoomService {

    private final RoomRepository roomRepository;
    private final ConversionService mvcConversionService;

    public RoomDTO getRoom(final long id) {
        return mvcConversionService.convert(fetchRoom(id), RoomDTO.class);
    }

    /**
     * @param id             a room id
     * @param newTemperature new temperature
     * @return RoomDTO object reflecting the changes made to the object
     */
    public RoomDTO setTemperatureOfRoom(final long id, final double newTemperature) {
        final Room room = fetchRoom(id);
        room.setTemperature(newTemperature);
        return mvcConversionService.convert(roomRepository.save(room), RoomDTO.class);
    }

    /**
     * @param roomId id of the room to be updated
     * @param userId id of the user to be added to the collection of user ids in the room
     * @return DTO object showing the updated state of the room
     */
    RoomDTO addUserToRoom(final long roomId, final long userId) {
        final Room room = fetchRoom(roomId);
        room.getUserIds().add(userId);
        return mvcConversionService.convert(roomRepository.save(room), RoomDTO.class);
    }

    RoomDTO removeUserFromRoom(final long roomId, final long userId) {
        final Room room = fetchRoom(roomId);
        room.getUserIds().remove(userId);
        return mvcConversionService.convert(roomRepository.save(room), RoomDTO.class);
    }

    public double getTemperatureOfRoom(final long roomId) {
        return fetchRoom(roomId).getTemperature();
    }

    Room fetchRoom(final long id) {
        return roomRepository.findById(id).orElseThrow(() -> new SHSNotFoundException(ErrorMessageGenerator.getSHSNotFoundErrorMessage(id, Room.class)));
    }


    /**
     * @param id              id of object
     * @param entityClassType entity object
     * @param dtoClassType    dto object
     * @param repository      repository object
     * @param consumer        consumer function to accept
     * @param <DTO>           generic object to be passed as a param
     * @param <Entity>        generic object to be passed as a param
     * @return DTO object reflecting changes made to the object
     */
    <DTO, Entity> DTO changeStateOfRoomObject(final long id,
                                              final Class<Entity> entityClassType,
                                              final Class<DTO> dtoClassType,
                                              final CrudRepository<Entity, Long> repository,
                                              final Consumer<Entity> consumer) {

        final Entity entity = repository.findById(id).orElseThrow(() -> new SHSNotFoundException(ErrorMessageGenerator.getSHSNotFoundErrorMessage(id, entityClassType)));
        consumer.accept(entity);
        return mvcConversionService.convert(repository.save(entity), dtoClassType);
    }
}
