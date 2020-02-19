package com.aditum.rehabicor.web.rest;

import com.aditum.rehabicor.RehabicorApp;

import com.aditum.rehabicor.domain.MayorEvent;
import com.aditum.rehabicor.repository.MayorEventRepository;
import com.aditum.rehabicor.service.MayorEventService;
import com.aditum.rehabicor.service.dto.MayorEventDTO;
import com.aditum.rehabicor.service.mapper.MayorEventMapper;
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
 * Test class for the MayorEventResource REST controller.
 *
 * @see MayorEventResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RehabicorApp.class)
public class MayorEventResourceIntTest {

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_DELETED = false;
    private static final Boolean UPDATED_DELETED = true;

    @Autowired
    private MayorEventRepository mayorEventRepository;

    @Autowired
    private MayorEventMapper mayorEventMapper;

    @Autowired
    private MayorEventService mayorEventService;

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

    private MockMvc restMayorEventMockMvc;

    private MayorEvent mayorEvent;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MayorEventResource mayorEventResource = new MayorEventResource(mayorEventService);
        this.restMayorEventMockMvc = MockMvcBuilders.standaloneSetup(mayorEventResource)
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
    public static MayorEvent createEntity(EntityManager em) {
        MayorEvent mayorEvent = new MayorEvent()
            .description(DEFAULT_DESCRIPTION)
            .code(DEFAULT_CODE)
            .deleted(DEFAULT_DELETED);
        return mayorEvent;
    }

    @Before
    public void initTest() {
        mayorEvent = createEntity(em);
    }

    @Test
    @Transactional
    public void createMayorEvent() throws Exception {
        int databaseSizeBeforeCreate = mayorEventRepository.findAll().size();

        // Create the MayorEvent
        MayorEventDTO mayorEventDTO = mayorEventMapper.toDto(mayorEvent);
        restMayorEventMockMvc.perform(post("/api/mayor-events")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mayorEventDTO)))
            .andExpect(status().isCreated());

        // Validate the MayorEvent in the database
        List<MayorEvent> mayorEventList = mayorEventRepository.findAll();
        assertThat(mayorEventList).hasSize(databaseSizeBeforeCreate + 1);
        MayorEvent testMayorEvent = mayorEventList.get(mayorEventList.size() - 1);
        assertThat(testMayorEvent.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testMayorEvent.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testMayorEvent.isDeleted()).isEqualTo(DEFAULT_DELETED);
    }

    @Test
    @Transactional
    public void createMayorEventWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mayorEventRepository.findAll().size();

        // Create the MayorEvent with an existing ID
        mayorEvent.setId(1L);
        MayorEventDTO mayorEventDTO = mayorEventMapper.toDto(mayorEvent);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMayorEventMockMvc.perform(post("/api/mayor-events")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mayorEventDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MayorEvent in the database
        List<MayorEvent> mayorEventList = mayorEventRepository.findAll();
        assertThat(mayorEventList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = mayorEventRepository.findAll().size();
        // set the field null
        mayorEvent.setDescription(null);

        // Create the MayorEvent, which fails.
        MayorEventDTO mayorEventDTO = mayorEventMapper.toDto(mayorEvent);

        restMayorEventMockMvc.perform(post("/api/mayor-events")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mayorEventDTO)))
            .andExpect(status().isBadRequest());

        List<MayorEvent> mayorEventList = mayorEventRepository.findAll();
        assertThat(mayorEventList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMayorEvents() throws Exception {
        // Initialize the database
        mayorEventRepository.saveAndFlush(mayorEvent);

        // Get all the mayorEventList
        restMayorEventMockMvc.perform(get("/api/mayor-events?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mayorEvent.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].deleted").value(hasItem(DEFAULT_DELETED.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getMayorEvent() throws Exception {
        // Initialize the database
        mayorEventRepository.saveAndFlush(mayorEvent);

        // Get the mayorEvent
        restMayorEventMockMvc.perform(get("/api/mayor-events/{id}", mayorEvent.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(mayorEvent.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.deleted").value(DEFAULT_DELETED.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingMayorEvent() throws Exception {
        // Get the mayorEvent
        restMayorEventMockMvc.perform(get("/api/mayor-events/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMayorEvent() throws Exception {
        // Initialize the database
        mayorEventRepository.saveAndFlush(mayorEvent);

        int databaseSizeBeforeUpdate = mayorEventRepository.findAll().size();

        // Update the mayorEvent
        MayorEvent updatedMayorEvent = mayorEventRepository.findById(mayorEvent.getId()).get();
        // Disconnect from session so that the updates on updatedMayorEvent are not directly saved in db
        em.detach(updatedMayorEvent);
        updatedMayorEvent
            .description(UPDATED_DESCRIPTION)
            .code(UPDATED_CODE)
            .deleted(UPDATED_DELETED);
        MayorEventDTO mayorEventDTO = mayorEventMapper.toDto(updatedMayorEvent);

        restMayorEventMockMvc.perform(put("/api/mayor-events")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mayorEventDTO)))
            .andExpect(status().isOk());

        // Validate the MayorEvent in the database
        List<MayorEvent> mayorEventList = mayorEventRepository.findAll();
        assertThat(mayorEventList).hasSize(databaseSizeBeforeUpdate);
        MayorEvent testMayorEvent = mayorEventList.get(mayorEventList.size() - 1);
        assertThat(testMayorEvent.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testMayorEvent.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testMayorEvent.isDeleted()).isEqualTo(UPDATED_DELETED);
    }

    @Test
    @Transactional
    public void updateNonExistingMayorEvent() throws Exception {
        int databaseSizeBeforeUpdate = mayorEventRepository.findAll().size();

        // Create the MayorEvent
        MayorEventDTO mayorEventDTO = mayorEventMapper.toDto(mayorEvent);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMayorEventMockMvc.perform(put("/api/mayor-events")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mayorEventDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MayorEvent in the database
        List<MayorEvent> mayorEventList = mayorEventRepository.findAll();
        assertThat(mayorEventList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMayorEvent() throws Exception {
        // Initialize the database
        mayorEventRepository.saveAndFlush(mayorEvent);

        int databaseSizeBeforeDelete = mayorEventRepository.findAll().size();

        // Delete the mayorEvent
        restMayorEventMockMvc.perform(delete("/api/mayor-events/{id}", mayorEvent.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<MayorEvent> mayorEventList = mayorEventRepository.findAll();
        assertThat(mayorEventList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MayorEvent.class);
        MayorEvent mayorEvent1 = new MayorEvent();
        mayorEvent1.setId(1L);
        MayorEvent mayorEvent2 = new MayorEvent();
        mayorEvent2.setId(mayorEvent1.getId());
        assertThat(mayorEvent1).isEqualTo(mayorEvent2);
        mayorEvent2.setId(2L);
        assertThat(mayorEvent1).isNotEqualTo(mayorEvent2);
        mayorEvent1.setId(null);
        assertThat(mayorEvent1).isNotEqualTo(mayorEvent2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MayorEventDTO.class);
        MayorEventDTO mayorEventDTO1 = new MayorEventDTO();
        mayorEventDTO1.setId(1L);
        MayorEventDTO mayorEventDTO2 = new MayorEventDTO();
        assertThat(mayorEventDTO1).isNotEqualTo(mayorEventDTO2);
        mayorEventDTO2.setId(mayorEventDTO1.getId());
        assertThat(mayorEventDTO1).isEqualTo(mayorEventDTO2);
        mayorEventDTO2.setId(2L);
        assertThat(mayorEventDTO1).isNotEqualTo(mayorEventDTO2);
        mayorEventDTO1.setId(null);
        assertThat(mayorEventDTO1).isNotEqualTo(mayorEventDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(mayorEventMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(mayorEventMapper.fromId(null)).isNull();
    }
}
