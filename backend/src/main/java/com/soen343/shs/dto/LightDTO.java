package com.soen343.shs.dto;

import lombok.*;

import java.time.LocalTime;

@Getter
@NoArgsConstructor
@Setter
@AllArgsConstructor
@Builder
public class LightDTO {
    private Long id;
    private boolean isLightOn;
    private boolean awayMode;
    private long roomId;
    private LocalTime start;
    private LocalTime end;
}