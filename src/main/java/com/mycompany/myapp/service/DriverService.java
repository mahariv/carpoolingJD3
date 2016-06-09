package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Driver;
import com.mycompany.myapp.repository.DriverRepository;
import com.mycompany.myapp.web.rest.dto.DriverDTO;
import com.mycompany.myapp.web.rest.mapper.DriverMapper;
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
 * Service Implementation for managing Driver.
 */
@Service
@Transactional
public class DriverService {

    private final Logger log = LoggerFactory.getLogger(DriverService.class);
    
    @Inject
    private DriverRepository driverRepository;
    
    @Inject
    private DriverMapper driverMapper;
    
    /**
     * Save a driver.
     * 
     * @param driverDTO the entity to save
     * @return the persisted entity
     */
    public DriverDTO save(DriverDTO driverDTO) {
        log.debug("Request to save Driver : {}", driverDTO);
        Driver driver = driverMapper.driverDTOToDriver(driverDTO);
        driver = driverRepository.save(driver);
        DriverDTO result = driverMapper.driverToDriverDTO(driver);
        return result;
    }

    /**
     *  Get all the drivers.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Driver> findAll(Pageable pageable) {
        log.debug("Request to get all Drivers");
        Page<Driver> result = driverRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one driver by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public DriverDTO findOne(Long id) {
        log.debug("Request to get Driver : {}", id);
        Driver driver = driverRepository.findOne(id);
        DriverDTO driverDTO = driverMapper.driverToDriverDTO(driver);
        return driverDTO;
    }

    /**
     *  Delete the  driver by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Driver : {}", id);
        driverRepository.delete(id);
    }
}
