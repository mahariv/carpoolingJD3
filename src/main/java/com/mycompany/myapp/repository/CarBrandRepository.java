package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.CarBrand;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the CarBrand entity.
 */
public interface CarBrandRepository extends JpaRepository<CarBrand,Long> {

}
