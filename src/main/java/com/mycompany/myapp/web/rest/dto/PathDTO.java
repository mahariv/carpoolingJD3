package com.mycompany.myapp.web.rest.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A DTO for the Path entity.
 */
public class PathDTO implements Serializable {

    private Long id;

    private String namePath;


    private Integer numOfDistance;


    private Long timeDuration;


    private Long locationStartId;

    private String locationStartNameLocation;

    private Long locationEndId;

    private String locationEndNameLocation;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getNamePath() {
        return namePath;
    }

    public void setNamePath(String namePath) {
        this.namePath = namePath;
    }
    public Integer getNumOfDistance() {
        return numOfDistance;
    }

    public void setNumOfDistance(Integer numOfDistance) {
        this.numOfDistance = numOfDistance;
    }
    public Long getTimeDuration() {
        return timeDuration;
    }

    public void setTimeDuration(Long timeDuration) {
        this.timeDuration = timeDuration;
    }

    public Long getLocationStartId() {
        return locationStartId;
    }

    public void setLocationStartId(Long locationId) {
        this.locationStartId = locationId;
    }

    public String getLocationStartNameLocation() {
        return locationStartNameLocation;
    }

    public void setLocationStartNameLocation(String locationNameLocation) {
        this.locationStartNameLocation = locationNameLocation;
    }

    public Long getLocationEndId() {
        return locationEndId;
    }

    public void setLocationEndId(Long locationId) {
        this.locationEndId = locationId;
    }

    public String getLocationEndNameLocation() {
        return locationEndNameLocation;
    }

    public void setLocationEndNameLocation(String locationNameLocation) {
        this.locationEndNameLocation = locationNameLocation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PathDTO pathDTO = (PathDTO) o;

        if ( ! Objects.equals(id, pathDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PathDTO{" +
            "id=" + id +
            ", namePath='" + namePath + "'" +
            ", numOfDistance='" + numOfDistance + "'" +
            ", timeDuration='" + timeDuration + "'" +
            '}';
    }
}
