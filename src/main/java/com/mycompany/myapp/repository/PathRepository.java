package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Path;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Path entity.
 */
public interface PathRepository extends JpaRepository<Path,Long> {

}
