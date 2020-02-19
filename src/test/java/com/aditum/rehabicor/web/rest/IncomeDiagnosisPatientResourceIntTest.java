package com.aditum.rehabicor.web.rest;

import com.aditum.rehabicor.RehabicorApp;

import com.aditum.rehabicor.domain.IncomeDiagnosisPatient;
import com.aditum.rehabicor.repository.IncomeDiagnosisPatientRepository;
import com.aditum.rehabicor.service.IncomeDiagnosisPatientService;
import com.aditum.rehabicor.service.dto.IncomeDiagnosisPatientDTO;
import com.aditum.rehabicor.service.mapper.IncomeDiagnosisPatientMapper;
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
 * Test class for the IncomeDiagnosisPatientResource REST controller.
 *
 * @see IncomeDiagnosisPatientResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RehabicorApp.class)
public class IncomeDiagnosisPatientResourceIntTest {

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_EXIST = false;
    private static final Boolean UPDATED_EXIST = true;

    private static final Long DEFAULT_INCOME_DIAGNOSIS_RELATION = 1L;
    private static final Long UPDATED_INCOME_DIAGNOSIS_RELATION = 2L;

    @Autowired
    private IncomeDiagnosisPatientRepository incomeDiagnosisPatientRepository;

    @Autowired
    private IncomeDiagnosisPatientMapper incomeDiagnosisPatientMapper;

    @Autowired
    private IncomeDiagnosisPatientService incomeDiagnosisPatientService;

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

    private MockMvc restIncomeDiagnosisPatientMockMvc;

    private IncomeDiagnosisPatient incomeDiagnosisPatient;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final IncomeDiagnosisPatientResource incomeDiagnosisPatientResource = new IncomeDiagnosisPatientResource(incomeDiagnosisPatientService);
        this.restIncomeDiagnosisPatientMockMvc = MockMvcBuilders.standaloneSetup(incomeDiagnosisPatientResource)
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
    public static IncomeDiagnosisPatient createEntity(EntityManager em) {
        IncomeDiagnosisPatient incomeDiagnosisPatient = new IncomeDiagnosisPatient()
            .description(DEFAULT_DESCRIPTION)
            .exist(DEFAULT_EXIST)
            .incomeDiagnosisRelation(DEFAULT_INCOME_DIAGNOSIS_RELATION);
        return incomeDiagnosisPatient;
    }

    @Before
    public void initTest() {
        incomeDiagnosisPatient = createEntity(em);
    }

    @Test
    @Transactional
    public void createIncomeDiagnosisPatient() throws Exception {
        int databaseSizeBeforeCreate = incomeDiagnosisPatientRepository.findAll().size();

        // Create the IncomeDiagnosisPatient
        IncomeDiagnosisPatientDTO incomeDiagnosisPatientDTO = incomeDiagnosisPatientMapper.toDto(incomeDiagnosisPatient);
        restIncomeDiagnosisPatientMockMvc.perform(post("/api/income-diagnosis-patients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(incomeDiagnosisPatientDTO)))
            .andExpect(status().isCreated());

        // Validate the IncomeDiagnosisPatient in the database
        List<IncomeDiagnosisPatient> incomeDiagnosisPatientList = incomeDiagnosisPatientRepository.findAll();
        assertThat(incomeDiagnosisPatientList).hasSize(databaseSizeBeforeCreate + 1);
        IncomeDiagnosisPatient testIncomeDiagnosisPatient = incomeDiagnosisPatientList.get(incomeDiagnosisPatientList.size() - 1);
        assertThat(testIncomeDiagnosisPatient.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testIncomeDiagnosisPatient.isExist()).isEqualTo(DEFAULT_EXIST);
        assertThat(testIncomeDiagnosisPatient.getIncomeDiagnosisRelation()).isEqualTo(DEFAULT_INCOME_DIAGNOSIS_RELATION);
    }

    @Test
    @Transactional
    public void createIncomeDiagnosisPatientWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = incomeDiagnosisPatientRepository.findAll().size();

        // Create the IncomeDiagnosisPatient with an existing ID
        incomeDiagnosisPatient.setId(1L);
        IncomeDiagnosisPatientDTO incomeDiagnosisPatientDTO = incomeDiagnosisPatientMapper.toDto(incomeDiagnosisPatient);

        // An entity with an existing ID cannot be created, so this API call must fail
        restIncomeDiagnosisPatientMockMvc.perform(post("/api/income-diagnosis-patients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(incomeDiagnosisPatientDTO)))
            .andExpect(status().isBadRequest());

        // Validate the IncomeDiagnosisPatient in the database
        List<IncomeDiagnosisPatient> incomeDiagnosisPatientList = incomeDiagnosisPatientRepository.findAll();
        assertThat(incomeDiagnosisPatientList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkExistIsRequired() throws Exception {
        int databaseSizeBeforeTest = incomeDiagnosisPatientRepository.findAll().size();
        // set the field null
        incomeDiagnosisPatient.setExist(null);

        // Create the IncomeDiagnosisPatient, which fails.
        IncomeDiagnosisPatientDTO incomeDiagnosisPatientDTO = incomeDiagnosisPatientMapper.toDto(incomeDiagnosisPatient);

        restIncomeDiagnosisPatientMockMvc.perform(post("/api/income-diagnosis-patients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(incomeDiagnosisPatientDTO)))
            .andExpect(status().isBadRequest());

        List<IncomeDiagnosisPatient> incomeDiagnosisPatientList = incomeDiagnosisPatientRepository.findAll();
        assertThat(incomeDiagnosisPatientList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllIncomeDiagnosisPatients() throws Exception {
        // Initialize the database
        incomeDiagnosisPatientRepository.saveAndFlush(incomeDiagnosisPatient);

        // Get all the incomeDiagnosisPatientList
        restIncomeDiagnosisPatientMockMvc.perform(get("/api/income-diagnosis-patients?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(incomeDiagnosisPatient.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].exist").value(hasItem(DEFAULT_EXIST.booleanValue())))
            .andExpect(jsonPath("$.[*].incomeDiagnosisRelation").value(hasItem(DEFAULT_INCOME_DIAGNOSIS_RELATION.intValue())));
    }
    
    @Test
    @Transactional
    public void getIncomeDiagnosisPatient() throws Exception {
        // Initialize the database
        incomeDiagnosisPatientRepository.saveAndFlush(incomeDiagnosisPatient);

        // Get the incomeDiagnosisPatient
        restIncomeDiagnosisPatientMockMvc.perform(get("/api/income-diagnosis-patients/{id}", incomeDiagnosisPatient.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(incomeDiagnosisPatient.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.exist").value(DEFAULT_EXIST.booleanValue()))
            .andExpect(jsonPath("$.incomeDiagnosisRelation").value(DEFAULT_INCOME_DIAGNOSIS_RELATION.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingIncomeDiagnosisPatient() throws Exception {
        // Get the incomeDiagnosisPatient
        restIncomeDiagnosisPatientMockMvc.perform(get("/api/income-diagnosis-patients/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateIncomeDiagnosisPatient() throws Exception {
        // Initialize the database
        incomeDiagnosisPatientRepository.saveAndFlush(incomeDiagnosisPatient);

        int databaseSizeBeforeUpdate = incomeDiagnosisPatientRepository.findAll().size();

        // Update the incomeDiagnosisPatient
        IncomeDiagnosisPatient updatedIncomeDiagnosisPatient = incomeDiagnosisPatientRepository.findById(incomeDiagnosisPatient.getId()).get();
        // Disconnect from session so that the updates on updatedIncomeDiagnosisPatient are not directly saved in db
        em.detach(updatedIncomeDiagnosisPatient);
        updatedIncomeDiagnosisPatient
            .description(UPDATED_DESCRIPTION)
            .exist(UPDATED_EXIST)
            .incomeDiagnosisRelation(UPDATED_INCOME_DIAGNOSIS_RELATION);
        IncomeDiagnosisPatientDTO incomeDiagnosisPatientDTO = incomeDiagnosisPatientMapper.toDto(updatedIncomeDiagnosisPatient);

        restIncomeDiagnosisPatientMockMvc.perform(put("/api/income-diagnosis-patients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(incomeDiagnosisPatientDTO)))
            .andExpect(status().isOk());

        // Validate the IncomeDiagnosisPatient in the database
        List<IncomeDiagnosisPatient> incomeDiagnosisPatientList = incomeDiagnosisPatientRepository.findAll();
        assertThat(incomeDiagnosisPatientList).hasSize(databaseSizeBeforeUpdate);
        IncomeDiagnosisPatient testIncomeDiagnosisPatient = incomeDiagnosisPatientList.get(incomeDiagnosisPatientList.size() - 1);
        assertThat(testIncomeDiagnosisPatient.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testIncomeDiagnosisPatient.isExist()).isEqualTo(UPDATED_EXIST);
        assertThat(testIncomeDiagnosisPatient.getIncomeDiagnosisRelation()).isEqualTo(UPDATED_INCOME_DIAGNOSIS_RELATION);
    }

    @Test
    @Transactional
    public void updateNonExistingIncomeDiagnosisPatient() throws Exception {
        int databaseSizeBeforeUpdate = incomeDiagnosisPatientRepository.findAll().size();

        // Create the IncomeDiagnosisPatient
        IncomeDiagnosisPatientDTO incomeDiagnosisPatientDTO = incomeDiagnosisPatientMapper.toDto(incomeDiagnosisPatient);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIncomeDiagnosisPatientMockMvc.perform(put("/api/income-diagnosis-patients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(incomeDiagnosisPatientDTO)))
            .andExpect(status().isBadRequest());

        // Validate the IncomeDiagnosisPatient in the database
        List<IncomeDiagnosisPatient> incomeDiagnosisPatientList = incomeDiagnosisPatientRepository.findAll();
        assertThat(incomeDiagnosisPatientList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteIncomeDiagnosisPatient() throws Exception {
        // Initialize the database
        incomeDiagnosisPatientRepository.saveAndFlush(incomeDiagnosisPatient);

        int databaseSizeBeforeDelete = incomeDiagnosisPatientRepository.findAll().size();

        // Delete the incomeDiagnosisPatient
        restIncomeDiagnosisPatientMockMvc.perform(delete("/api/income-diagnosis-patients/{id}", incomeDiagnosisPatient.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<IncomeDiagnosisPatient> incomeDiagnosisPatientList = incomeDiagnosisPatientRepository.findAll();
        assertThat(incomeDiagnosisPatientList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(IncomeDiagnosisPatient.class);
        IncomeDiagnosisPatient incomeDiagnosisPatient1 = new IncomeDiagnosisPatient();
        incomeDiagnosisPatient1.setId(1L);
        IncomeDiagnosisPatient incomeDiagnosisPatient2 = new IncomeDiagnosisPatient();
        incomeDiagnosisPatient2.setId(incomeDiagnosisPatient1.getId());
        assertThat(incomeDiagnosisPatient1).isEqualTo(incomeDiagnosisPatient2);
        incomeDiagnosisPatient2.setId(2L);
        assertThat(incomeDiagnosisPatient1).isNotEqualTo(incomeDiagnosisPatient2);
        incomeDiagnosisPatient1.setId(null);
        assertThat(incomeDiagnosisPatient1).isNotEqualTo(incomeDiagnosisPatient2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(IncomeDiagnosisPatientDTO.class);
        IncomeDiagnosisPatientDTO incomeDiagnosisPatientDTO1 = new IncomeDiagnosisPatientDTO();
        incomeDiagnosisPatientDTO1.setId(1L);
        IncomeDiagnosisPatientDTO incomeDiagnosisPatientDTO2 = new IncomeDiagnosisPatientDTO();
        assertThat(incomeDiagnosisPatientDTO1).isNotEqualTo(incomeDiagnosisPatientDTO2);
        incomeDiagnosisPatientDTO2.setId(incomeDiagnosisPatientDTO1.getId());
        assertThat(incomeDiagnosisPatientDTO1).isEqualTo(incomeDiagnosisPatientDTO2);
        incomeDiagnosisPatientDTO2.setId(2L);
        assertThat(incomeDiagnosisPatientDTO1).isNotEqualTo(incomeDiagnosisPatientDTO2);
        incomeDiagnosisPatientDTO1.setId(null);
        assertThat(incomeDiagnosisPatientDTO1).isNotEqualTo(incomeDiagnosisPatientDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(incomeDiagnosisPatientMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(incomeDiagnosisPatientMapper.fromId(null)).isNull();
    }
}
