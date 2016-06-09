package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Path;
import com.mycompany.myapp.repository.PathRepository;
import com.mycompany.myapp.web.rest.dto.PathDTO;
import com.mycompany.myapp.web.rest.mapper.PathMapper;
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
 * Service Implementation for managing Path.
 */
@Service
@Transactional
public class PathService {

    private final Logger log = LoggerFactory.getLogger(PathService.class);
    
    @Inject
    private PathRepository pathRepository;
    
    @Inject
    private PathMapper pathMapper;
    
    /**
     * Save a path.
     * 
     * @param pathDTO the entity to save
     * @return the persisted entity
     */
    public PathDTO save(PathDTO pathDTO) {
        log.debug("Request to save Path : {}", pathDTO);
        Path path = pathMapper.pathDTOToPath(pathDTO);
        path = pathRepository.save(path);
        PathDTO result = pathMapper.pathToPathDTO(path);
        return result;
    }

    /**
     *  Get all the paths.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Path> findAll(Pageable pageable) {
        log.debug("Request to get all Paths");
        Page<Path> result = pathRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one path by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public PathDTO findOne(Long id) {
        log.debug("Request to get Path : {}", id);
        Path path = pathRepository.findOne(id);
        PathDTO pathDTO = pathMapper.pathToPathDTO(path);
        return pathDTO;
    }

    /**
     *  Delete the  path by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Path : {}", id);
        pathRepository.delete(id);
    }
}
