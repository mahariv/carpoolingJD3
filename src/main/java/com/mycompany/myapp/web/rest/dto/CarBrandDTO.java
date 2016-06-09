package com.mycompany.myapp.web.rest.dto;

import java.io.Serializable;
import java.util.Objects;


/**
 * A DTO for the CarBrand entity.
 */
public class CarBrandDTO implements Serializable {

    private Long id;

    private String nameBrand;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getNameBrand() {
        return nameBrand;
    }

    public void setNameBrand(String nameBrand) {
        this.nameBrand = nameBrand;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CarBrandDTO carBrandDTO = (CarBrandDTO) o;

        if ( ! Objects.equals(id, carBrandDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "CarBrandDTO{" +
            "id=" + id +
            ", nameBrand='" + nameBrand + "'" +
            '}';
    }
}
