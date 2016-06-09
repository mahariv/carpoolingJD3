package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.CarpoolingCsid2016App;
import com.mycompany.myapp.domain.CarBrand;
import com.mycompany.myapp.repository.CarBrandRepository;
import com.mycompany.myapp.service.CarBrandService;
import com.mycompany.myapp.web.rest.dto.CarBrandDTO;
import com.mycompany.myapp.web.rest.mapper.CarBrandMapper;

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
 * Test class for the CarBrandResource REST controller.
 *
 * @see CarBrandResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = CarpoolingCsid2016App.class)
@WebAppConfiguration
@IntegrationTest
public class CarBrandResourceIntTest {

    private static final String DEFAULT_NAME_BRAND = "AAAAA";
    private static final String UPDATED_NAME_BRAND = "BBBBB";

    @Inject
    private CarBrandRepository carBrandRepository;

    @Inject
    private CarBrandMapper carBrandMapper;

    @Inject
    private CarBrandService carBrandService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restCarBrandMockMvc;

    private CarBrand carBrand;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CarBrandResource carBrandResource = new CarBrandResource();
        ReflectionTestUtils.setField(carBrandResource, "carBrandService", carBrandService);
        ReflectionTestUtils.setField(carBrandResource, "carBrandMapper", carBrandMapper);
        this.restCarBrandMockMvc = MockMvcBuilders.standaloneSetup(carBrandResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        carBrand = new CarBrand();
        carBrand.setNameBrand(DEFAULT_NAME_BRAND);
    }

    @Test
    @Transactional
    public void createCarBrand() throws Exception {
        int databaseSizeBeforeCreate = carBrandRepository.findAll().size();

        // Create the CarBrand
        CarBrandDTO carBrandDTO = carBrandMapper.carBrandToCarBrandDTO(carBrand);

        restCarBrandMockMvc.perform(post("/api/car-brands")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(carBrandDTO)))
                .andExpect(status().isCreated());

        // Validate the CarBrand in the database
        List<CarBrand> carBrands = carBrandRepository.findAll();
        assertThat(carBrands).hasSize(databaseSizeBeforeCreate + 1);
        CarBrand testCarBrand = carBrands.get(carBrands.size() - 1);
        assertThat(testCarBrand.getNameBrand()).isEqualTo(DEFAULT_NAME_BRAND);
    }

    @Test
    @Transactional
    public void getAllCarBrands() throws Exception {
        // Initialize the database
        carBrandRepository.saveAndFlush(carBrand);

        // Get all the carBrands
        restCarBrandMockMvc.perform(get("/api/car-brands?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(carBrand.getId().intValue())))
                .andExpect(jsonPath("$.[*].nameBrand").value(hasItem(DEFAULT_NAME_BRAND.toString())));
    }

    @Test
    @Transactional
    public void getCarBrand() throws Exception {
        // Initialize the database
        carBrandRepository.saveAndFlush(carBrand);

        // Get the carBrand
        restCarBrandMockMvc.perform(get("/api/car-brands/{id}", carBrand.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(carBrand.getId().intValue()))
            .andExpect(jsonPath("$.nameBrand").value(DEFAULT_NAME_BRAND.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCarBrand() throws Exception {
        // Get the carBrand
        restCarBrandMockMvc.perform(get("/api/car-brands/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCarBrand() throws Exception {
        // Initialize the database
        carBrandRepository.saveAndFlush(carBrand);
        int databaseSizeBeforeUpdate = carBrandRepository.findAll().size();

        // Update the carBrand
        CarBrand updatedCarBrand = new CarBrand();
        updatedCarBrand.setId(carBrand.getId());
        updatedCarBrand.setNameBrand(UPDATED_NAME_BRAND);
        CarBrandDTO carBrandDTO = carBrandMapper.carBrandToCarBrandDTO(updatedCarBrand);

        restCarBrandMockMvc.perform(put("/api/car-brands")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(carBrandDTO)))
                .andExpect(status().isOk());

        // Validate the CarBrand in the database
        List<CarBrand> carBrands = carBrandRepository.findAll();
        assertThat(carBrands).hasSize(databaseSizeBeforeUpdate);
        CarBrand testCarBrand = carBrands.get(carBrands.size() - 1);
        assertThat(testCarBrand.getNameBrand()).isEqualTo(UPDATED_NAME_BRAND);
    }

    @Test
    @Transactional
    public void deleteCarBrand() throws Exception {
        // Initialize the database
        carBrandRepository.saveAndFlush(carBrand);
        int databaseSizeBeforeDelete = carBrandRepository.findAll().size();

        // Get the carBrand
        restCarBrandMockMvc.perform(delete("/api/car-brands/{id}", carBrand.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<CarBrand> carBrands = carBrandRepository.findAll();
        assertThat(carBrands).hasSize(databaseSizeBeforeDelete - 1);
    }
}
