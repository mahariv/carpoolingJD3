package com.mycompany.myapp.web.rest.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.web.rest.dto.CarBrandDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity CarBrand and its DTO CarBrandDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CarBrandMapper {

    CarBrandDTO carBrandToCarBrandDTO(CarBrand carBrand);

    List<CarBrandDTO> carBrandsToCarBrandDTOs(List<CarBrand> carBrands);

    CarBrand carBrandDTOToCarBrand(CarBrandDTO carBrandDTO);

    List<CarBrand> carBrandDTOsToCarBrands(List<CarBrandDTO> carBrandDTOs);
}
