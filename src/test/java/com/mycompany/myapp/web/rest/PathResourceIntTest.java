package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.CarpoolingCsid2016App;
import com.mycompany.myapp.domain.Path;
import com.mycompany.myapp.repository.PathRepository;
import com.mycompany.myapp.service.PathService;
import com.mycompany.myapp.web.rest.dto.PathDTO;
import com.mycompany.myapp.web.rest.mapper.PathMapper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the PathResource REST controller.
 *
 * @see PathResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = CarpoolingCsid2016App.class)
@WebAppConfiguration
@IntegrationTest
public class PathResourceIntTest {

    private static final String DEFAULT_NAME_PATH = "AAAAA";
    private static final String UPDATED_NAME_PATH = "BBBBB";

    private static final Integer DEFAULT_NUM_OF_DISTANCE = 1;
    private static final Integer UPDATED_NUM_OF_DISTANCE = 2;

    private static final Long DEFAULT_TIME_DURATION = 1L;
    private static final Long UPDATED_TIME_DURATION = 2L;

    @Inject
    private PathRepository pathRepository;

    @Inject
    private PathMapper pathMapper;

    @Inject
    private PathService pathService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restPathMockMvc;

    private Path path;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PathResource pathResource = new PathResource();
        ReflectionTestUtils.setField(pathResource, "pathService", pathService);
        ReflectionTestUtils.setField(pathResource, "pathMapper", pathMapper);
        this.restPathMockMvc = MockMvcBuilders.standaloneSetup(pathResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        path = new Path();
        path.setNamePath(DEFAULT_NAME_PATH);
        path.setNumOfDistance(DEFAULT_NUM_OF_DISTANCE);
        path.setTimeDuration(DEFAULT_TIME_DURATION);
    }

    @Test
    @Transactional
    public void createPath() throws Exception {
        int databaseSizeBeforeCreate = pathRepository.findAll().size();

        // Create the Path
        PathDTO pathDTO = pathMapper.pathToPathDTO(path);

        restPathMockMvc.perform(post("/api/paths")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pathDTO)))
                .andExpect(status().isCreated());

        // Validate the Path in the database
        List<Path> paths = pathRepository.findAll();
        assertThat(paths).hasSize(databaseSizeBeforeCreate + 1);
        Path testPath = paths.get(paths.size() - 1);
        assertThat(testPath.getNamePath()).isEqualTo(DEFAULT_NAME_PATH);
        assertThat(testPath.getNumOfDistance()).isEqualTo(DEFAULT_NUM_OF_DISTANCE);
        assertThat(testPath.getTimeDuration()).isEqualTo(DEFAULT_TIME_DURATION);
    }

    @Test
    @Transactional
    public void getAllPaths() throws Exception {
        // Initialize the database
        pathRepository.saveAndFlush(path);

        // Get all the paths
        restPathMockMvc.perform(get("/api/paths?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(path.getId().intValue())))
                .andExpect(jsonPath("$.[*].namePath").value(hasItem(DEFAULT_NAME_PATH.toString())))
                .andExpect(jsonPath("$.[*].numOfDistance").value(hasItem(DEFAULT_NUM_OF_DISTANCE)))
                .andExpect(jsonPath("$.[*].timeDuration").value(hasItem(DEFAULT_TIME_DURATION.intValue())));
    }

    @Test
    @Transactional
    public void getPath() throws Exception {
        // Initialize the database
        pathRepository.saveAndFlush(path);

        // Get the path
        restPathMockMvc.perform(get("/api/paths/{id}", path.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(path.getId().intValue()))
            .andExpect(jsonPath("$.namePath").value(DEFAULT_NAME_PATH.toString()))
            .andExpect(jsonPath("$.numOfDistance").value(DEFAULT_NUM_OF_DISTANCE))
            .andExpect(jsonPath("$.timeDuration").value(DEFAULT_TIME_DURATION.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPath() throws Exception {
        // Get the path
        restPathMockMvc.perform(get("/api/paths/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePath() throws Exception {
        // Initialize the database
        pathRepository.saveAndFlush(path);
        int databaseSizeBeforeUpdate = pathRepository.findAll().size();

        // Update the path
        Path updatedPath = new Path();
        updatedPath.setId(path.getId());
        updatedPath.setNamePath(UPDATED_NAME_PATH);
        updatedPath.setNumOfDistance(UPDATED_NUM_OF_DISTANCE);
        updatedPath.setTimeDuration(UPDATED_TIME_DURATION);
        PathDTO pathDTO = pathMapper.pathToPathDTO(updatedPath);

        restPathMockMvc.perform(put("/api/paths")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pathDTO)))
                .andExpect(status().isOk());

        // Validate the Path in the database
        List<Path> paths = pathRepository.findAll();
        assertThat(paths).hasSize(databaseSizeBeforeUpdate);
        Path testPath = paths.get(paths.size() - 1);
        assertThat(testPath.getNamePath()).isEqualTo(UPDATED_NAME_PATH);
        assertThat(testPath.getNumOfDistance()).isEqualTo(UPDATED_NUM_OF_DISTANCE);
        assertThat(testPath.getTimeDuration()).isEqualTo(UPDATED_TIME_DURATION);
    }

    @Test
    @Transactional
    public void deletePath() throws Exception {
        // Initialize the database
        pathRepository.saveAndFlush(path);
        int databaseSizeBeforeDelete = pathRepository.findAll().size();

        // Get the path
        restPathMockMvc.perform(delete("/api/paths/{id}", path.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Path> paths = pathRepository.findAll();
        assertThat(paths).hasSize(databaseSizeBeforeDelete - 1);
    }
}
