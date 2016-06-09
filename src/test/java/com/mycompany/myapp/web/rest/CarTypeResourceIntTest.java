package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.CarpoolingCsid2016App;
import com.mycompany.myapp.domain.CarType;
import com.mycompany.myapp.repository.CarTypeRepository;
import com.mycompany.myapp.service.CarTypeService;
import com.mycompany.myapp.web.rest.dto.CarTypeDTO;
import com.mycompany.myapp.web.rest.mapper.CarTypeMapper;

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
 * Test class for the CarTypeResource REST controller.
 *
 * @see CarTypeResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = CarpoolingCsid2016App.class)
@WebAppConfiguration
@IntegrationTest
public class CarTypeResourceIntTest {

    private static final String DEFAULT_NAME_TYPE = "AAAAA";
    private static final String UPDATED_NAME_TYPE = "BBBBB";

    @Inject
    private CarTypeRepository carTypeRepository;

    @Inject
    private CarTypeMapper carTypeMapper;

    @Inject
    private CarTypeService carTypeService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restCarTypeMockMvc;

    private CarType carType;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CarTypeResource carTypeResource = new CarTypeResource();
        ReflectionTestUtils.setField(carTypeResource, "carTypeService", carTypeService);
        ReflectionTestUtils.setField(carTypeResource, "carTypeMapper", carTypeMapper);
        this.restCarTypeMockMvc = MockMvcBuilders.standaloneSetup(carTypeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        carType = new CarType();
        carType.setNameType(DEFAULT_NAME_TYPE);
    }

    @Test
    @Transactional
    public void createCarType() throws Exception {
        int databaseSizeBeforeCreate = carTypeRepository.findAll().size();

        // Create the CarType
        CarTypeDTO carTypeDTO = carTypeMapper.carTypeToCarTypeDTO(carType);

        restCarTypeMockMvc.perform(post("/api/car-types")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(carTypeDTO)))
                .andExpect(status().isCreated());

        // Validate the CarType in the database
        List<CarType> carTypes = carTypeRepository.findAll();
        assertThat(carTypes).hasSize(databaseSizeBeforeCreate + 1);
        CarType testCarType = carTypes.get(carTypes.size() - 1);
        assertThat(testCarType.getNameType()).isEqualTo(DEFAULT_NAME_TYPE);
    }

    @Test
    @Transactional
    public void getAllCarTypes() throws Exception {
        // Initialize the database
        carTypeRepository.saveAndFlush(carType);

        // Get all the carTypes
        restCarTypeMockMvc.perform(get("/api/car-types?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(carType.getId().intValue())))
                .andExpect(jsonPath("$.[*].nameType").value(hasItem(DEFAULT_NAME_TYPE.toString())));
    }

    @Test
    @Transactional
    public void getCarType() throws Exception {
        // Initialize the database
        carTypeRepository.saveAndFlush(carType);

        // Get the carType
        restCarTypeMockMvc.perform(get("/api/car-types/{id}", carType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(carType.getId().intValue()))
            .andExpect(jsonPath("$.nameType").value(DEFAULT_NAME_TYPE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCarType() throws Exception {
        // Get the carType
        restCarTypeMockMvc.perform(get("/api/car-types/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCarType() throws Exception {
        // Initialize the database
        carTypeRepository.saveAndFlush(carType);
        int databaseSizeBeforeUpdate = carTypeRepository.findAll().size();

        // Update the carType
        CarType updatedCarType = new CarType();
        updatedCarType.setId(carType.getId());
        updatedCarType.setNameType(UPDATED_NAME_TYPE);
        CarTypeDTO carTypeDTO = carTypeMapper.carTypeToCarTypeDTO(updatedCarType);

        restCarTypeMockMvc.perform(put("/api/car-types")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(carTypeDTO)))
                .andExpect(status().isOk());

        // Validate the CarType in the database
        List<CarType> carTypes = carTypeRepository.findAll();
        assertThat(carTypes).hasSize(databaseSizeBeforeUpdate);
        CarType testCarType = carTypes.get(carTypes.size() - 1);
        assertThat(testCarType.getNameType()).isEqualTo(UPDATED_NAME_TYPE);
    }

    @Test
    @Transactional
    public void deleteCarType() throws Exception {
        // Initialize the database
        carTypeRepository.saveAndFlush(carType);
        int databaseSizeBeforeDelete = carTypeRepository.findAll().size();

        // Get the carType
        restCarTypeMockMvc.perform(delete("/api/car-types/{id}", carType.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<CarType> carTypes = carTypeRepository.findAll();
        assertThat(carTypes).hasSize(databaseSizeBeforeDelete - 1);
    }
}
