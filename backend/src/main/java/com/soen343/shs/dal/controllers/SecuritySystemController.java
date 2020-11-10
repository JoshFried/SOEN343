package com.soen343.shs.dal.controllers;

import com.soen343.shs.dal.service.SecuritySystemService;
import com.soen343.shs.dto.SecuritySystemDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "api/security")
public class SecuritySystemController {
    private final SecuritySystemService securityService;

    @GetMapping(value = "/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public SecuritySystemDTO getRoom(@PathVariable final long id) {
        return securityService.getSHSSecurity(id);
    }

    @PutMapping(value = "/{id}/away")
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public SecuritySystemDTO setAwayMode(@PathVariable final long id,
                                         @AuthenticationPrincipal final Authentication auth,
                                         @RequestBody final boolean desiredState) {

        return securityService.toggleAway(auth.getName(), desiredState, id);
    }

    @PutMapping(value = "/{id}/auto")
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public SecuritySystemDTO setAutoMode(@PathVariable final long id,
                                         @RequestBody final boolean desiredState) {

        return securityService.toggleAutoMode(id, desiredState);
    }

    @PutMapping(value = "/{id}/delay")
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public SecuritySystemDTO updateDelay(@PathVariable final long id,
                                         @AuthenticationPrincipal final Authentication auth,
                                         @RequestBody final long delay) {

        return securityService.updateIntruderDetectionDelay(auth.getName(), id, delay);
    }


}
