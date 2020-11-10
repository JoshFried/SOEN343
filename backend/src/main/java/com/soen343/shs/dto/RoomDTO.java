package com.soen343.shs.dto;

import lombok.*;

import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoomDTO {
    private long roomId;
    private String name;
    private Set<DoorDTO> doors;
    private Set<WindowDTO> windows;
    private Set<LightDTO> lights;
    private double temperature;
    private Set<Long> userIds;
    private long houseId;
}