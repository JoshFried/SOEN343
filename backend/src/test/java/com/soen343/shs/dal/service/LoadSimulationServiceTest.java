package com.soen343.shs.dal.service;

import com.soen343.shs.dal.model.House;
import com.soen343.shs.dal.model.HouseWindow;
import com.soen343.shs.dal.model.InteriorDoor;
import com.soen343.shs.dal.model.Light;
import com.soen343.shs.dal.repository.HouseRepository;
import com.soen343.shs.dal.repository.RoomRepository;
import com.soen343.shs.dto.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.convert.ConversionService;

import java.util.Collections;
import java.util.Set;

import static com.soen343.shs.dal.service.helpers.HouseHelper.*;
import static com.soen343.shs.dal.service.helpers.RoomHelper.ROOM_NAME;
import static com.soen343.shs.dal.service.helpers.UserTestHelper.USERNAME;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LoadSimulationServiceTest {

    @Mock
    private HouseRepository houseRepository;

    @Mock
    private UserService userService;

    @Mock
    private RoomRepository roomRepository;

    @Mock
    private CityService cityService;

    @Mock
    private ConversionService mvcConversionService;

    @Mock
    private SecuritySystemService securitySystemService;

    @InjectMocks
    private LoadSimulationService classUnderTest;

    @Test
    void testLoadHouse() {
        final RealUserDTO user = Mockito.mock(RealUserDTO.class);
        final House house = buildHouse();
        final LoadHouseDTO loadHouse = Mockito.mock(LoadHouseDTO.class);

        when(userService.getUserByUsername(USERNAME, RealUserDTO.class)).thenReturn(user);
        when(loadHouse.getCity()).thenReturn(CITY_NAME);
        when(cityService.getCity(CITY_NAME)).thenReturn(CityDTO.builder().build());
        when(loadHouse.getRooms()).thenReturn(createLoadRooms());

        when(mvcConversionService.convert(any(LoadInteriorDoorDTO.class), any())).thenReturn(InteriorDoor.builder().build());
        when(mvcConversionService.convert(any(LoadHouseWindowDTO.class), any())).thenReturn(HouseWindow.builder().build());
        when(securitySystemService.createSecuritySystem(HOUSE_ID, 30000)).thenReturn(SecuritySystemDTO.builder().build());
        when(mvcConversionService.convert(any(LoadLightDTO.class), any())).thenReturn(Light.builder().build());
        when(houseRepository.save(any(House.class))).thenReturn(house);
        when(mvcConversionService.convert(house, HouseDTO.class)).thenReturn(createHouseDTO());

        final HouseDTO dto = classUnderTest.loadHouse(loadHouse, USERNAME);
        Assertions.assertEquals(house.getId(), dto.getId());
        Assertions.assertEquals(house.getParents(), dto.getParents());
        Assertions.assertEquals(house.getRooms().size(), dto.getRooms().size());
    }


    private static LoadHouseDTO createLoadHouse() {
        return LoadHouseDTO.builder()
                .city(CITY_NAME)
                .rooms(createLoadRooms())
                .build();
    }


    private static Set<LoadRoomDTO> createLoadRooms() {
        return Collections
                .singleton(LoadRoomDTO.builder()
                        .doors(Collections.singleton(LoadInteriorDoorDTO
                                .builder()
                                .build()))
                        .houseWindows(Collections.singleton(LoadHouseWindowDTO.builder().build()))
                        .lights(Collections.singleton(LoadLightDTO.builder().build()))
                        .name(ROOM_NAME)
                        .build());
    }


}
