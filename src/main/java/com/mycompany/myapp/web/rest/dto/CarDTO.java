package com.mycompany.myapp.web.rest.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A DTO for the Car entity.
 */
public class CarDTO implements Serializable {

    private Long id;

    private Integer seatCount;


    private Long driverId;

    private String driverLastName;

    private Long carBrandId;

    private String carBrandNameBrand;

    private Long carTypeId;

    private String carTypeNameType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Integer getSeatCount() {
        return seatCount;
    }

    public void setSeatCount(Integer seatCount) {
        this.seatCount = seatCount;
    }

    public Long getDriverId() {
        return driverId;
    }

    public void setDriverId(Long driverId) {
        this.driverId = driverId;
    }

    public String getDriverLastName() {
        return driverLastName;
    }

    public void setDriverLastName(String driverLastName) {
        this.driverLastName = driverLastName;
    }

    public Long getCarBrandId() {
        return carBrandId;
    }

    public void setCarBrandId(Long carBrandId) {
        this.carBrandId = carBrandId;
    }

    public String getCarBrandNameBrand() {
        return carBrandNameBrand;
    }

    public void setCarBrandNameBrand(String carBrandNameBrand) {
        this.carBrandNameBrand = carBrandNameBrand;
    }

    public Long getCarTypeId() {
        return carTypeId;
    }

    public void setCarTypeId(Long carTypeId) {
        this.carTypeId = carTypeId;
    }

    public String getCarTypeNameType() {
        return carTypeNameType;
    }

    public void setCarTypeNameType(String carTypeNameType) {
        this.carTypeNameType = carTypeNameType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CarDTO carDTO = (CarDTO) o;

        if ( ! Objects.equals(id, carDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "CarDTO{" +
            "id=" + id +
            ", seatCount='" + seatCount + "'" +
            '}';
    }
}
