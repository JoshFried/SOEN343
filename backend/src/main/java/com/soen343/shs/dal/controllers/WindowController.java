package com.soen343.shs.dal.controllers;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.soen343.shs.dal.service.WindowService;
import com.soen343.shs.dto.WindowDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class WindowController {

    private final WindowService windowService;

    @PutMapping(value = "/api/house/{houseId}/room/{roomId}/window/{windowId}")
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public @ResponseBody
    WindowDTO modifyWindowState(@PathVariable final long houseId,
                                @PathVariable final long roomId,
                                @PathVariable final long windowId,
                                @AuthenticationPrincipal final Authentication auth,
                                @RequestBody final ObjectNode objectNode) {
        return windowService
                .modifyWindowState(
                        auth.getName(),
                        roomId,
                        windowId,
                        objectNode.get("open").asBoolean(),
                        objectNode.get("desiredState").asBoolean()
                );
    }
}
