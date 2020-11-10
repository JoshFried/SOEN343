package com.soen343.shs.dal.service.validators;

import com.soen343.shs.dal.model.UserRole;
import com.soen343.shs.dal.service.RoomService;
import com.soen343.shs.dal.service.UserService;
import com.soen343.shs.dal.service.exceptions.IllegalRequestException;
import com.soen343.shs.dto.RealUserDTO;
import com.soen343.shs.dto.RoomDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class PermissionValidator {
    private final UserService userService;
    private final RoomService roomService;

    public void validatePermissions(final String username, final long roomId) {
        final RealUserDTO user = userService.getUserByUsername(username, RealUserDTO.class);
        final RoomDTO room = roomService.getRoom(roomId);

        if (user.getRole() == UserRole.valueOf("STRANGER")) {
            throw new IllegalRequestException(
                    String.format("User: %s cannot change the state of any room entities in house: %d, because they are a stranger!",
                            username,
                            room.getHouseId()));
        }

        if (user.getRole() != UserRole.valueOf("PARENT") && !user.getLocation().equals(room)) {
            throw new IllegalRequestException(
                    String.format("User : %s cannot change the state of room %s's entities, because they are not in that room!",
                            username,
                            room.getName()));
        }
    }

    public void validateAwayModePermmissions(final String username) {
        final RealUserDTO user = userService.getUserByUsername(username, RealUserDTO.class);
        if ((user.getRole() == UserRole.valueOf("PARENT") || user.getRole() == UserRole.valueOf("CHILD")) && !user.isOutside()) {
            throw new IllegalRequestException(String.format("User: %s cannot change the state of away mode because they are already in the house", username));
        } else if (user.getRole() == UserRole.valueOf("GUEST") || user.getRole() == UserRole.valueOf("STRANGER")) {
            throw new IllegalRequestException(
                    String.format("User: %s cannot change the state of away mode because they do not have valid permissions", username));
        }
    }
}
