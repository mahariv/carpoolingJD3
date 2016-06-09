package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.CarpoolingCsid2016App;
import com.mycompany.myapp.domain.Passenger;
import com.mycompany.myapp.repository.PassengerRepository;
import com.mycompany.myapp.service.PassengerService;
import com.mycompany.myapp.web.rest.dto.PassengerDTO;
import com.mycompany.myapp.web.rest.mapper.PassengerMapper;

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
 * Test class for the PassengerResource REST controller.
 *
 * @see PassengerResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = CarpoolingCsid2016App.class)
@WebAppConfiguration
@IntegrationTest
public class PassengerResourceIntTest {

    private static final String DEFAULT_FIRST_NAME = "AAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBB";
    private static final String DEFAULT_LAST_NAME = "AAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBB";

    private static final Integer DEFAULT_AGE = 1;
    private static final Integer UPDATED_AGE = 2;
    private static final String DEFAULT_PHONE = "AAAAA";
    private static final String UPDATED_PHONE = "BBBBB";
    private static final String DEFAULT_MAIL = "AAAAA";
    private static final String UPDATED_MAIL = "BBBBB";

    @Inject
    private PassengerRepository passengerRepository;

    @Inject
    private PassengerMapper passengerMapper;

    @Inject
    private PassengerService passengerService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restPassengerMockMvc;

    private Passenger passenger;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PassengerResource passengerResource = new PassengerResource();
        ReflectionTestUtils.setField(passengerResource, "passengerService", passengerService);
        ReflectionTestUtils.setField(passengerResource, "passengerMapper", passengerMapper);
        this.restPassengerMockMvc = MockMvcBuilders.standaloneSetup(passengerResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        passenger = new Passenger();
        passenger.setFirstName(DEFAULT_FIRST_NAME);
        passenger.setLastName(DEFAULT_LAST_NAME);
        passenger.setAge(DEFAULT_AGE);
        passenger.setPhone(DEFAULT_PHONE);
        passenger.setMail(DEFAULT_MAIL);
    }

    @Test
    @Transactional
    public void createPassenger() throws Exception {
        int databaseSizeBeforeCreate = passengerRepository.findAll().size();

        // Create the Passenger
        PassengerDTO passengerDTO = passengerMapper.passengerToPassengerDTO(passenger);

        restPassengerMockMvc.perform(post("/api/passengers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(passengerDTO)))
                .andExpect(status().isCreated());

        // Validate the Passenger in the database
        List<Passenger> passengers = passengerRepository.findAll();
        assertThat(passengers).hasSize(databaseSizeBeforeCreate + 1);
        Passenger testPassenger = passengers.get(passengers.size() - 1);
        assertThat(testPassenger.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testPassenger.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testPassenger.getAge()).isEqualTo(DEFAULT_AGE);
        assertThat(testPassenger.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testPassenger.getMail()).isEqualTo(DEFAULT_MAIL);
    }

    @Test
    @Transactional
    public void getAllPassengers() throws Exception {
        // Initialize the database
        passengerRepository.saveAndFlush(passenger);

        // Get all the passengers
        restPassengerMockMvc.perform(get("/api/passengers?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(passenger.getId().intValue())))
                .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME.toString())))
                .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME.toString())))
                .andExpect(jsonPath("$.[*].age").value(hasItem(DEFAULT_AGE)))
                .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE.toString())))
                .andExpect(jsonPath("$.[*].mail").value(hasItem(DEFAULT_MAIL.toString())));
    }

    @Test
    @Transactional
    public void getPassenger() throws Exception {
        // Initialize the database
        passengerRepository.saveAndFlush(passenger);

        // Get the passenger
        restPassengerMockMvc.perform(get("/api/passengers/{id}", passenger.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(passenger.getId().intValue()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME.toString()))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME.toString()))
            .andExpect(jsonPath("$.age").value(DEFAULT_AGE))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE.toString()))
            .andExpect(jsonPath("$.mail").value(DEFAULT_MAIL.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPassenger() throws Exception {
        // Get the passenger
        restPassengerMockMvc.perform(get("/api/passengers/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePassenger() throws Exception {
        // Initialize the database
        passengerRepository.saveAndFlush(passenger);
        int databaseSizeBeforeUpdate = passengerRepository.findAll().size();

        // Update the passenger
        Passenger updatedPassenger = new Passenger();
        updatedPassenger.setId(passenger.getId());
        updatedPassenger.setFirstName(UPDATED_FIRST_NAME);
        updatedPassenger.setLastName(UPDATED_LAST_NAME);
        updatedPassenger.setAge(UPDATED_AGE);
        updatedPassenger.setPhone(UPDATED_PHONE);
        updatedPassenger.setMail(UPDATED_MAIL);
        PassengerDTO passengerDTO = passengerMapper.passengerToPassengerDTO(updatedPassenger);

        restPassengerMockMvc.perform(put("/api/passengers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(passengerDTO)))
                .andExpect(status().isOk());

        // Validate the Passenger in the database
        List<Passenger> passengers = passengerRepository.findAll();
        assertThat(passengers).hasSize(databaseSizeBeforeUpdate);
        Passenger testPassenger = passengers.get(passengers.size() - 1);
        assertThat(testPassenger.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testPassenger.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testPassenger.getAge()).isEqualTo(UPDATED_AGE);
        assertThat(testPassenger.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testPassenger.getMail()).isEqualTo(UPDATED_MAIL);
    }

    @Test
    @Transactional
    public void deletePassenger() throws Exception {
        // Initialize the database
        passengerRepository.saveAndFlush(passenger);
        int databaseSizeBeforeDelete = passengerRepository.findAll().size();

        // Get the passenger
        restPassengerMockMvc.perform(delete("/api/passengers/{id}", passenger.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Passenger> passengers = passengerRepository.findAll();
        assertThat(passengers).hasSize(databaseSizeBeforeDelete - 1);
    }
}
