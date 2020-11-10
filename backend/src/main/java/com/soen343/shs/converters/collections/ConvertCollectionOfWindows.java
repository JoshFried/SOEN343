package com.soen343.shs.converters.collections;

import com.soen343.shs.dal.model.HouseWindow;
import com.soen343.shs.dto.WindowDTO;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Component
public class ConvertCollectionOfWindows {
    public static Set<HouseWindow> convertWindowDTOs(final Set<WindowDTO> windows) {
        return windows.stream()
                .map(window -> HouseWindow.builder()
                        .blocked(window.isBlocked())
                        .roomId(window.getRoomId())
                        .id(window.getId())
                        .open(window.isOpen())
                        .build())
                .collect(Collectors.toSet());
    }

    public static Set<WindowDTO> convertWindows(final Set<HouseWindow> windows) {
        return windows.stream()
                .map(window -> WindowDTO.builder()
                        .blocked(window.getBlocked())
                        .roomId(window.getRoomId())
                        .id(window.getId())
                        .open(window.getOpen())
                        .build())
                .collect(Collectors.toSet());
    }
}
