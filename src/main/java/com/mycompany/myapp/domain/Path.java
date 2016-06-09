package com.mycompany.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Path.
 */
@Entity
@Table(name = "path")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Path implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name_path")
    private String namePath;

    @Column(name = "num_of_distance")
    private Integer numOfDistance;

    @Column(name = "time_duration")
    private Long timeDuration;

    @ManyToOne
    private Location locationStart;

    @ManyToOne
    private Location locationEnd;

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

    public Location getLocationStart() {
        return locationStart;
    }

    public void setLocationStart(Location location) {
        this.locationStart = location;
    }

    public Location getLocationEnd() {
        return locationEnd;
    }

    public void setLocationEnd(Location location) {
        this.locationEnd = location;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Path path = (Path) o;
        if(path.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, path.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Path{" +
            "id=" + id +
            ", namePath='" + namePath + "'" +
            ", numOfDistance='" + numOfDistance + "'" +
            ", timeDuration='" + timeDuration + "'" +
            '}';
    }
}
