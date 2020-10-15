package com.soen343.shs.DTO;

import com.soen343.shs.dal.model.Door;
import com.soen343.shs.dal.model.Light;
import com.soen343.shs.dal.model.Window;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class RoomDTO {
    private String name;
    private List<Door> doors;
    private List<Window> windows;
    private List<Light> lights;
    private double temperature;
    private List<Long> userIds;
}
