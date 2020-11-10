package com.soen343.shs.dal.service;

import com.soen343.shs.dal.model.HouseWindow;
import com.soen343.shs.dal.repository.HouseWindowRepository;
import com.soen343.shs.dal.service.validators.PermissionValidator;
import com.soen343.shs.dal.service.validators.StateValidator;
import com.soen343.shs.dto.WindowDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class WindowService {
    private final HouseWindowRepository houseWindowRepository;
    private final RoomService roomService;
    private final PermissionValidator validator;

    /**
     * @param id           id of window  object to modify
     * @param open         boolean referring to if we should try to open the window or not
     * @param desiredState boolean referring to the desired state of the object
     * @return WindowDTO object reflecting the changes made to the object
     */
    public WindowDTO modifyWindowState(final String username,
                                       final long roomId,
                                       final long id,
                                       final boolean open,
                                       final boolean desiredState) {

        validator.validatePermissions(username, roomId);
        return roomService.changeStateOfRoomObject(id, HouseWindow.class, WindowDTO.class, houseWindowRepository,
                window -> {
                    if (open) {
                        StateValidator.validateState(desiredState, window.getOpen(), id, window.getClass());
                        StateValidator.checkForIllegalStateException(HouseWindow.class, id, window.getBlocked());
                        window.setOpen(desiredState);
                    } else {
                        StateValidator.validateState(desiredState, window.getBlocked(), id, window.getClass());
                        StateValidator.checkForIllegalStateException(HouseWindow.class, id, window.getOpen());
                        window.setBlocked(desiredState);
                    }
                });
    }
}
