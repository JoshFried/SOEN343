package com.soen343.shs.dal.service;

import com.soen343.shs.dal.model.ExteriorDoor;
import com.soen343.shs.dto.ExteriorDoorDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.function.Consumer;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DoorServiceTest {
    @Mock
    private RoomService roomService;

    @InjectMocks
    DoorService classUnderTest;

    private final static long MOCK_HOUSE_EXTERIOR_DOOR_ID = 1;


    @ParameterizedTest
    @MethodSource("exteriorDoorTestArgs")
    void test(final ExteriorDoor exteriorDoor, final boolean doWeOpen, final boolean finalOpen, final boolean finalLocked) {
        setUpExteriorDoorAndAssertStateChange(exteriorDoor, doWeOpen, finalOpen, finalLocked);
    }

    private static Stream<Arguments> exteriorDoorTestArgs() {
        return Stream.of(
                Arguments.of(
                        buildMockExteriorDoor(false, false),
                        false,
                        false,
                        true
                ),
                Arguments.of(
                        buildMockExteriorDoor(false, true),
                        false,
                        false,
                        false
                ),
                Arguments.of(
                        buildMockExteriorDoor(true, false),
                        true,
                        false,
                        false
                ),
                Arguments.of(
                        buildMockExteriorDoor(false, false),
                        true,
                        true,
                        false
                )
        );
    }

    private void setUpExteriorDoorAndAssertStateChange(final ExteriorDoor exteriorDoor,
                                                       final boolean doWeOpen,
                                                       final boolean finalOpen,
                                                       final boolean finalLocked) {

        when(roomService.changeStateOfRoomObject(anyLong(), any(), any(), any(), any(Consumer.class)))
                .thenReturn(ExteriorDoorDTO.builder()
                        .id(MOCK_HOUSE_EXTERIOR_DOOR_ID)
                        .open(finalOpen)
                        .locked(!doWeOpen && finalLocked)
                        .build());

        final ExteriorDoorDTO dto = classUnderTest.modifyExteriorDoorState(MOCK_HOUSE_EXTERIOR_DOOR_ID, doWeOpen, !doWeOpen && finalLocked);

        Assertions.assertEquals(dto.getId(), exteriorDoor.getId());
        Assertions.assertEquals(finalOpen, dto.getOpen());
        Assertions.assertEquals(finalLocked, dto.getLocked());
        Assertions.assertTrue(exteriorDoor.getLocked() != dto.getLocked() || exteriorDoor.getOpen() != dto.getOpen());
    }

    private static ExteriorDoor buildMockExteriorDoor(final boolean open, final boolean locked) {
        return ExteriorDoor.builder()
                .id(MOCK_HOUSE_EXTERIOR_DOOR_ID)
                .open(open)
                .locked(locked)
                .build();
    }

}
