package com.soen343.shs.dal.service;

import com.soen343.shs.dal.service.events.UserEntersRoomPublisher;
import com.soen343.shs.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class SimulationService {

    private final ConversionService mvcConversionService;
    private final HouseService houseService;
    private final RoomService roomService;
    private final UserService userService;
    private final HouseMemberService houseMemberService;
    private final UserEntersRoomPublisher publisher;

    /**
     * @param userId  id used to fetch user from db
     * @param houseId id used to fetch house from db
     * @param dto     Generic instance used to fetch user from db
     * @param <DTO>   Generic object used to fetch user from db
     * @return boolean if the update is successful we will return true, else we throw an error
     */
    public <DTO extends UserDTO> boolean addHouseIdToUser(final long userId, final long houseId, final Class<DTO> dto) {
        final DTO user = userService.getById(userId, dto);
        user.getHouseIds().add(houseId);

        if (user instanceof RealUserDTO) {
            userService.updateUser((RealUserDTO) user);
        } else {
            houseMemberService.updateHouseMember((HouseMemberDTO) user);
        }

        return addUserIdToHouse(user, houseId);
    }

    public HouseMemberDTO createNewHouseMember(final HouseMemberDTO dto) {
        final HouseMemberDTO newHouseMember = houseMemberService.createNewHouseMember(dto);
        addUserIdToHouse(newHouseMember, dto.getHouseIds().iterator().next());
        addHouseIdToUser(dto.getId(), dto.getHouseIds().iterator().next(), HouseMemberDTO.class);
        return newHouseMember;
    }

    /**
     * @param username username of user
     * @param roomId   a room id to transfer to
     * @return UserDTO object reflecting the changes made to the object
     */
    public <DTO extends UserDTO> UserDTO moveUserToRoom(final String username,
                                                        final long roomId,
                                                        final Class<DTO> dto) {

        final DTO user = userService.getUserByUsername(username, dto);
        updatePreviousLocation(user);
        user.setLocation(roomService.getRoom(roomId));

        if (user instanceof RealUserDTO) {
            userService.updateUser((RealUserDTO) user);
        } else {
            houseMemberService.updateHouseMember((HouseMemberDTO) user);
        }
        roomService.addUserToRoom(roomId, user.getId());
        publisher.publishEvent(roomId, user.getHouseIds().iterator().next());
        return mvcConversionService.convert(user, dto);
    }

    private <DTO extends UserDTO> void updatePreviousLocation(final DTO user) {
        if (user.isOutside()) {
            user.setOutside(false);
        } else {
            roomService.removeUserFromRoom(user.getLocation().getRoomId(), user.getId());
        }
    }

    /**
     * @param houseId a house id
     * @return list of RoomDTO
     */
    public Set<RoomDTO> findAllRooms(final Long houseId) {
        return Objects.requireNonNull(mvcConversionService.convert(houseService.fetchHouse(houseId), HouseDTO.class)).getRooms();
    }

    /**
     * @param dto     user object to have the role checked
     * @param houseId id of house to fetch from db
     * @return boolean value letting the previous method know the operation was successful
     */
    private boolean addUserIdToHouse(final UserDTO dto, final long houseId) {
        final HouseDTO house = houseService.getHouse(houseId);

        switch (dto.getRole()) {
            case PARENT:
                house.getParents().add(dto.getId());
                break;
            case CHILD:
                house.getChildren().add(dto.getId());
                break;
            case GUEST:
                house.getGuests().add(dto.getId());
                break;
        }
        houseService.updateHouse(house);
        return true;
    }
}
