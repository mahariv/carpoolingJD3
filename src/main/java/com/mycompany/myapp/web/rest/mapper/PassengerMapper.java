package com.mycompany.myapp.web.rest.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.web.rest.dto.PassengerDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Passenger and its DTO PassengerDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PassengerMapper {

    PassengerDTO passengerToPassengerDTO(Passenger passenger);

    List<PassengerDTO> passengersToPassengerDTOs(List<Passenger> passengers);

    Passenger passengerDTOToPassenger(PassengerDTO passengerDTO);

    List<Passenger> passengerDTOsToPassengers(List<PassengerDTO> passengerDTOs);
}
