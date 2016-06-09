package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Passenger;
import com.mycompany.myapp.repository.PassengerRepository;
import com.mycompany.myapp.web.rest.dto.PassengerDTO;
import com.mycompany.myapp.web.rest.mapper.PassengerMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Passenger.
 */
@Service
@Transactional
public class PassengerService {

    private final Logger log = LoggerFactory.getLogger(PassengerService.class);
    
    @Inject
    private PassengerRepository passengerRepository;
    
    @Inject
    private PassengerMapper passengerMapper;
    
    /**
     * Save a passenger.
     * 
     * @param passengerDTO the entity to save
     * @return the persisted entity
     */
    public PassengerDTO save(PassengerDTO passengerDTO) {
        log.debug("Request to save Passenger : {}", passengerDTO);
        Passenger passenger = passengerMapper.passengerDTOToPassenger(passengerDTO);
        passenger = passengerRepository.save(passenger);
        PassengerDTO result = passengerMapper.passengerToPassengerDTO(passenger);
        return result;
    }

    /**
     *  Get all the passengers.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Passenger> findAll(Pageable pageable) {
        log.debug("Request to get all Passengers");
        Page<Passenger> result = passengerRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one passenger by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public PassengerDTO findOne(Long id) {
        log.debug("Request to get Passenger : {}", id);
        Passenger passenger = passengerRepository.findOne(id);
        PassengerDTO passengerDTO = passengerMapper.passengerToPassengerDTO(passenger);
        return passengerDTO;
    }

    /**
     *  Delete the  passenger by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Passenger : {}", id);
        passengerRepository.delete(id);
    }
}
