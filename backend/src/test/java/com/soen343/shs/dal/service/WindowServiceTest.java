package com.soen343.shs.dal.service;

import com.soen343.shs.dal.model.HouseWindow;
import com.soen343.shs.dal.service.validators.PermissionValidator;
import com.soen343.shs.dto.WindowDTO;
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

import static com.soen343.shs.dal.service.helpers.RoomHelper.ROOM_ID;
import static com.soen343.shs.dal.service.helpers.UserTestHelper.USERNAME;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class WindowServiceTest {

    @Mock
    private RoomService roomService;

    @Mock
    private PermissionValidator validator;
    
    @InjectMocks
    private WindowService classUnderTest;

    private final static long MOCK_HOUSE_WINDOW_ID = 1;


    @ParameterizedTest
    @MethodSource("windowTestArgs")
    void windowTest(final HouseWindow mockHouseWindow, final boolean doWeOpen, final boolean finalOpen, final boolean finalBlocked) {
        setUpWindowAndAssertStateChange(mockHouseWindow, doWeOpen, finalOpen, finalBlocked);
    }

    private static Stream<Arguments> windowTestArgs() {
        return Stream.of(
                Arguments.of(
                        buildMockHouseWindow(false, false),
                        false,
                        false,
                        true
                ),
                Arguments.of(
                        buildMockHouseWindow(false, false),
                        true,
                        true,
                        false
                ),
                Arguments.of(
                        buildMockHouseWindow(false, true),
                        false,
                        false,
                        false
                ),
                Arguments.of(
                        buildMockHouseWindow(false, false),
                        false,
                        false,
                        true
                )
        );
    }

    private void setUpWindowAndAssertStateChange(final HouseWindow mockHouseWindow,
                                                 final boolean doWeOpen,
                                                 final boolean finalOpen,
                                                 final boolean finalBlocked) {
        when(roomService.changeStateOfRoomObject(anyLong(), any(), any(), any(), any(Consumer.class)))
                .thenReturn(WindowDTO.builder()
                        .id(MOCK_HOUSE_WINDOW_ID)
                        .open(finalOpen)
                        .blocked(finalBlocked)
                        .build());
        final WindowDTO dto = classUnderTest
                .modifyWindowState(
                        USERNAME,
                        ROOM_ID,
                        MOCK_HOUSE_WINDOW_ID,
                        doWeOpen,
                        doWeOpen ? finalOpen : finalBlocked);
        Assertions.assertEquals(dto.getId(), mockHouseWindow.getId());
        Assertions.assertEquals(dto.isOpen(), finalOpen);
        Assertions.assertEquals(dto.isBlocked(), finalBlocked);
        Assertions.assertTrue(mockHouseWindow.getBlocked() != dto.isBlocked() || mockHouseWindow.getOpen() != dto.isOpen());

    }

    private static HouseWindow buildMockHouseWindow(final boolean open, final boolean blocked) {
        return HouseWindow.builder()
                .id(MOCK_HOUSE_WINDOW_ID)
                .open(open)
                .blocked(blocked)
                .build();
    }
}
