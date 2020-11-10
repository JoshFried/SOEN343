package com.soen343.shs.dal.service;

import com.soen343.shs.dal.model.ExteriorDoor;
import com.soen343.shs.dal.model.InteriorDoor;
import com.soen343.shs.dal.repository.ExteriorDoorRepository;
import com.soen343.shs.dal.repository.InteriorDoorRepository;
import com.soen343.shs.dal.service.validators.StateValidator;
import com.soen343.shs.dto.DoorDTO;
import com.soen343.shs.dto.ExteriorDoorDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DoorService {
    private final InteriorDoorRepository interiorDoorRepository;
    private final ExteriorDoorRepository exteriorDoorRepository;
    private final RoomService roomService;


    /**
     * @param id           id of door to modify
     * @param open         boolean referring to if we should try to open the door or not
     * @param desiredState boolean referring to the desired state of the object
     * @return DoorDTO reflecting to the changes made to the object
     */
    public ExteriorDoorDTO modifyExteriorDoorState(final long id, final boolean open, final boolean desiredState) {

        return roomService.changeStateOfRoomObject(id, ExteriorDoor.class, ExteriorDoorDTO.class, exteriorDoorRepository,
                door -> {
                    if (open) {
                        StateValidator.checkForIllegalStateException(ExteriorDoor.class, id, door.getLocked());
                        StateValidator.validateState(desiredState, door.getOpen(), id, door.getClass());
                        door.setOpen(desiredState);
                    } else {
                        StateValidator.checkForIllegalStateException(ExteriorDoor.class, id, door.getOpen());
                        StateValidator.validateState(desiredState, door.getLocked(), id, door.getClass());
                        door.setLocked(desiredState);
                    }
                });
    }

    public DoorDTO modifyInteriorDoorState(final long id, final boolean desiredState) {

        return roomService.changeStateOfRoomObject(id, InteriorDoor.class, DoorDTO.class, interiorDoorRepository,
                door -> {
                    StateValidator.validateState(desiredState, door.getOpen(), id, door.getClass());
                    door.setOpen(desiredState);
                });
    }

}
