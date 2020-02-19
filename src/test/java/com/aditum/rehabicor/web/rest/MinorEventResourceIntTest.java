package com.aditum.rehabicor.web.rest;

import com.aditum.rehabicor.RehabicorApp;

import com.aditum.rehabicor.domain.MinorEvent;
import com.aditum.rehabicor.repository.MinorEventRepository;
import com.aditum.rehabicor.service.MinorEventService;
import com.aditum.rehabicor.service.dto.MinorEventDTO;
import com.aditum.rehabicor.service.mapper.MinorEventMapper;
import com.aditum.rehabicor.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;


import static com.aditum.rehabicor.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the MinorEventResource REST controller.
 *
 * @see MinorEventResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RehabicorApp.class)
public class MinorEventResourceIntTest {

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_DELETED = false;
    private static final Boolean UPDATED_DELETED = true;

    @Autowired
    private MinorEventRepository minorEventRepository;

    @Autowired
    private MinorEventMapper minorEventMapper;

    @Autowired
    private MinorEventService minorEventService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restMinorEventMockMvc;

    private MinorEvent minorEvent;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MinorEventResource minorEventResource = new MinorEventResource(minorEventService);
        this.restMinorEventMockMvc = MockMvcBuilders.standaloneSetup(minorEventResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MinorEvent createEntity(EntityManager em) {
        MinorEvent minorEvent = new MinorEvent()
            .description(DEFAULT_DESCRIPTION)
            .code(DEFAULT_CODE)
            .deleted(DEFAULT_DELETED);
        return minorEvent;
    }

    @Before
    public void initTest() {
        minorEvent = createEntity(em);
    }

    @Test
    @Transactional
    public void createMinorEvent() throws Exception {
        int databaseSizeBeforeCreate = minorEventRepository.findAll().size();

        // Create the MinorEvent
        MinorEventDTO minorEventDTO = minorEventMapper.toDto(minorEvent);
        restMinorEventMockMvc.perform(post("/api/minor-events")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(minorEventDTO)))
            .andExpect(status().isCreated());

        // Validate the MinorEvent in the database
        List<MinorEvent> minorEventList = minorEventRepository.findAll();
        assertThat(minorEventList).hasSize(databaseSizeBeforeCreate + 1);
        MinorEvent testMinorEvent = minorEventList.get(minorEventList.size() - 1);
        assertThat(testMinorEvent.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testMinorEvent.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testMinorEvent.isDeleted()).isEqualTo(DEFAULT_DELETED);
    }

    @Test
    @Transactional
    public void createMinorEventWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = minorEventRepository.findAll().size();

        // Create the MinorEvent with an existing ID
        minorEvent.setId(1L);
        MinorEventDTO minorEventDTO = minorEventMapper.toDto(minorEvent);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMinorEventMockMvc.perform(post("/api/minor-events")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(minorEventDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MinorEvent in the database
        List<MinorEvent> minorEventList = minorEventRepository.findAll();
        assertThat(minorEventList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = minorEventRepository.findAll().size();
        // set the field null
        minorEvent.setDescription(null);

        // Create the MinorEvent, which fails.
        MinorEventDTO minorEventDTO = minorEventMapper.toDto(minorEvent);

        restMinorEventMockMvc.perform(post("/api/minor-events")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(minorEventDTO)))
            .andExpect(status().isBadRequest());

        List<MinorEvent> minorEventList = minorEventRepository.findAll();
        assertThat(minorEventList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMinorEvents() throws Exception {
        // Initialize the database
        minorEventRepository.saveAndFlush(minorEvent);

        // Get all the minorEventList
        restMinorEventMockMvc.perform(get("/api/minor-events?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(minorEvent.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].deleted").value(hasItem(DEFAULT_DELETED.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getMinorEvent() throws Exception {
        // Initialize the database
        minorEventRepository.saveAndFlush(minorEvent);

        // Get the minorEvent
        restMinorEventMockMvc.perform(get("/api/minor-events/{id}", minorEvent.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(minorEvent.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.deleted").value(DEFAULT_DELETED.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingMinorEvent() throws Exception {
        // Get the minorEvent
        restMinorEventMockMvc.perform(get("/api/minor-events/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMinorEvent() throws Exception {
        // Initialize the database
        minorEventRepository.saveAndFlush(minorEvent);

        int databaseSizeBeforeUpdate = minorEventRepository.findAll().size();

        // Update the minorEvent
        MinorEvent updatedMinorEvent = minorEventRepository.findById(minorEvent.getId()).get();
        // Disconnect from session so that the updates on updatedMinorEvent are not directly saved in db
        em.detach(updatedMinorEvent);
        updatedMinorEvent
            .description(UPDATED_DESCRIPTION)
            .code(UPDATED_CODE)
            .deleted(UPDATED_DELETED);
        MinorEventDTO minorEventDTO = minorEventMapper.toDto(updatedMinorEvent);

        restMinorEventMockMvc.perform(put("/api/minor-events")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(minorEventDTO)))
            .andExpect(status().isOk());

        // Validate the MinorEvent in the database
        List<MinorEvent> minorEventList = minorEventRepository.findAll();
        assertThat(minorEventList).hasSize(databaseSizeBeforeUpdate);
        MinorEvent testMinorEvent = minorEventList.get(minorEventList.size() - 1);
        assertThat(testMinorEvent.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testMinorEvent.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testMinorEvent.isDeleted()).isEqualTo(UPDATED_DELETED);
    }

    @Test
    @Transactional
    public void updateNonExistingMinorEvent() throws Exception {
        int databaseSizeBeforeUpdate = minorEventRepository.findAll().size();

        // Create the MinorEvent
        MinorEventDTO minorEventDTO = minorEventMapper.toDto(minorEvent);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMinorEventMockMvc.perform(put("/api/minor-events")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(minorEventDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MinorEvent in the database
        List<MinorEvent> minorEventList = minorEventRepository.findAll();
        assertThat(minorEventList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMinorEvent() throws Exception {
        // Initialize the database
        minorEventRepository.saveAndFlush(minorEvent);

        int databaseSizeBeforeDelete = minorEventRepository.findAll().size();

        // Delete the minorEvent
        restMinorEventMockMvc.perform(delete("/api/minor-events/{id}", minorEvent.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<MinorEvent> minorEventList = minorEventRepository.findAll();
        assertThat(minorEventList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MinorEvent.class);
        MinorEvent minorEvent1 = new MinorEvent();
        minorEvent1.setId(1L);
        MinorEvent minorEvent2 = new MinorEvent();
        minorEvent2.setId(minorEvent1.getId());
        assertThat(minorEvent1).isEqualTo(minorEvent2);
        minorEvent2.setId(2L);
        assertThat(minorEvent1).isNotEqualTo(minorEvent2);
        minorEvent1.setId(null);
        assertThat(minorEvent1).isNotEqualTo(minorEvent2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MinorEventDTO.class);
        MinorEventDTO minorEventDTO1 = new MinorEventDTO();
        minorEventDTO1.setId(1L);
        MinorEventDTO minorEventDTO2 = new MinorEventDTO();
        assertThat(minorEventDTO1).isNotEqualTo(minorEventDTO2);
        minorEventDTO2.setId(minorEventDTO1.getId());
        assertThat(minorEventDTO1).isEqualTo(minorEventDTO2);
        minorEventDTO2.setId(2L);
        assertThat(minorEventDTO1).isNotEqualTo(minorEventDTO2);
        minorEventDTO1.setId(null);
        assertThat(minorEventDTO1).isNotEqualTo(minorEventDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(minorEventMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(minorEventMapper.fromId(null)).isNull();
    }
}
