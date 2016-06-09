package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.CarType;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the CarType entity.
 */
public interface CarTypeRepository extends JpaRepository<CarType,Long> {

}
