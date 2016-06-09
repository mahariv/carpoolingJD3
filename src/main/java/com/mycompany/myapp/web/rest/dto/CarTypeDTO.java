package com.mycompany.myapp.web.rest.dto;

import java.io.Serializable;
import java.util.Objects;


/**
 * A DTO for the CarType entity.
 */
public class CarTypeDTO implements Serializable {

    private Long id;

    private String nameType;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getNameType() {
        return nameType;
    }

    public void setNameType(String nameType) {
        this.nameType = nameType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CarTypeDTO carTypeDTO = (CarTypeDTO) o;

        if ( ! Objects.equals(id, carTypeDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "CarTypeDTO{" +
            "id=" + id +
            ", nameType='" + nameType + "'" +
            '}';
    }
}
