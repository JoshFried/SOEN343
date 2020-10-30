package com.soen343.shs.dal.controllers;

import com.soen343.shs.dal.service.CityService;
import com.soen343.shs.dal.service.SimulationService;
import com.soen343.shs.dto.CityDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "api/city")
public class CityController {
    private final CityService cityService;

    @PutMapping(value = "/{name}/temperatureOutside/{temperatureOutside}")
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public @ResponseBody
    CityDTO updateTemperatureOutside(@PathVariable final String name, @PathVariable final double temperatureOutside) {
        return cityService.setTemperatureOutside(name, temperatureOutside);
    }
    /api/simulation/api/city/Montreal
    @GetMapping(value = "/{name}")
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public @ResponseBody
    CityDTO getCity(@PathVariable final String name) {
        return cityService.getCity(name);
    }
}
