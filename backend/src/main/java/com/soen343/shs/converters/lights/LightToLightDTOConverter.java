package com.soen343.shs.converters.lights;

import com.soen343.shs.dal.model.Light;
import com.soen343.shs.dto.LightDTO;
import org.springframework.core.convert.converter.Converter;

public class LightToLightDTOConverter implements Converter<Light, LightDTO> {

    @Override
    public LightDTO convert(final Light light) {
        return LightDTO.builder()
                .id(light.getId())
                .isLightOn(light.getIsLightOn())
                .awayMode(light.getAwayMode())
                .end(light.getEnd())
                .start(light.getStart())
                .build();
    }
}
