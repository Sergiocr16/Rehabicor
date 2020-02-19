package com.aditum.rehabicor.web.rest;

import com.aditum.rehabicor.RehabicorApp;

import com.aditum.rehabicor.domain.Session;
import com.aditum.rehabicor.repository.SessionRepository;
import com.aditum.rehabicor.service.SessionService;
import com.aditum.rehabicor.service.dto.SessionDTO;
import com.aditum.rehabicor.service.mapper.SessionMapper;
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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;


import static com.aditum.rehabicor.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the SessionResource REST controller.
 *
 * @see SessionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RehabicorApp.class)
public class SessionResourceIntTest {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final Instant DEFAULT_EXECUTION_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_EXECUTION_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Boolean DEFAULT_ABSCENCE = false;
    private static final Boolean UPDATED_ABSCENCE = true;

    private static final Boolean DEFAULT_HOSPITALIZATION = false;
    private static final Boolean UPDATED_HOSPITALIZATION = true;

    private static final Integer DEFAULT_STATUS = 1;
    private static final Integer UPDATED_STATUS = 2;

    private static final Boolean DEFAULT_DELETED = false;
    private static final Boolean UPDATED_DELETED = true;

    private static final Boolean DEFAULT_CURRENTLY_WORKING = false;
    private static final Boolean UPDATED_CURRENTLY_WORKING = true;

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private SessionMapper sessionMapper;

    @Autowired
    private SessionService sessionService;

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

    private MockMvc restSessionMockMvc;

    private Session session;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SessionResource sessionResource = new SessionResource(sessionService);
        this.restSessionMockMvc = MockMvcBuilders.standaloneSetup(sessionResource)
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
    public static Session createEntity(EntityManager em) {
        Session session = new Session()
            .code(DEFAULT_CODE)
            .executionDate(DEFAULT_EXECUTION_DATE)
            .abscence(DEFAULT_ABSCENCE)
            .hospitalization(DEFAULT_HOSPITALIZATION)
            .status(DEFAULT_STATUS)
            .deleted(DEFAULT_DELETED)
            .currentlyWorking(DEFAULT_CURRENTLY_WORKING);
        return session;
    }

    @Before
    public void initTest() {
        session = createEntity(em);
    }

    @Test
    @Transactional
    public void createSession() throws Exception {
        int databaseSizeBeforeCreate = sessionRepository.findAll().size();

        // Create the Session
        SessionDTO sessionDTO = sessionMapper.toDto(session);
        restSessionMockMvc.perform(post("/api/sessions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sessionDTO)))
            .andExpect(status().isCreated());

        // Validate the Session in the database
        List<Session> sessionList = sessionRepository.findAll();
        assertThat(sessionList).hasSize(databaseSizeBeforeCreate + 1);
        Session testSession = sessionList.get(sessionList.size() - 1);
        assertThat(testSession.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testSession.getExecutionDate()).isEqualTo(DEFAULT_EXECUTION_DATE);
        assertThat(testSession.isAbscence()).isEqualTo(DEFAULT_ABSCENCE);
        assertThat(testSession.isHospitalization()).isEqualTo(DEFAULT_HOSPITALIZATION);
        assertThat(testSession.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testSession.isDeleted()).isEqualTo(DEFAULT_DELETED);
        assertThat(testSession.isCurrentlyWorking()).isEqualTo(DEFAULT_CURRENTLY_WORKING);
    }

    @Test
    @Transactional
    public void createSessionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = sessionRepository.findAll().size();

        // Create the Session with an existing ID
        session.setId(1L);
        SessionDTO sessionDTO = sessionMapper.toDto(session);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSessionMockMvc.perform(post("/api/sessions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sessionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Session in the database
        List<Session> sessionList = sessionRepository.findAll();
        assertThat(sessionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = sessionRepository.findAll().size();
        // set the field null
        session.setCode(null);

        // Create the Session, which fails.
        SessionDTO sessionDTO = sessionMapper.toDto(session);

        restSessionMockMvc.perform(post("/api/sessions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sessionDTO)))
            .andExpect(status().isBadRequest());

        List<Session> sessionList = sessionRepository.findAll();
        assertThat(sessionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkExecutionDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = sessionRepository.findAll().size();
        // set the field null
        session.setExecutionDate(null);

        // Create the Session, which fails.
        SessionDTO sessionDTO = sessionMapper.toDto(session);

        restSessionMockMvc.perform(post("/api/sessions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sessionDTO)))
            .andExpect(status().isBadRequest());

        List<Session> sessionList = sessionRepository.findAll();
        assertThat(sessionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSessions() throws Exception {
        // Initialize the database
        sessionRepository.saveAndFlush(session);

        // Get all the sessionList
        restSessionMockMvc.perform(get("/api/sessions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(session.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].executionDate").value(hasItem(DEFAULT_EXECUTION_DATE.toString())))
            .andExpect(jsonPath("$.[*].abscence").value(hasItem(DEFAULT_ABSCENCE.booleanValue())))
            .andExpect(jsonPath("$.[*].hospitalization").value(hasItem(DEFAULT_HOSPITALIZATION.booleanValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].deleted").value(hasItem(DEFAULT_DELETED.booleanValue())))
            .andExpect(jsonPath("$.[*].currentlyWorking").value(hasItem(DEFAULT_CURRENTLY_WORKING.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getSession() throws Exception {
        // Initialize the database
        sessionRepository.saveAndFlush(session);

        // Get the session
        restSessionMockMvc.perform(get("/api/sessions/{id}", session.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(session.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.executionDate").value(DEFAULT_EXECUTION_DATE.toString()))
            .andExpect(jsonPath("$.abscence").value(DEFAULT_ABSCENCE.booleanValue()))
            .andExpect(jsonPath("$.hospitalization").value(DEFAULT_HOSPITALIZATION.booleanValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.deleted").value(DEFAULT_DELETED.booleanValue()))
            .andExpect(jsonPath("$.currentlyWorking").value(DEFAULT_CURRENTLY_WORKING.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingSession() throws Exception {
        // Get the session
        restSessionMockMvc.perform(get("/api/sessions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSession() throws Exception {
        // Initialize the database
        sessionRepository.saveAndFlush(session);

        int databaseSizeBeforeUpdate = sessionRepository.findAll().size();

        // Update the session
        Session updatedSession = sessionRepository.findById(session.getId()).get();
        // Disconnect from session so that the updates on updatedSession are not directly saved in db
        em.detach(updatedSession);
        updatedSession
            .code(UPDATED_CODE)
            .executionDate(UPDATED_EXECUTION_DATE)
            .abscence(UPDATED_ABSCENCE)
            .hospitalization(UPDATED_HOSPITALIZATION)
            .status(UPDATED_STATUS)
            .deleted(UPDATED_DELETED)
            .currentlyWorking(UPDATED_CURRENTLY_WORKING);
        SessionDTO sessionDTO = sessionMapper.toDto(updatedSession);

        restSessionMockMvc.perform(put("/api/sessions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sessionDTO)))
            .andExpect(status().isOk());

        // Validate the Session in the database
        List<Session> sessionList = sessionRepository.findAll();
        assertThat(sessionList).hasSize(databaseSizeBeforeUpdate);
        Session testSession = sessionList.get(sessionList.size() - 1);
        assertThat(testSession.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testSession.getExecutionDate()).isEqualTo(UPDATED_EXECUTION_DATE);
        assertThat(testSession.isAbscence()).isEqualTo(UPDATED_ABSCENCE);
        assertThat(testSession.isHospitalization()).isEqualTo(UPDATED_HOSPITALIZATION);
        assertThat(testSession.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testSession.isDeleted()).isEqualTo(UPDATED_DELETED);
        assertThat(testSession.isCurrentlyWorking()).isEqualTo(UPDATED_CURRENTLY_WORKING);
    }

    @Test
    @Transactional
    public void updateNonExistingSession() throws Exception {
        int databaseSizeBeforeUpdate = sessionRepository.findAll().size();

        // Create the Session
        SessionDTO sessionDTO = sessionMapper.toDto(session);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSessionMockMvc.perform(put("/api/sessions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sessionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Session in the database
        List<Session> sessionList = sessionRepository.findAll();
        assertThat(sessionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSession() throws Exception {
        // Initialize the database
        sessionRepository.saveAndFlush(session);

        int databaseSizeBeforeDelete = sessionRepository.findAll().size();

        // Delete the session
        restSessionMockMvc.perform(delete("/api/sessions/{id}", session.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Session> sessionList = sessionRepository.findAll();
        assertThat(sessionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Session.class);
        Session session1 = new Session();
        session1.setId(1L);
        Session session2 = new Session();
        session2.setId(session1.getId());
        assertThat(session1).isEqualTo(session2);
        session2.setId(2L);
        assertThat(session1).isNotEqualTo(session2);
        session1.setId(null);
        assertThat(session1).isNotEqualTo(session2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SessionDTO.class);
        SessionDTO sessionDTO1 = new SessionDTO();
        sessionDTO1.setId(1L);
        SessionDTO sessionDTO2 = new SessionDTO();
        assertThat(sessionDTO1).isNotEqualTo(sessionDTO2);
        sessionDTO2.setId(sessionDTO1.getId());
        assertThat(sessionDTO1).isEqualTo(sessionDTO2);
        sessionDTO2.setId(2L);
        assertThat(sessionDTO1).isNotEqualTo(sessionDTO2);
        sessionDTO1.setId(null);
        assertThat(sessionDTO1).isNotEqualTo(sessionDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(sessionMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(sessionMapper.fromId(null)).isNull();
    }
}
