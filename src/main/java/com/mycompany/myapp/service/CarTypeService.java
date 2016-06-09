package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.CarType;
import com.mycompany.myapp.repository.CarTypeRepository;
import com.mycompany.myapp.web.rest.dto.CarTypeDTO;
import com.mycompany.myapp.web.rest.mapper.CarTypeMapper;
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
 * Service Implementation for managing CarType.
 */
@Service
@Transactional
public class CarTypeService {

    private final Logger log = LoggerFactory.getLogger(CarTypeService.class);
    
    @Inject
    private CarTypeRepository carTypeRepository;
    
    @Inject
    private CarTypeMapper carTypeMapper;
    
    /**
     * Save a carType.
     * 
     * @param carTypeDTO the entity to save
     * @return the persisted entity
     */
    public CarTypeDTO save(CarTypeDTO carTypeDTO) {
        log.debug("Request to save CarType : {}", carTypeDTO);
        CarType carType = carTypeMapper.carTypeDTOToCarType(carTypeDTO);
        carType = carTypeRepository.save(carType);
        CarTypeDTO result = carTypeMapper.carTypeToCarTypeDTO(carType);
        return result;
    }

    /**
     *  Get all the carTypes.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<CarType> findAll(Pageable pageable) {
        log.debug("Request to get all CarTypes");
        Page<CarType> result = carTypeRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one carType by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public CarTypeDTO findOne(Long id) {
        log.debug("Request to get CarType : {}", id);
        CarType carType = carTypeRepository.findOne(id);
        CarTypeDTO carTypeDTO = carTypeMapper.carTypeToCarTypeDTO(carType);
        return carTypeDTO;
    }

    /**
     *  Delete the  carType by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete CarType : {}", id);
        carTypeRepository.delete(id);
    }
}
