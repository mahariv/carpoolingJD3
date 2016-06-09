package com.mycompany.myapp.web.rest.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.web.rest.dto.CarTypeDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity CarType and its DTO CarTypeDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CarTypeMapper {

    CarTypeDTO carTypeToCarTypeDTO(CarType carType);

    List<CarTypeDTO> carTypesToCarTypeDTOs(List<CarType> carTypes);

    CarType carTypeDTOToCarType(CarTypeDTO carTypeDTO);

    List<CarType> carTypeDTOsToCarTypes(List<CarTypeDTO> carTypeDTOs);
}
