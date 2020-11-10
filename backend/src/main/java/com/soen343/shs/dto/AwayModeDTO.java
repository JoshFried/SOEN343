package com.soen343.shs.dto;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AwayModeDTO {
    private long id;
    private boolean active;
    private long intruderDetectionDelay;
}
