package com.soen343.shs.dal.service;

import com.google.common.collect.Sets;
import com.soen343.shs.dal.model.House;
import com.soen343.shs.dal.model.Room;
import com.soen343.shs.dal.repository.HouseRepository;
import com.soen343.shs.dal.repository.mapping.SHSHouseMapper;
import com.soen343.shs.dal.service.exceptions.house.HouseNotFoundException;
import com.soen343.shs.dal.service.validators.helper.ErrorMessageGenerator;
import com.soen343.shs.dto.HouseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HouseService {

    private final HouseRepository houseRepository;
    private final ConversionService mvcConversionService;

    public HouseDTO getHouse(final long id) {
        return mvcConversionService.convert(fetchHouse(id), HouseDTO.class);
    }

    House fetchHouse(final long id) {
        return houseRepository.findById(id).orElseThrow(() -> new HouseNotFoundException(ErrorMessageGenerator.getSHSNotFoundErrorMessage(id, House.class)));
    }

    public HouseDTO updateHouse(final HouseDTO dto) {
        houseRepository.save(SHSHouseMapper.mapHouseDTOToHouse(dto, fetchHouse(dto.getId())));
        return dto;
    }

    /**
     * @param houseId a house id
     * @return inside temperature
     */
    public double getTemperatureInside(final long houseId) {
        return fetchHouse(houseId)
                .getRooms()
                .stream()
                .mapToDouble(Room::getTemperature)
                .average()
                .orElse(0);
    }

    public Set<HouseDTO> getAllHouses() {
        return Sets.newHashSet(houseRepository.findAll())
                .stream()
                .map(house -> mvcConversionService.convert(house, HouseDTO.class))
                .collect(Collectors.toSet());
    }
}
