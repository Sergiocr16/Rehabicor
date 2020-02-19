package com.aditum.rehabicor.web.rest;

import com.aditum.rehabicor.RehabicorApp;

import com.aditum.rehabicor.domain.DepressiveSymptomsSession;
import com.aditum.rehabicor.repository.DepressiveSymptomsSessionRepository;
import com.aditum.rehabicor.service.DepressiveSymptomsSessionService;
import com.aditum.rehabicor.service.dto.DepressiveSymptomsSessionDTO;
import com.aditum.rehabicor.service.mapper.DepressiveSymptomsSessionMapper;
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
 * Test class for the DepressiveSymptomsSessionResource REST controller.
 *
 * @see DepressiveSymptomsSessionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RehabicorApp.class)
public class DepressiveSymptomsSessionResourceIntTest {

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_EXIST = false;
    private static final Boolean UPDATED_EXIST = true;

    private static final Long DEFAULT_DEPRESSIVE_SYMPTOM_RELATION = 1L;
    private static final Long UPDATED_DEPRESSIVE_SYMPTOM_RELATION = 2L;

    @Autowired
    private DepressiveSymptomsSessionRepository depressiveSymptomsSessionRepository;

    @Autowired
    private DepressiveSymptomsSessionMapper depressiveSymptomsSessionMapper;

    @Autowired
    private DepressiveSymptomsSessionService depressiveSymptomsSessionService;

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

    private MockMvc restDepressiveSymptomsSessionMockMvc;

    private DepressiveSymptomsSession depressiveSymptomsSession;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DepressiveSymptomsSessionResource depressiveSymptomsSessionResource = new DepressiveSymptomsSessionResource(depressiveSymptomsSessionService);
        this.restDepressiveSymptomsSessionMockMvc = MockMvcBuilders.standaloneSetup(depressiveSymptomsSessionResource)
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
    public static DepressiveSymptomsSession createEntity(EntityManager em) {
        DepressiveSymptomsSession depressiveSymptomsSession = new DepressiveSymptomsSession()
            .description(DEFAULT_DESCRIPTION)
            .exist(DEFAULT_EXIST)
            .depressiveSymptomRelation(DEFAULT_DEPRESSIVE_SYMPTOM_RELATION);
        return depressiveSymptomsSession;
    }

    @Before
    public void initTest() {
        depressiveSymptomsSession = createEntity(em);
    }

    @Test
    @Transactional
    public void createDepressiveSymptomsSession() throws Exception {
        int databaseSizeBeforeCreate = depressiveSymptomsSessionRepository.findAll().size();

        // Create the DepressiveSymptomsSession
        DepressiveSymptomsSessionDTO depressiveSymptomsSessionDTO = depressiveSymptomsSessionMapper.toDto(depressiveSymptomsSession);
        restDepressiveSymptomsSessionMockMvc.perform(post("/api/depressive-symptoms-sessions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(depressiveSymptomsSessionDTO)))
            .andExpect(status().isCreated());

        // Validate the DepressiveSymptomsSession in the database
        List<DepressiveSymptomsSession> depressiveSymptomsSessionList = depressiveSymptomsSessionRepository.findAll();
        assertThat(depressiveSymptomsSessionList).hasSize(databaseSizeBeforeCreate + 1);
        DepressiveSymptomsSession testDepressiveSymptomsSession = depressiveSymptomsSessionList.get(depressiveSymptomsSessionList.size() - 1);
        assertThat(testDepressiveSymptomsSession.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testDepressiveSymptomsSession.isExist()).isEqualTo(DEFAULT_EXIST);
        assertThat(testDepressiveSymptomsSession.getDepressiveSymptomRelation()).isEqualTo(DEFAULT_DEPRESSIVE_SYMPTOM_RELATION);
    }

    @Test
    @Transactional
    public void createDepressiveSymptomsSessionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = depressiveSymptomsSessionRepository.findAll().size();

        // Create the DepressiveSymptomsSession with an existing ID
        depressiveSymptomsSession.setId(1L);
        DepressiveSymptomsSessionDTO depressiveSymptomsSessionDTO = depressiveSymptomsSessionMapper.toDto(depressiveSymptomsSession);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDepressiveSymptomsSessionMockMvc.perform(post("/api/depressive-symptoms-sessions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(depressiveSymptomsSessionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DepressiveSymptomsSession in the database
        List<DepressiveSymptomsSession> depressiveSymptomsSessionList = depressiveSymptomsSessionRepository.findAll();
        assertThat(depressiveSymptomsSessionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkExistIsRequired() throws Exception {
        int databaseSizeBeforeTest = depressiveSymptomsSessionRepository.findAll().size();
        // set the field null
        depressiveSymptomsSession.setExist(null);

        // Create the DepressiveSymptomsSession, which fails.
        DepressiveSymptomsSessionDTO depressiveSymptomsSessionDTO = depressiveSymptomsSessionMapper.toDto(depressiveSymptomsSession);

        restDepressiveSymptomsSessionMockMvc.perform(post("/api/depressive-symptoms-sessions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(depressiveSymptomsSessionDTO)))
            .andExpect(status().isBadRequest());

        List<DepressiveSymptomsSession> depressiveSymptomsSessionList = depressiveSymptomsSessionRepository.findAll();
        assertThat(depressiveSymptomsSessionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDepressiveSymptomsSessions() throws Exception {
        // Initialize the database
        depressiveSymptomsSessionRepository.saveAndFlush(depressiveSymptomsSession);

        // Get all the depressiveSymptomsSessionList
        restDepressiveSymptomsSessionMockMvc.perform(get("/api/depressive-symptoms-sessions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(depressiveSymptomsSession.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].exist").value(hasItem(DEFAULT_EXIST.booleanValue())))
            .andExpect(jsonPath("$.[*].depressiveSymptomRelation").value(hasItem(DEFAULT_DEPRESSIVE_SYMPTOM_RELATION.intValue())));
    }
    
    @Test
    @Transactional
    public void getDepressiveSymptomsSession() throws Exception {
        // Initialize the database
        depressiveSymptomsSessionRepository.saveAndFlush(depressiveSymptomsSession);

        // Get the depressiveSymptomsSession
        restDepressiveSymptomsSessionMockMvc.perform(get("/api/depressive-symptoms-sessions/{id}", depressiveSymptomsSession.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(depressiveSymptomsSession.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.exist").value(DEFAULT_EXIST.booleanValue()))
            .andExpect(jsonPath("$.depressiveSymptomRelation").value(DEFAULT_DEPRESSIVE_SYMPTOM_RELATION.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingDepressiveSymptomsSession() throws Exception {
        // Get the depressiveSymptomsSession
        restDepressiveSymptomsSessionMockMvc.perform(get("/api/depressive-symptoms-sessions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDepressiveSymptomsSession() throws Exception {
        // Initialize the database
        depressiveSymptomsSessionRepository.saveAndFlush(depressiveSymptomsSession);

        int databaseSizeBeforeUpdate = depressiveSymptomsSessionRepository.findAll().size();

        // Update the depressiveSymptomsSession
        DepressiveSymptomsSession updatedDepressiveSymptomsSession = depressiveSymptomsSessionRepository.findById(depressiveSymptomsSession.getId()).get();
        // Disconnect from session so that the updates on updatedDepressiveSymptomsSession are not directly saved in db
        em.detach(updatedDepressiveSymptomsSession);
        updatedDepressiveSymptomsSession
            .description(UPDATED_DESCRIPTION)
            .exist(UPDATED_EXIST)
            .depressiveSymptomRelation(UPDATED_DEPRESSIVE_SYMPTOM_RELATION);
        DepressiveSymptomsSessionDTO depressiveSymptomsSessionDTO = depressiveSymptomsSessionMapper.toDto(updatedDepressiveSymptomsSession);

        restDepressiveSymptomsSessionMockMvc.perform(put("/api/depressive-symptoms-sessions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(depressiveSymptomsSessionDTO)))
            .andExpect(status().isOk());

        // Validate the DepressiveSymptomsSession in the database
        List<DepressiveSymptomsSession> depressiveSymptomsSessionList = depressiveSymptomsSessionRepository.findAll();
        assertThat(depressiveSymptomsSessionList).hasSize(databaseSizeBeforeUpdate);
        DepressiveSymptomsSession testDepressiveSymptomsSession = depressiveSymptomsSessionList.get(depressiveSymptomsSessionList.size() - 1);
        assertThat(testDepressiveSymptomsSession.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testDepressiveSymptomsSession.isExist()).isEqualTo(UPDATED_EXIST);
        assertThat(testDepressiveSymptomsSession.getDepressiveSymptomRelation()).isEqualTo(UPDATED_DEPRESSIVE_SYMPTOM_RELATION);
    }

    @Test
    @Transactional
    public void updateNonExistingDepressiveSymptomsSession() throws Exception {
        int databaseSizeBeforeUpdate = depressiveSymptomsSessionRepository.findAll().size();

        // Create the DepressiveSymptomsSession
        DepressiveSymptomsSessionDTO depressiveSymptomsSessionDTO = depressiveSymptomsSessionMapper.toDto(depressiveSymptomsSession);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDepressiveSymptomsSessionMockMvc.perform(put("/api/depressive-symptoms-sessions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(depressiveSymptomsSessionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DepressiveSymptomsSession in the database
        List<DepressiveSymptomsSession> depressiveSymptomsSessionList = depressiveSymptomsSessionRepository.findAll();
        assertThat(depressiveSymptomsSessionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDepressiveSymptomsSession() throws Exception {
        // Initialize the database
        depressiveSymptomsSessionRepository.saveAndFlush(depressiveSymptomsSession);

        int databaseSizeBeforeDelete = depressiveSymptomsSessionRepository.findAll().size();

        // Delete the depressiveSymptomsSession
        restDepressiveSymptomsSessionMockMvc.perform(delete("/api/depressive-symptoms-sessions/{id}", depressiveSymptomsSession.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<DepressiveSymptomsSession> depressiveSymptomsSessionList = depressiveSymptomsSessionRepository.findAll();
        assertThat(depressiveSymptomsSessionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DepressiveSymptomsSession.class);
        DepressiveSymptomsSession depressiveSymptomsSession1 = new DepressiveSymptomsSession();
        depressiveSymptomsSession1.setId(1L);
        DepressiveSymptomsSession depressiveSymptomsSession2 = new DepressiveSymptomsSession();
        depressiveSymptomsSession2.setId(depressiveSymptomsSession1.getId());
        assertThat(depressiveSymptomsSession1).isEqualTo(depressiveSymptomsSession2);
        depressiveSymptomsSession2.setId(2L);
        assertThat(depressiveSymptomsSession1).isNotEqualTo(depressiveSymptomsSession2);
        depressiveSymptomsSession1.setId(null);
        assertThat(depressiveSymptomsSession1).isNotEqualTo(depressiveSymptomsSession2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DepressiveSymptomsSessionDTO.class);
        DepressiveSymptomsSessionDTO depressiveSymptomsSessionDTO1 = new DepressiveSymptomsSessionDTO();
        depressiveSymptomsSessionDTO1.setId(1L);
        DepressiveSymptomsSessionDTO depressiveSymptomsSessionDTO2 = new DepressiveSymptomsSessionDTO();
        assertThat(depressiveSymptomsSessionDTO1).isNotEqualTo(depressiveSymptomsSessionDTO2);
        depressiveSymptomsSessionDTO2.setId(depressiveSymptomsSessionDTO1.getId());
        assertThat(depressiveSymptomsSessionDTO1).isEqualTo(depressiveSymptomsSessionDTO2);
        depressiveSymptomsSessionDTO2.setId(2L);
        assertThat(depressiveSymptomsSessionDTO1).isNotEqualTo(depressiveSymptomsSessionDTO2);
        depressiveSymptomsSessionDTO1.setId(null);
        assertThat(depressiveSymptomsSessionDTO1).isNotEqualTo(depressiveSymptomsSessionDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(depressiveSymptomsSessionMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(depressiveSymptomsSessionMapper.fromId(null)).isNull();
    }
}
