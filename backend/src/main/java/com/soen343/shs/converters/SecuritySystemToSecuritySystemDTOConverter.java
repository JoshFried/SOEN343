package com.soen343.shs.converters;

import com.soen343.shs.dal.model.AwayMode;
import com.soen343.shs.dal.model.SecuritySystem;
import com.soen343.shs.dto.AwayModeDTO;
import com.soen343.shs.dto.SecuritySystemDTO;
import org.springframework.core.convert.converter.Converter;

public class SecuritySystemToSecuritySystemDTOConverter implements Converter<SecuritySystem, SecuritySystemDTO> {
    @Override
    public SecuritySystemDTO convert(final SecuritySystem system) {
        return SecuritySystemDTO.builder()
                .houseId(system.getHouseId())
                .id(system.getId())
                .auto(system.getAuto())
                .awayMode(convertAwayMode(system.getAwayMode()))
                .build();
    }

    private static AwayModeDTO convertAwayMode(final AwayMode awayMode) {
        return AwayModeDTO.builder()
                .id(awayMode.getId())
                .intruderDetectionDelay(awayMode.getIntruderDetectionDelay())
                .active(awayMode.getActive())
                .build();
    }
}
