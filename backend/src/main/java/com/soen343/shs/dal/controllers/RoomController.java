package com.soen343.shs.dal.controllers;

import com.soen343.shs.dal.service.RoomService;
import com.soen343.shs.dto.RoomDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "api/house/{houseId}/room/{roomId}")
public class RoomController {

    private final RoomService roomService;

    @GetMapping(value = "/")
    @ResponseStatus(value = HttpStatus.OK)
    public RoomDTO getRoom(@PathVariable final long roomId) {
        return roomService.getRoom(roomId);
    }

    @PutMapping(value = "/temperature")
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public @ResponseBody
    RoomDTO modifyRoomTemperature(@PathVariable final long roomId, @RequestBody final double newTemperature) {
        return roomService.setTemperatureOfRoom(roomId, newTemperature);
    }

    @GetMapping(value = "/temperature")
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public @ResponseBody
    double getRoomTemperature(@PathVariable final long roomId) {
        return roomService.getTemperatureOfRoom(roomId);
    }
}
