package com.soen343.shs.dal.controllers;

import com.soen343.shs.dal.service.HouseService;
import com.soen343.shs.dto.HouseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/house")
public class HouseController {
    private final HouseService houseService;

    @GetMapping(value = "/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public @ResponseBody
    HouseDTO getHouse(@PathVariable final long id) {
        return houseService.getHouse(id);
    }

    @GetMapping(value = "/houses")
    @ResponseStatus(value = HttpStatus.OK)
    public Set<HouseDTO> getAllHouses() {
        return houseService.getAllHouses();
    }
}
