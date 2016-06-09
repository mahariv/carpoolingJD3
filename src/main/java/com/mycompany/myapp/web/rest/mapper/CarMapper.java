package com.mycompany.myapp.web.rest.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.web.rest.dto.CarDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Car and its DTO CarDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CarMapper {

    @Mapping(source = "driver.id", target = "driverId")
    @Mapping(source = "driver.lastName", target = "driverLastName")
    @Mapping(source = "carBrand.id", target = "carBrandId")
    @Mapping(source = "carBrand.nameBrand", target = "carBrandNameBrand")
    @Mapping(source = "carType.id", target = "carTypeId")
    @Mapping(source = "carType.nameType", target = "carTypeNameType")
    CarDTO carToCarDTO(Car car);

    List<CarDTO> carsToCarDTOs(List<Car> cars);

    @Mapping(source = "driverId", target = "driver")
    @Mapping(source = "carBrandId", target = "carBrand")
    @Mapping(source = "carTypeId", target = "carType")
    Car carDTOToCar(CarDTO carDTO);

    List<Car> carDTOsToCars(List<CarDTO> carDTOs);

    default Driver driverFromId(Long id) {
        if (id == null) {
            return null;
        }
        Driver driver = new Driver();
        driver.setId(id);
        return driver;
    }

    default CarBrand carBrandFromId(Long id) {
        if (id == null) {
            return null;
        }
        CarBrand carBrand = new CarBrand();
        carBrand.setId(id);
        return carBrand;
    }

    default CarType carTypeFromId(Long id) {
        if (id == null) {
            return null;
        }
        CarType carType = new CarType();
        carType.setId(id);
        return carType;
    }
}
