package com.soen343.shs.dal.service;

import com.soen343.shs.dal.model.House;
import com.soen343.shs.dal.model.HouseWindow;
import com.soen343.shs.dal.model.Room;
import com.soen343.shs.dal.service.events.UserEntersRoomPublisher;
import com.soen343.shs.dto.HouseDTO;
import com.soen343.shs.dto.HouseMemberDTO;
import com.soen343.shs.dto.RoomDTO;
import com.soen343.shs.dto.UserDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.convert.ConversionService;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static com.soen343.shs.dal.service.helpers.HouseHelper.HOUSE_ID;
import static com.soen343.shs.dal.service.helpers.HouseMemberHelper.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SimulationServiceTest {

    @Mock
    private ConversionService mvcConversionService;

    @Mock
    private HouseService houseService;

    @Mock
    private RoomService roomService;

    @Mock
    private UserService userService;

    @Mock
    private HouseMemberService houseMemberService;

    @Mock
    private UserEntersRoomPublisher publisher;

    @InjectMocks
    private SimulationService classUnderTest;

    @Test
    void moveUserToRoomSuccessfully() {
        final HouseMemberDTO dto = Mockito.mock(HouseMemberDTO.class);

        when(userService.getUserByUsername(MOCK_HOUSE_MEMBER_NAME, HouseMemberDTO.class)).thenReturn(dto);
        final RoomDTO roomDTO = buildRoomDTO();
        when(roomService.getRoom(MOCK_ROOM_ID)).thenReturn(roomDTO);
        when(dto.getLocation()).thenReturn(roomDTO);

        when(houseMemberService.updateHouseMember(dto)).thenReturn(HouseMemberDTO.builder().houseIds(Collections.singleton(HOUSE_ID)).location(roomDTO).build());
        when(mvcConversionService.convert(any(), any())).thenReturn(dto);
        when(dto.getHouseIds()).thenReturn(Collections.singleton(HOUSE_ID));

        final UserDTO houseMemberDTO = classUnderTest.moveUserToRoom(MOCK_HOUSE_MEMBER_NAME, MOCK_ROOM_ID, HouseMemberDTO.class);

        Assertions.assertEquals(MOCK_ROOM_ID, houseMemberDTO.getLocation().getRoomId());
    }

    @Test
    void testFindAllRooms() {
        final House house = House.builder()
                .city("Montreal")
                .rooms(Collections.singleton(buildMockRoom()))
                .id(1L)
                .build();
        final HouseDTO houseDTO = Mockito.mock(HouseDTO.class);

        final Set<RoomDTO> dto = Collections.singleton(buildRoomDTO());

        when(houseService.fetchHouse(house.getId())).thenReturn(house);
        when(mvcConversionService.convert(house, HouseDTO.class)).thenReturn(houseDTO);
        when(houseDTO.getRooms()).thenReturn(dto);
        final Set<RoomDTO> rooms = classUnderTest.findAllRooms(house.getId());
        Assertions.assertEquals(dto, rooms);
    }

    private static RoomDTO buildRoomDTO() {
        return RoomDTO.builder()
                .roomId(MOCK_ROOM_ID)
                .name("MockRoom")
                .temperature(0)
                .userIds(Collections.emptySet())
                .windows(Collections.emptySet())
                .doors(Collections.emptySet())
                .lights(Collections.emptySet())
                .build();
    }


    private static Room buildMockRoom() {
        return Room.builder()
                .id(MOCK_ROOM_ID)
                .name("MockRoom")
                .temperature(0)
                .doors(new HashSet<>())
                .lights(new HashSet<>())
                .userIds(new HashSet<>())
                .houseWindows(new HashSet<>())
                .build();
    }

    private static HouseWindow buildMockHouseWindow() {
        return HouseWindow.builder()
                .id(MOCK_HOUSE_WINDOW_ID)
                .open(true)
                .blocked(false)
                .build();
    }
}
