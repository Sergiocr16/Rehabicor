package com.aditum.rehabicor.web.rest;

import com.aditum.rehabicor.RehabicorApp;

import com.aditum.rehabicor.domain.InitialAssessment;
import com.aditum.rehabicor.repository.InitialAssessmentRepository;
import com.aditum.rehabicor.service.InitialAssessmentService;
import com.aditum.rehabicor.service.dto.InitialAssessmentDTO;
import com.aditum.rehabicor.service.mapper.InitialAssessmentMapper;
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
 * Test class for the InitialAssessmentResource REST controller.
 *
 * @see InitialAssessmentResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RehabicorApp.class)
public class InitialAssessmentResourceIntTest {

    private static final String DEFAULT_SMOKING = "AAAAAAAAAA";
    private static final String UPDATED_SMOKING = "BBBBBBBBBB";

    private static final String DEFAULT_WEIGHT = "AAAAAAAAAA";
    private static final String UPDATED_WEIGHT = "BBBBBBBBBB";

    private static final String DEFAULT_SIZE = "AAAAAAAAAA";
    private static final String UPDATED_SIZE = "BBBBBBBBBB";

    private static final String DEFAULT_I_MC = "AAAAAAAAAA";
    private static final String UPDATED_I_MC = "BBBBBBBBBB";

    private static final String DEFAULT_HBIAC = "AAAAAAAAAA";
    private static final String UPDATED_HBIAC = "BBBBBBBBBB";

    private static final String DEFAULT_BASELINE_FUNCTIONAL_CAPACITY = "AAAAAAAAAA";
    private static final String UPDATED_BASELINE_FUNCTIONAL_CAPACITY = "BBBBBBBBBB";

    private static final String DEFAULT_L_DL = "AAAAAAAAAA";
    private static final String UPDATED_L_DL = "BBBBBBBBBB";

    private static final String DEFAULT_H_DL = "AAAAAAAAAA";
    private static final String UPDATED_H_DL = "BBBBBBBBBB";

    private static final String DEFAULT_CARDIOVASCULAR_RISK = "AAAAAAAAAA";
    private static final String UPDATED_CARDIOVASCULAR_RISK = "BBBBBBBBBB";

    private static final Boolean DEFAULT_DELETED = false;
    private static final Boolean UPDATED_DELETED = true;

    @Autowired
    private InitialAssessmentRepository initialAssessmentRepository;

    @Autowired
    private InitialAssessmentMapper initialAssessmentMapper;

    @Autowired
    private InitialAssessmentService initialAssessmentService;

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

    private MockMvc restInitialAssessmentMockMvc;

    private InitialAssessment initialAssessment;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final InitialAssessmentResource initialAssessmentResource = new InitialAssessmentResource(initialAssessmentService);
        this.restInitialAssessmentMockMvc = MockMvcBuilders.standaloneSetup(initialAssessmentResource)
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
    public static InitialAssessment createEntity(EntityManager em) {
        InitialAssessment initialAssessment = new InitialAssessment()
            .smoking(DEFAULT_SMOKING)
            .weight(DEFAULT_WEIGHT)
            .size(DEFAULT_SIZE)
            .iMC(DEFAULT_I_MC)
            .hbiac(DEFAULT_HBIAC)
            .baselineFunctionalCapacity(DEFAULT_BASELINE_FUNCTIONAL_CAPACITY)
            .lDL(DEFAULT_L_DL)
            .hDL(DEFAULT_H_DL)
            .cardiovascularRisk(DEFAULT_CARDIOVASCULAR_RISK)
            .deleted(DEFAULT_DELETED);
        return initialAssessment;
    }

    @Before
    public void initTest() {
        initialAssessment = createEntity(em);
    }

    @Test
    @Transactional
    public void createInitialAssessment() throws Exception {
        int databaseSizeBeforeCreate = initialAssessmentRepository.findAll().size();

        // Create the InitialAssessment
        InitialAssessmentDTO initialAssessmentDTO = initialAssessmentMapper.toDto(initialAssessment);
        restInitialAssessmentMockMvc.perform(post("/api/initial-assessments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(initialAssessmentDTO)))
            .andExpect(status().isCreated());

        // Validate the InitialAssessment in the database
        List<InitialAssessment> initialAssessmentList = initialAssessmentRepository.findAll();
        assertThat(initialAssessmentList).hasSize(databaseSizeBeforeCreate + 1);
        InitialAssessment testInitialAssessment = initialAssessmentList.get(initialAssessmentList.size() - 1);
        assertThat(testInitialAssessment.getSmoking()).isEqualTo(DEFAULT_SMOKING);
        assertThat(testInitialAssessment.getWeight()).isEqualTo(DEFAULT_WEIGHT);
        assertThat(testInitialAssessment.getSize()).isEqualTo(DEFAULT_SIZE);
        assertThat(testInitialAssessment.getiMC()).isEqualTo(DEFAULT_I_MC);
        assertThat(testInitialAssessment.getHbiac()).isEqualTo(DEFAULT_HBIAC);
        assertThat(testInitialAssessment.getBaselineFunctionalCapacity()).isEqualTo(DEFAULT_BASELINE_FUNCTIONAL_CAPACITY);
        assertThat(testInitialAssessment.getlDL()).isEqualTo(DEFAULT_L_DL);
        assertThat(testInitialAssessment.gethDL()).isEqualTo(DEFAULT_H_DL);
        assertThat(testInitialAssessment.getCardiovascularRisk()).isEqualTo(DEFAULT_CARDIOVASCULAR_RISK);
        assertThat(testInitialAssessment.isDeleted()).isEqualTo(DEFAULT_DELETED);
    }

    @Test
    @Transactional
    public void createInitialAssessmentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = initialAssessmentRepository.findAll().size();

        // Create the InitialAssessment with an existing ID
        initialAssessment.setId(1L);
        InitialAssessmentDTO initialAssessmentDTO = initialAssessmentMapper.toDto(initialAssessment);

        // An entity with an existing ID cannot be created, so this API call must fail
        restInitialAssessmentMockMvc.perform(post("/api/initial-assessments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(initialAssessmentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the InitialAssessment in the database
        List<InitialAssessment> initialAssessmentList = initialAssessmentRepository.findAll();
        assertThat(initialAssessmentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkSmokingIsRequired() throws Exception {
        int databaseSizeBeforeTest = initialAssessmentRepository.findAll().size();
        // set the field null
        initialAssessment.setSmoking(null);

        // Create the InitialAssessment, which fails.
        InitialAssessmentDTO initialAssessmentDTO = initialAssessmentMapper.toDto(initialAssessment);

        restInitialAssessmentMockMvc.perform(post("/api/initial-assessments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(initialAssessmentDTO)))
            .andExpect(status().isBadRequest());

        List<InitialAssessment> initialAssessmentList = initialAssessmentRepository.findAll();
        assertThat(initialAssessmentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkWeightIsRequired() throws Exception {
        int databaseSizeBeforeTest = initialAssessmentRepository.findAll().size();
        // set the field null
        initialAssessment.setWeight(null);

        // Create the InitialAssessment, which fails.
        InitialAssessmentDTO initialAssessmentDTO = initialAssessmentMapper.toDto(initialAssessment);

        restInitialAssessmentMockMvc.perform(post("/api/initial-assessments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(initialAssessmentDTO)))
            .andExpect(status().isBadRequest());

        List<InitialAssessment> initialAssessmentList = initialAssessmentRepository.findAll();
        assertThat(initialAssessmentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSizeIsRequired() throws Exception {
        int databaseSizeBeforeTest = initialAssessmentRepository.findAll().size();
        // set the field null
        initialAssessment.setSize(null);

        // Create the InitialAssessment, which fails.
        InitialAssessmentDTO initialAssessmentDTO = initialAssessmentMapper.toDto(initialAssessment);

        restInitialAssessmentMockMvc.perform(post("/api/initial-assessments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(initialAssessmentDTO)))
            .andExpect(status().isBadRequest());

        List<InitialAssessment> initialAssessmentList = initialAssessmentRepository.findAll();
        assertThat(initialAssessmentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkiMCIsRequired() throws Exception {
        int databaseSizeBeforeTest = initialAssessmentRepository.findAll().size();
        // set the field null
        initialAssessment.setiMC(null);

        // Create the InitialAssessment, which fails.
        InitialAssessmentDTO initialAssessmentDTO = initialAssessmentMapper.toDto(initialAssessment);

        restInitialAssessmentMockMvc.perform(post("/api/initial-assessments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(initialAssessmentDTO)))
            .andExpect(status().isBadRequest());

        List<InitialAssessment> initialAssessmentList = initialAssessmentRepository.findAll();
        assertThat(initialAssessmentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllInitialAssessments() throws Exception {
        // Initialize the database
        initialAssessmentRepository.saveAndFlush(initialAssessment);

        // Get all the initialAssessmentList
        restInitialAssessmentMockMvc.perform(get("/api/initial-assessments?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(initialAssessment.getId().intValue())))
            .andExpect(jsonPath("$.[*].smoking").value(hasItem(DEFAULT_SMOKING.toString())))
            .andExpect(jsonPath("$.[*].weight").value(hasItem(DEFAULT_WEIGHT.toString())))
            .andExpect(jsonPath("$.[*].size").value(hasItem(DEFAULT_SIZE.toString())))
            .andExpect(jsonPath("$.[*].iMC").value(hasItem(DEFAULT_I_MC.toString())))
            .andExpect(jsonPath("$.[*].hbiac").value(hasItem(DEFAULT_HBIAC.toString())))
            .andExpect(jsonPath("$.[*].baselineFunctionalCapacity").value(hasItem(DEFAULT_BASELINE_FUNCTIONAL_CAPACITY.toString())))
            .andExpect(jsonPath("$.[*].lDL").value(hasItem(DEFAULT_L_DL.toString())))
            .andExpect(jsonPath("$.[*].hDL").value(hasItem(DEFAULT_H_DL.toString())))
            .andExpect(jsonPath("$.[*].cardiovascularRisk").value(hasItem(DEFAULT_CARDIOVASCULAR_RISK.toString())))
            .andExpect(jsonPath("$.[*].deleted").value(hasItem(DEFAULT_DELETED.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getInitialAssessment() throws Exception {
        // Initialize the database
        initialAssessmentRepository.saveAndFlush(initialAssessment);

        // Get the initialAssessment
        restInitialAssessmentMockMvc.perform(get("/api/initial-assessments/{id}", initialAssessment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(initialAssessment.getId().intValue()))
            .andExpect(jsonPath("$.smoking").value(DEFAULT_SMOKING.toString()))
            .andExpect(jsonPath("$.weight").value(DEFAULT_WEIGHT.toString()))
            .andExpect(jsonPath("$.size").value(DEFAULT_SIZE.toString()))
            .andExpect(jsonPath("$.iMC").value(DEFAULT_I_MC.toString()))
            .andExpect(jsonPath("$.hbiac").value(DEFAULT_HBIAC.toString()))
            .andExpect(jsonPath("$.baselineFunctionalCapacity").value(DEFAULT_BASELINE_FUNCTIONAL_CAPACITY.toString()))
            .andExpect(jsonPath("$.lDL").value(DEFAULT_L_DL.toString()))
            .andExpect(jsonPath("$.hDL").value(DEFAULT_H_DL.toString()))
            .andExpect(jsonPath("$.cardiovascularRisk").value(DEFAULT_CARDIOVASCULAR_RISK.toString()))
            .andExpect(jsonPath("$.deleted").value(DEFAULT_DELETED.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingInitialAssessment() throws Exception {
        // Get the initialAssessment
        restInitialAssessmentMockMvc.perform(get("/api/initial-assessments/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInitialAssessment() throws Exception {
        // Initialize the database
        initialAssessmentRepository.saveAndFlush(initialAssessment);

        int databaseSizeBeforeUpdate = initialAssessmentRepository.findAll().size();

        // Update the initialAssessment
        InitialAssessment updatedInitialAssessment = initialAssessmentRepository.findById(initialAssessment.getId()).get();
        // Disconnect from session so that the updates on updatedInitialAssessment are not directly saved in db
        em.detach(updatedInitialAssessment);
        updatedInitialAssessment
            .smoking(UPDATED_SMOKING)
            .weight(UPDATED_WEIGHT)
            .size(UPDATED_SIZE)
            .iMC(UPDATED_I_MC)
            .hbiac(UPDATED_HBIAC)
            .baselineFunctionalCapacity(UPDATED_BASELINE_FUNCTIONAL_CAPACITY)
            .lDL(UPDATED_L_DL)
            .hDL(UPDATED_H_DL)
            .cardiovascularRisk(UPDATED_CARDIOVASCULAR_RISK)
            .deleted(UPDATED_DELETED);
        InitialAssessmentDTO initialAssessmentDTO = initialAssessmentMapper.toDto(updatedInitialAssessment);

        restInitialAssessmentMockMvc.perform(put("/api/initial-assessments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(initialAssessmentDTO)))
            .andExpect(status().isOk());

        // Validate the InitialAssessment in the database
        List<InitialAssessment> initialAssessmentList = initialAssessmentRepository.findAll();
        assertThat(initialAssessmentList).hasSize(databaseSizeBeforeUpdate);
        InitialAssessment testInitialAssessment = initialAssessmentList.get(initialAssessmentList.size() - 1);
        assertThat(testInitialAssessment.getSmoking()).isEqualTo(UPDATED_SMOKING);
        assertThat(testInitialAssessment.getWeight()).isEqualTo(UPDATED_WEIGHT);
        assertThat(testInitialAssessment.getSize()).isEqualTo(UPDATED_SIZE);
        assertThat(testInitialAssessment.getiMC()).isEqualTo(UPDATED_I_MC);
        assertThat(testInitialAssessment.getHbiac()).isEqualTo(UPDATED_HBIAC);
        assertThat(testInitialAssessment.getBaselineFunctionalCapacity()).isEqualTo(UPDATED_BASELINE_FUNCTIONAL_CAPACITY);
        assertThat(testInitialAssessment.getlDL()).isEqualTo(UPDATED_L_DL);
        assertThat(testInitialAssessment.gethDL()).isEqualTo(UPDATED_H_DL);
        assertThat(testInitialAssessment.getCardiovascularRisk()).isEqualTo(UPDATED_CARDIOVASCULAR_RISK);
        assertThat(testInitialAssessment.isDeleted()).isEqualTo(UPDATED_DELETED);
    }

    @Test
    @Transactional
    public void updateNonExistingInitialAssessment() throws Exception {
        int databaseSizeBeforeUpdate = initialAssessmentRepository.findAll().size();

        // Create the InitialAssessment
        InitialAssessmentDTO initialAssessmentDTO = initialAssessmentMapper.toDto(initialAssessment);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInitialAssessmentMockMvc.perform(put("/api/initial-assessments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(initialAssessmentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the InitialAssessment in the database
        List<InitialAssessment> initialAssessmentList = initialAssessmentRepository.findAll();
        assertThat(initialAssessmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteInitialAssessment() throws Exception {
        // Initialize the database
        initialAssessmentRepository.saveAndFlush(initialAssessment);

        int databaseSizeBeforeDelete = initialAssessmentRepository.findAll().size();

        // Delete the initialAssessment
        restInitialAssessmentMockMvc.perform(delete("/api/initial-assessments/{id}", initialAssessment.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<InitialAssessment> initialAssessmentList = initialAssessmentRepository.findAll();
        assertThat(initialAssessmentList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(InitialAssessment.class);
        InitialAssessment initialAssessment1 = new InitialAssessment();
        initialAssessment1.setId(1L);
        InitialAssessment initialAssessment2 = new InitialAssessment();
        initialAssessment2.setId(initialAssessment1.getId());
        assertThat(initialAssessment1).isEqualTo(initialAssessment2);
        initialAssessment2.setId(2L);
        assertThat(initialAssessment1).isNotEqualTo(initialAssessment2);
        initialAssessment1.setId(null);
        assertThat(initialAssessment1).isNotEqualTo(initialAssessment2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(InitialAssessmentDTO.class);
        InitialAssessmentDTO initialAssessmentDTO1 = new InitialAssessmentDTO();
        initialAssessmentDTO1.setId(1L);
        InitialAssessmentDTO initialAssessmentDTO2 = new InitialAssessmentDTO();
        assertThat(initialAssessmentDTO1).isNotEqualTo(initialAssessmentDTO2);
        initialAssessmentDTO2.setId(initialAssessmentDTO1.getId());
        assertThat(initialAssessmentDTO1).isEqualTo(initialAssessmentDTO2);
        initialAssessmentDTO2.setId(2L);
        assertThat(initialAssessmentDTO1).isNotEqualTo(initialAssessmentDTO2);
        initialAssessmentDTO1.setId(null);
        assertThat(initialAssessmentDTO1).isNotEqualTo(initialAssessmentDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(initialAssessmentMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(initialAssessmentMapper.fromId(null)).isNull();
    }
}
