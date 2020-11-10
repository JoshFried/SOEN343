package com.soen343.shs.dal.controllers;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.soen343.shs.dal.service.LightService;
import com.soen343.shs.dto.LightDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/house/{houseId}/room/{roomId}/light/{lightId}")
public class LightController {

    private final LightService lightService;

    @PutMapping(value = "/")
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public @ResponseBody
    LightDTO modifyLightState(@PathVariable final long houseId,
                              @PathVariable final long roomId,
                              @PathVariable final long lightId,
                              @AuthenticationPrincipal final Authentication auth,
                              @RequestBody final ObjectNode objectNode) {

        return lightService.modifyLightState(
                auth.getName(),
                roomId,
                lightId,
                objectNode.get("desiredState").asBoolean());
    }

    @PutMapping(value = "/startTime")
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public @ResponseBody
    LightDTO updateStartTime(@PathVariable final long houseId,
                             @PathVariable final long roomId,
                             @PathVariable final long lightId,
                             @AuthenticationPrincipal final Authentication auth,
                             @RequestBody final LocalTime startTime) {

        return lightService.updateStartTime(lightId, startTime);
    }

    @PutMapping(value = "/endTime")
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public @ResponseBody
    LightDTO updateEndTime(@PathVariable final long houseId,
                           @PathVariable final long roomId,
                           @PathVariable final long lightId,
                           @AuthenticationPrincipal final Authentication auth,
                           @RequestBody final LocalTime endTime) {

        return lightService.updateEndTime(lightId, endTime);
    }

    @PutMapping(value = "/away")
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public @ResponseBody
    LightDTO toggleAwayMode(@PathVariable final long houseId,
                            @PathVariable final long roomId,
                            @PathVariable final long lightId,
                            @AuthenticationPrincipal final Authentication auth,
                            @RequestBody final boolean desiredState) {

        return lightService.toggleAwayMode(auth.getName(), lightId, desiredState);
    }


}
