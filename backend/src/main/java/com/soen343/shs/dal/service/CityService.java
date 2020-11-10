package com.soen343.shs.dal.service;

import com.soen343.shs.dal.model.City;
import com.soen343.shs.dal.repository.CityRepository;
import com.soen343.shs.dal.service.exceptions.state.SHSNotFoundException;
import com.soen343.shs.dto.CityDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CityService {
    private final CityRepository cityRepository;
    private final ConversionService mvcConversionService;


    private City fetchCity(final String name) {
        return cityRepository.findByName(name).orElseThrow(() -> new SHSNotFoundException(String.format("City %s doesn't exist!", name)));
    }

    public CityDTO getCity(final String name) {
        return mvcConversionService.convert(fetchCity(name), CityDTO.class);
    }

    /**
     * @param name        name of the city
     * @param temperature new outside temperature
     * @return HouseDTO object reflecting the changes made to the object
     */
    public CityDTO setTemperatureOutside(final String name, final double temperature) {
        final City city = fetchCity(name);
        city.setTemperatureOutside(temperature);
        return mvcConversionService.convert(cityRepository.save(city), CityDTO.class);
    }

    /**
     * @param name name of the city
     * @return DTO representing current city
     */
    public CityDTO getTemperatureOutside(final String name) {
        return getCity(name);
    }
}
