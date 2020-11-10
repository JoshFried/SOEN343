package com.soen343.shs.dal.service.helpers;

import com.soen343.shs.dal.model.RealUser;
import com.soen343.shs.dal.model.Room;
import com.soen343.shs.dto.RealUserDTO;
import com.soen343.shs.dto.RoomDTO;

import java.util.Collections;

import static com.soen343.shs.dal.model.UserRole.PARENT;
import static com.soen343.shs.dal.service.helpers.HouseHelper.HOUSE_ID;

public class UserTestHelper {
    public static final Long USER_ID = 1L;
    public static final String USERNAME = "someDude";
    public static final String PASSWORD = "password";
    public static final String EMAIL = "someemail@email.com";
    public static final String FIRST_NAME = "Joe";
    public static final String LAST_NAME = "Smith";

    public static RealUser createUser() {
        return RealUser.builder()
                .username(USERNAME)
                .email(EMAIL)
                .firstName(FIRST_NAME)
                .id(USER_ID)
                .isOutside(false)
                .houseIds(Collections.singleton(HOUSE_ID))
                .lastName(LAST_NAME)
                .location(Room.builder().id(1L).build())
                .password(PASSWORD)
                .role(PARENT)
                .build();
    }

    public static RealUserDTO createUserDTO() {
        return RealUserDTO.builder()
                .username(USERNAME)
                .email(EMAIL)
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .id(USER_ID)
                .role(PARENT)
                .houseIds(Collections.singleton(HOUSE_ID))
                .location(RoomDTO.builder().build())
                .isOutside(false)
                .build();
    }
}
