package com.aditum.rehabicor.web.rest;

import com.aditum.rehabicor.RehabicorApp;

import com.aditum.rehabicor.domain.MayorEventsSession;
import com.aditum.rehabicor.repository.MayorEventsSessionRepository;
import com.aditum.rehabicor.service.MayorEventsSessionService;
import com.aditum.rehabicor.service.dto.MayorEventsSessionDTO;
import com.aditum.rehabicor.service.mapper.MayorEventsSessionMapper;
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
 * Test class for the MayorEventsSessionResource REST controller.
 *
 * @see MayorEventsSessionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RehabicorApp.class)
public class MayorEventsSessionResourceIntTest {

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_EXIST = false;
    private static final Boolean UPDATED_EXIST = true;

    private static final Long DEFAULT_MAYOR_EVENT_RELATION = 1L;
    private static final Long UPDATED_MAYOR_EVENT_RELATION = 2L;

    @Autowired
    private MayorEventsSessionRepository mayorEventsSessionRepository;

    @Autowired
    private MayorEventsSessionMapper mayorEventsSessionMapper;

    @Autowired
    private MayorEventsSessionService mayorEventsSessionService;

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

    private MockMvc restMayorEventsSessionMockMvc;

    private MayorEventsSession mayorEventsSession;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MayorEventsSessionResource mayorEventsSessionResource = new MayorEventsSessionResource(mayorEventsSessionService);
        this.restMayorEventsSessionMockMvc = MockMvcBuilders.standaloneSetup(mayorEventsSessionResource)
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
    public static MayorEventsSession createEntity(EntityManager em) {
        MayorEventsSession mayorEventsSession = new MayorEventsSession()
            .description(DEFAULT_DESCRIPTION)
            .exist(DEFAULT_EXIST)
            .mayorEventRelation(DEFAULT_MAYOR_EVENT_RELATION);
        return mayorEventsSession;
    }

    @Before
    public void initTest() {
        mayorEventsSession = createEntity(em);
    }

    @Test
    @Transactional
    public void createMayorEventsSession() throws Exception {
        int databaseSizeBeforeCreate = mayorEventsSessionRepository.findAll().size();

        // Create the MayorEventsSession
        MayorEventsSessionDTO mayorEventsSessionDTO = mayorEventsSessionMapper.toDto(mayorEventsSession);
        restMayorEventsSessionMockMvc.perform(post("/api/mayor-events-sessions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mayorEventsSessionDTO)))
            .andExpect(status().isCreated());

        // Validate the MayorEventsSession in the database
        List<MayorEventsSession> mayorEventsSessionList = mayorEventsSessionRepository.findAll();
        assertThat(mayorEventsSessionList).hasSize(databaseSizeBeforeCreate + 1);
        MayorEventsSession testMayorEventsSession = mayorEventsSessionList.get(mayorEventsSessionList.size() - 1);
        assertThat(testMayorEventsSession.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testMayorEventsSession.isExist()).isEqualTo(DEFAULT_EXIST);
        assertThat(testMayorEventsSession.getMayorEventRelation()).isEqualTo(DEFAULT_MAYOR_EVENT_RELATION);
    }

    @Test
    @Transactional
    public void createMayorEventsSessionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mayorEventsSessionRepository.findAll().size();

        // Create the MayorEventsSession with an existing ID
        mayorEventsSession.setId(1L);
        MayorEventsSessionDTO mayorEventsSessionDTO = mayorEventsSessionMapper.toDto(mayorEventsSession);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMayorEventsSessionMockMvc.perform(post("/api/mayor-events-sessions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mayorEventsSessionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MayorEventsSession in the database
        List<MayorEventsSession> mayorEventsSessionList = mayorEventsSessionRepository.findAll();
        assertThat(mayorEventsSessionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkExistIsRequired() throws Exception {
        int databaseSizeBeforeTest = mayorEventsSessionRepository.findAll().size();
        // set the field null
        mayorEventsSession.setExist(null);

        // Create the MayorEventsSession, which fails.
        MayorEventsSessionDTO mayorEventsSessionDTO = mayorEventsSessionMapper.toDto(mayorEventsSession);

        restMayorEventsSessionMockMvc.perform(post("/api/mayor-events-sessions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mayorEventsSessionDTO)))
            .andExpect(status().isBadRequest());

        List<MayorEventsSession> mayorEventsSessionList = mayorEventsSessionRepository.findAll();
        assertThat(mayorEventsSessionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMayorEventsSessions() throws Exception {
        // Initialize the database
        mayorEventsSessionRepository.saveAndFlush(mayorEventsSession);

        // Get all the mayorEventsSessionList
        restMayorEventsSessionMockMvc.perform(get("/api/mayor-events-sessions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mayorEventsSession.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].exist").value(hasItem(DEFAULT_EXIST.booleanValue())))
            .andExpect(jsonPath("$.[*].mayorEventRelation").value(hasItem(DEFAULT_MAYOR_EVENT_RELATION.intValue())));
    }
    
    @Test
    @Transactional
    public void getMayorEventsSession() throws Exception {
        // Initialize the database
        mayorEventsSessionRepository.saveAndFlush(mayorEventsSession);

        // Get the mayorEventsSession
        restMayorEventsSessionMockMvc.perform(get("/api/mayor-events-sessions/{id}", mayorEventsSession.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(mayorEventsSession.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.exist").value(DEFAULT_EXIST.booleanValue()))
            .andExpect(jsonPath("$.mayorEventRelation").value(DEFAULT_MAYOR_EVENT_RELATION.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingMayorEventsSession() throws Exception {
        // Get the mayorEventsSession
        restMayorEventsSessionMockMvc.perform(get("/api/mayor-events-sessions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMayorEventsSession() throws Exception {
        // Initialize the database
        mayorEventsSessionRepository.saveAndFlush(mayorEventsSession);

        int databaseSizeBeforeUpdate = mayorEventsSessionRepository.findAll().size();

        // Update the mayorEventsSession
        MayorEventsSession updatedMayorEventsSession = mayorEventsSessionRepository.findById(mayorEventsSession.getId()).get();
        // Disconnect from session so that the updates on updatedMayorEventsSession are not directly saved in db
        em.detach(updatedMayorEventsSession);
        updatedMayorEventsSession
            .description(UPDATED_DESCRIPTION)
            .exist(UPDATED_EXIST)
            .mayorEventRelation(UPDATED_MAYOR_EVENT_RELATION);
        MayorEventsSessionDTO mayorEventsSessionDTO = mayorEventsSessionMapper.toDto(updatedMayorEventsSession);

        restMayorEventsSessionMockMvc.perform(put("/api/mayor-events-sessions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mayorEventsSessionDTO)))
            .andExpect(status().isOk());

        // Validate the MayorEventsSession in the database
        List<MayorEventsSession> mayorEventsSessionList = mayorEventsSessionRepository.findAll();
        assertThat(mayorEventsSessionList).hasSize(databaseSizeBeforeUpdate);
        MayorEventsSession testMayorEventsSession = mayorEventsSessionList.get(mayorEventsSessionList.size() - 1);
        assertThat(testMayorEventsSession.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testMayorEventsSession.isExist()).isEqualTo(UPDATED_EXIST);
        assertThat(testMayorEventsSession.getMayorEventRelation()).isEqualTo(UPDATED_MAYOR_EVENT_RELATION);
    }

    @Test
    @Transactional
    public void updateNonExistingMayorEventsSession() throws Exception {
        int databaseSizeBeforeUpdate = mayorEventsSessionRepository.findAll().size();

        // Create the MayorEventsSession
        MayorEventsSessionDTO mayorEventsSessionDTO = mayorEventsSessionMapper.toDto(mayorEventsSession);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMayorEventsSessionMockMvc.perform(put("/api/mayor-events-sessions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mayorEventsSessionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MayorEventsSession in the database
        List<MayorEventsSession> mayorEventsSessionList = mayorEventsSessionRepository.findAll();
        assertThat(mayorEventsSessionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMayorEventsSession() throws Exception {
        // Initialize the database
        mayorEventsSessionRepository.saveAndFlush(mayorEventsSession);

        int databaseSizeBeforeDelete = mayorEventsSessionRepository.findAll().size();

        // Delete the mayorEventsSession
        restMayorEventsSessionMockMvc.perform(delete("/api/mayor-events-sessions/{id}", mayorEventsSession.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<MayorEventsSession> mayorEventsSessionList = mayorEventsSessionRepository.findAll();
        assertThat(mayorEventsSessionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MayorEventsSession.class);
        MayorEventsSession mayorEventsSession1 = new MayorEventsSession();
        mayorEventsSession1.setId(1L);
        MayorEventsSession mayorEventsSession2 = new MayorEventsSession();
        mayorEventsSession2.setId(mayorEventsSession1.getId());
        assertThat(mayorEventsSession1).isEqualTo(mayorEventsSession2);
        mayorEventsSession2.setId(2L);
        assertThat(mayorEventsSession1).isNotEqualTo(mayorEventsSession2);
        mayorEventsSession1.setId(null);
        assertThat(mayorEventsSession1).isNotEqualTo(mayorEventsSession2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MayorEventsSessionDTO.class);
        MayorEventsSessionDTO mayorEventsSessionDTO1 = new MayorEventsSessionDTO();
        mayorEventsSessionDTO1.setId(1L);
        MayorEventsSessionDTO mayorEventsSessionDTO2 = new MayorEventsSessionDTO();
        assertThat(mayorEventsSessionDTO1).isNotEqualTo(mayorEventsSessionDTO2);
        mayorEventsSessionDTO2.setId(mayorEventsSessionDTO1.getId());
        assertThat(mayorEventsSessionDTO1).isEqualTo(mayorEventsSessionDTO2);
        mayorEventsSessionDTO2.setId(2L);
        assertThat(mayorEventsSessionDTO1).isNotEqualTo(mayorEventsSessionDTO2);
        mayorEventsSessionDTO1.setId(null);
        assertThat(mayorEventsSessionDTO1).isNotEqualTo(mayorEventsSessionDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(mayorEventsSessionMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(mayorEventsSessionMapper.fromId(null)).isNull();
    }
}
