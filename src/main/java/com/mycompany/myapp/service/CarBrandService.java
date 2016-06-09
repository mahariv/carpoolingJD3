package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.CarBrand;
import com.mycompany.myapp.repository.CarBrandRepository;
import com.mycompany.myapp.web.rest.dto.CarBrandDTO;
import com.mycompany.myapp.web.rest.mapper.CarBrandMapper;
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
 * Service Implementation for managing CarBrand.
 */
@Service
@Transactional
public class CarBrandService {

    private final Logger log = LoggerFactory.getLogger(CarBrandService.class);
    
    @Inject
    private CarBrandRepository carBrandRepository;
    
    @Inject
    private CarBrandMapper carBrandMapper;
    
    /**
     * Save a carBrand.
     * 
     * @param carBrandDTO the entity to save
     * @return the persisted entity
     */
    public CarBrandDTO save(CarBrandDTO carBrandDTO) {
        log.debug("Request to save CarBrand : {}", carBrandDTO);
        CarBrand carBrand = carBrandMapper.carBrandDTOToCarBrand(carBrandDTO);
        carBrand = carBrandRepository.save(carBrand);
        CarBrandDTO result = carBrandMapper.carBrandToCarBrandDTO(carBrand);
        return result;
    }

    /**
     *  Get all the carBrands.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<CarBrand> findAll(Pageable pageable) {
        log.debug("Request to get all CarBrands");
        Page<CarBrand> result = carBrandRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one carBrand by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public CarBrandDTO findOne(Long id) {
        log.debug("Request to get CarBrand : {}", id);
        CarBrand carBrand = carBrandRepository.findOne(id);
        CarBrandDTO carBrandDTO = carBrandMapper.carBrandToCarBrandDTO(carBrand);
        return carBrandDTO;
    }

    /**
     *  Delete the  carBrand by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete CarBrand : {}", id);
        carBrandRepository.delete(id);
    }
}
