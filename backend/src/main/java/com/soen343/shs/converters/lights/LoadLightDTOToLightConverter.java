package com.soen343.shs.converters.lights;

import com.soen343.shs.dal.model.Light;
import com.soen343.shs.dto.LoadLightDTO;
import org.springframework.core.convert.converter.Converter;

import java.time.LocalTime;


public class LoadLightDTOToLightConverter implements Converter<LoadLightDTO, Light> {

    @Override
    public Light convert(final LoadLightDTO loadLightDTO) {
        return Light.builder()
                .isLightOn(loadLightDTO.isLightOn())
                .awayMode(true)
                .start(LocalTime.of(12, 0, 0, 0))
                .end(LocalTime.of(18, 0, 0, 0))
                .build();
    }
}
