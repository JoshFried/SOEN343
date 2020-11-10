package com.soen343.shs.dal.service;

import com.soen343.shs.dal.model.Light;
import com.soen343.shs.dal.repository.LightRepository;
import com.soen343.shs.dal.service.validators.PermissionValidator;
import com.soen343.shs.dto.LightDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.convert.ConversionService;

import java.util.function.Consumer;
import java.util.stream.Stream;

import static com.soen343.shs.dal.service.helpers.RoomHelper.ROOM_ID;
import static com.soen343.shs.dal.service.helpers.UserTestHelper.USERNAME;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LightServiceTest {

    @Mock
    private RoomService roomService;

    @Mock
    private LightRepository lightRepository;

    @Mock
    private PermissionValidator permissionValidator;

    @Mock
    private ConversionService mvcConversionService;

    @InjectMocks
    private LightService classUnderTest;

    private final static long MOCK_HOUSE_LIGHT_ID = 1;

    @ParameterizedTest
    @MethodSource("lightTestArgs")
    void test(final boolean initialState, final boolean desiredState) {
        setUpLightAndAssertStateChange(initialState, desiredState);
    }

    private static Stream<Arguments> lightTestArgs() {
        return Stream.of(
                Arguments.of(
                        false,
                        true
                ),
                Arguments.of(
                        true,
                        false
                )
        );
    }

    private void setUpLightAndAssertStateChange(final boolean initialState, final boolean desiredState) {
        final Light mockLight = buildMockLight(initialState);
        when(roomService.changeStateOfRoomObject(anyLong(), any(), any(), any(), any(Consumer.class))).thenReturn(LightDTO.builder()
                .id(MOCK_HOUSE_LIGHT_ID)
                .isLightOn(desiredState)
                .build());


        final LightDTO dto = classUnderTest.modifyLightState(USERNAME, ROOM_ID, MOCK_HOUSE_LIGHT_ID, desiredState);

        Assertions.assertEquals(dto.getId(), mockLight.getId());
        Assertions.assertEquals(dto.isLightOn(), desiredState);
        Assertions.assertNotEquals(dto.isLightOn(), mockLight.getIsLightOn());
    }

    private static Light buildMockLight(final boolean desiredState) {
        return Light.builder()
                .id(MOCK_HOUSE_LIGHT_ID)
                .isLightOn(desiredState)
                .build();
    }


}
