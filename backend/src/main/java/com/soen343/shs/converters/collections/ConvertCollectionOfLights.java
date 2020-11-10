package com.soen343.shs.converters.collections;

import com.soen343.shs.dal.model.Light;
import com.soen343.shs.dto.LightDTO;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Component
public class ConvertCollectionOfLights {

    public static Set<Light> convertLightDTOs(final Set<LightDTO> lights) {
        return lights.stream()
                .map(light -> Light.builder()
                        .id(light.getId())
                        .roomId(light.getRoomId())
                        .isLightOn(light.isLightOn())
                        .awayMode(light.isAwayMode())
                        .start(light.getStart())
                        .end(light.getEnd())
                        .build())
                .collect(Collectors.toSet());
    }

    public static Set<LightDTO> convertLights(final Set<Light> lights) {
        return lights.stream()
                .map(light -> LightDTO.builder()
                        .id(light.getId())
                        .isLightOn(light.getIsLightOn())
                        .roomId(light.getRoomId())
                        .awayMode(light.getAwayMode())
                        .end(light.getEnd())
                        .start(light.getStart())
                        .build())
                .collect(Collectors.toSet());
    }
}
