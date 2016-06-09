package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Passenger;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Passenger entity.
 */
public interface PassengerRepository extends JpaRepository<Passenger,Long> {

}
