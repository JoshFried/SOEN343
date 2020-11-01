package com.soen343.shs.dal.controllers;

import com.soen343.shs.dal.service.SHSSecurityService;
import com.soen343.shs.dto.SecuritySystemDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "api/security")
public class SecuritySystemController {
    private final SHSSecurityService securityService;

    @GetMapping(value = "/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public SecuritySystemDTO getRoom(@PathVariable final long id) {
        return securityService.getSHSSecurity(id);
    }

    @PutMapping(value = "/{id}/away")
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public SecuritySystemDTO setAwayMode(@PathVariable final long id, @RequestBody final boolean desiredState) {
        return securityService.toggleAway(desiredState, id);
    }


}
