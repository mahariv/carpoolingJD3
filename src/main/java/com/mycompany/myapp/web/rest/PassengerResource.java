package com.mycompany.myapp.web.rest;
import com.mycompany.myapp.security.AuthoritiesConstants;
import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Passenger;
import com.mycompany.myapp.service.PassengerService;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import com.mycompany.myapp.web.rest.util.PaginationUtil;
import com.mycompany.myapp.web.rest.dto.PassengerDTO;
import com.mycompany.myapp.web.rest.mapper.PassengerMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;
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
 * REST controller for managing Passenger.
 */
@RestController
@RequestMapping("/api")
public class PassengerResource {

    private final Logger log = LoggerFactory.getLogger(PassengerResource.class);
        
    @Inject
    private PassengerService passengerService;
    
    @Inject
    private PassengerMapper passengerMapper;
    
    /**
     * POST  /passengers : Create a new passenger.
     *
     * @param passengerDTO the passengerDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new passengerDTO, or with status 400 (Bad Request) if the passenger has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/passengers",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Secured(AuthoritiesConstants.ADMIN)
    public ResponseEntity<PassengerDTO> createPassenger(@RequestBody PassengerDTO passengerDTO) throws URISyntaxException {
        log.debug("REST request to save Passenger : {}", passengerDTO);
        if (passengerDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("passenger", "idexists", "A new passenger cannot already have an ID")).body(null);
        }
        PassengerDTO result = passengerService.save(passengerDTO);
        return ResponseEntity.created(new URI("/api/passengers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("passenger", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /passengers : Updates an existing passenger.
     *
     * @param passengerDTO the passengerDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated passengerDTO,
     * or with status 400 (Bad Request) if the passengerDTO is not valid,
     * or with status 500 (Internal Server Error) if the passengerDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/passengers",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Secured(AuthoritiesConstants.ADMIN)
    public ResponseEntity<PassengerDTO> updatePassenger(@RequestBody PassengerDTO passengerDTO) throws URISyntaxException {
        log.debug("REST request to update Passenger : {}", passengerDTO);
        if (passengerDTO.getId() == null) {
            return createPassenger(passengerDTO);
        }
        PassengerDTO result = passengerService.save(passengerDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("passenger", passengerDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /passengers : get all the passengers.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of passengers in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/passengers",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Secured(AuthoritiesConstants.ADMIN)
    @Transactional(readOnly = true)
    public ResponseEntity<List<PassengerDTO>> getAllPassengers(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Passengers");
        Page<Passenger> page = passengerService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/passengers");
        return new ResponseEntity<>(passengerMapper.passengersToPassengerDTOs(page.getContent()), headers, HttpStatus.OK);
    }

    /**
     * GET  /passengers/:id : get the "id" passenger.
     *
     * @param id the id of the passengerDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the passengerDTO, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/passengers/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Secured(AuthoritiesConstants.ADMIN)
    public ResponseEntity<PassengerDTO> getPassenger(@PathVariable Long id) {
        log.debug("REST request to get Passenger : {}", id);
        PassengerDTO passengerDTO = passengerService.findOne(id);
        return Optional.ofNullable(passengerDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /passengers/:id : delete the "id" passenger.
     *
     * @param id the id of the passengerDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/passengers/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Secured(AuthoritiesConstants.ADMIN)
    public ResponseEntity<Void> deletePassenger(@PathVariable Long id) {
        log.debug("REST request to delete Passenger : {}", id);
        passengerService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("passenger", id.toString())).build();
    }

}
