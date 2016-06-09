package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.CarType;
import com.mycompany.myapp.service.CarTypeService;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import com.mycompany.myapp.web.rest.util.PaginationUtil;
import com.mycompany.myapp.web.rest.dto.CarTypeDTO;
import com.mycompany.myapp.web.rest.mapper.CarTypeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing CarType.
 */
@RestController
@RequestMapping("/api")
public class CarTypeResource {

    private final Logger log = LoggerFactory.getLogger(CarTypeResource.class);
        
    @Inject
    private CarTypeService carTypeService;
    
    @Inject
    private CarTypeMapper carTypeMapper;
    
    /**
     * POST  /car-types : Create a new carType.
     *
     * @param carTypeDTO the carTypeDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new carTypeDTO, or with status 400 (Bad Request) if the carType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/car-types",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CarTypeDTO> createCarType(@RequestBody CarTypeDTO carTypeDTO) throws URISyntaxException {
        log.debug("REST request to save CarType : {}", carTypeDTO);
        if (carTypeDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("carType", "idexists", "A new carType cannot already have an ID")).body(null);
        }
        CarTypeDTO result = carTypeService.save(carTypeDTO);
        return ResponseEntity.created(new URI("/api/car-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("carType", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /car-types : Updates an existing carType.
     *
     * @param carTypeDTO the carTypeDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated carTypeDTO,
     * or with status 400 (Bad Request) if the carTypeDTO is not valid,
     * or with status 500 (Internal Server Error) if the carTypeDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/car-types",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CarTypeDTO> updateCarType(@RequestBody CarTypeDTO carTypeDTO) throws URISyntaxException {
        log.debug("REST request to update CarType : {}", carTypeDTO);
        if (carTypeDTO.getId() == null) {
            return createCarType(carTypeDTO);
        }
        CarTypeDTO result = carTypeService.save(carTypeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("carType", carTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /car-types : get all the carTypes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of carTypes in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/car-types",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public ResponseEntity<List<CarTypeDTO>> getAllCarTypes(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of CarTypes");
        Page<CarType> page = carTypeService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/car-types");
        return new ResponseEntity<>(carTypeMapper.carTypesToCarTypeDTOs(page.getContent()), headers, HttpStatus.OK);
    }

    /**
     * GET  /car-types/:id : get the "id" carType.
     *
     * @param id the id of the carTypeDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the carTypeDTO, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/car-types/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CarTypeDTO> getCarType(@PathVariable Long id) {
        log.debug("REST request to get CarType : {}", id);
        CarTypeDTO carTypeDTO = carTypeService.findOne(id);
        return Optional.ofNullable(carTypeDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /car-types/:id : delete the "id" carType.
     *
     * @param id the id of the carTypeDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/car-types/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteCarType(@PathVariable Long id) {
        log.debug("REST request to delete CarType : {}", id);
        carTypeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("carType", id.toString())).build();
    }

}
