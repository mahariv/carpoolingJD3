package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Path;
import com.mycompany.myapp.service.PathService;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import com.mycompany.myapp.web.rest.util.PaginationUtil;
import com.mycompany.myapp.web.rest.dto.PathDTO;
import com.mycompany.myapp.web.rest.mapper.PathMapper;
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
 * REST controller for managing Path.
 */
@RestController
@RequestMapping("/api")
public class PathResource {

    private final Logger log = LoggerFactory.getLogger(PathResource.class);
        
    @Inject
    private PathService pathService;
    
    @Inject
    private PathMapper pathMapper;
    
    /**
     * POST  /paths : Create a new path.
     *
     * @param pathDTO the pathDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new pathDTO, or with status 400 (Bad Request) if the path has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/paths",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PathDTO> createPath(@RequestBody PathDTO pathDTO) throws URISyntaxException {
        log.debug("REST request to save Path : {}", pathDTO);
        if (pathDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("path", "idexists", "A new path cannot already have an ID")).body(null);
        }
        PathDTO result = pathService.save(pathDTO);
        return ResponseEntity.created(new URI("/api/paths/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("path", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /paths : Updates an existing path.
     *
     * @param pathDTO the pathDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated pathDTO,
     * or with status 400 (Bad Request) if the pathDTO is not valid,
     * or with status 500 (Internal Server Error) if the pathDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/paths",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PathDTO> updatePath(@RequestBody PathDTO pathDTO) throws URISyntaxException {
        log.debug("REST request to update Path : {}", pathDTO);
        if (pathDTO.getId() == null) {
            return createPath(pathDTO);
        }
        PathDTO result = pathService.save(pathDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("path", pathDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /paths : get all the paths.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of paths in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/paths",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public ResponseEntity<List<PathDTO>> getAllPaths(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Paths");
        Page<Path> page = pathService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/paths");
        return new ResponseEntity<>(pathMapper.pathsToPathDTOs(page.getContent()), headers, HttpStatus.OK);
    }

    /**
     * GET  /paths/:id : get the "id" path.
     *
     * @param id the id of the pathDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the pathDTO, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/paths/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PathDTO> getPath(@PathVariable Long id) {
        log.debug("REST request to get Path : {}", id);
        PathDTO pathDTO = pathService.findOne(id);
        return Optional.ofNullable(pathDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /paths/:id : delete the "id" path.
     *
     * @param id the id of the pathDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/paths/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deletePath(@PathVariable Long id) {
        log.debug("REST request to delete Path : {}", id);
        pathService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("path", id.toString())).build();
    }

}
