package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.CarBrand;
import com.mycompany.myapp.service.CarBrandService;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import com.mycompany.myapp.web.rest.util.PaginationUtil;
import com.mycompany.myapp.web.rest.dto.CarBrandDTO;
import com.mycompany.myapp.web.rest.mapper.CarBrandMapper;
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
 * REST controller for managing CarBrand.
 */
@RestController
@RequestMapping("/api")
public class CarBrandResource {

    private final Logger log = LoggerFactory.getLogger(CarBrandResource.class);
        
    @Inject
    private CarBrandService carBrandService;
    
    @Inject
    private CarBrandMapper carBrandMapper;
    
    /**
     * POST  /car-brands : Create a new carBrand.
     *
     * @param carBrandDTO the carBrandDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new carBrandDTO, or with status 400 (Bad Request) if the carBrand has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/car-brands",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CarBrandDTO> createCarBrand(@RequestBody CarBrandDTO carBrandDTO) throws URISyntaxException {
        log.debug("REST request to save CarBrand : {}", carBrandDTO);
        if (carBrandDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("carBrand", "idexists", "A new carBrand cannot already have an ID")).body(null);
        }
        CarBrandDTO result = carBrandService.save(carBrandDTO);
        return ResponseEntity.created(new URI("/api/car-brands/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("carBrand", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /car-brands : Updates an existing carBrand.
     *
     * @param carBrandDTO the carBrandDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated carBrandDTO,
     * or with status 400 (Bad Request) if the carBrandDTO is not valid,
     * or with status 500 (Internal Server Error) if the carBrandDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/car-brands",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CarBrandDTO> updateCarBrand(@RequestBody CarBrandDTO carBrandDTO) throws URISyntaxException {
        log.debug("REST request to update CarBrand : {}", carBrandDTO);
        if (carBrandDTO.getId() == null) {
            return createCarBrand(carBrandDTO);
        }
        CarBrandDTO result = carBrandService.save(carBrandDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("carBrand", carBrandDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /car-brands : get all the carBrands.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of carBrands in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/car-brands",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public ResponseEntity<List<CarBrandDTO>> getAllCarBrands(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of CarBrands");
        Page<CarBrand> page = carBrandService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/car-brands");
        return new ResponseEntity<>(carBrandMapper.carBrandsToCarBrandDTOs(page.getContent()), headers, HttpStatus.OK);
    }

    /**
     * GET  /car-brands/:id : get the "id" carBrand.
     *
     * @param id the id of the carBrandDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the carBrandDTO, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/car-brands/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CarBrandDTO> getCarBrand(@PathVariable Long id) {
        log.debug("REST request to get CarBrand : {}", id);
        CarBrandDTO carBrandDTO = carBrandService.findOne(id);
        return Optional.ofNullable(carBrandDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /car-brands/:id : delete the "id" carBrand.
     *
     * @param id the id of the carBrandDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/car-brands/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteCarBrand(@PathVariable Long id) {
        log.debug("REST request to delete CarBrand : {}", id);
        carBrandService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("carBrand", id.toString())).build();
    }

}
