package com.aditum.rehabicor.web.rest;

import com.aditum.rehabicor.RehabicorApp;

import com.aditum.rehabicor.domain.Patient;
import com.aditum.rehabicor.repository.PatientRepository;
import com.aditum.rehabicor.service.PatientService;
import com.aditum.rehabicor.service.dto.PatientDTO;
import com.aditum.rehabicor.service.mapper.PatientMapper;
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
 * Test class for the PatientResource REST controller.
 *
 * @see PatientResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RehabicorApp.class)
public class PatientResourceIntTest {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final Integer DEFAULT_AGE = 1;
    private static final Integer UPDATED_AGE = 2;

    private static final String DEFAULT_SEX = "AAAAAAAAAA";
    private static final String UPDATED_SEX = "BBBBBBBBBB";

    private static final String DEFAULT_OCUPATION = "AAAAAAAAAA";
    private static final String UPDATED_OCUPATION = "BBBBBBBBBB";

    private static final Integer DEFAULT_LAST_EVENT_OCURRED = 1;
    private static final Integer UPDATED_LAST_EVENT_OCURRED = 2;

    private static final Boolean DEFAULT_DECEASED = false;
    private static final Boolean UPDATED_DECEASED = true;

    private static final Boolean DEFAULT_ABANDONMENT = false;
    private static final Boolean UPDATED_ABANDONMENT = true;

    private static final Boolean DEFAULT_ABANDONMENT_MEDIC_CAUSE = false;
    private static final Boolean UPDATED_ABANDONMENT_MEDIC_CAUSE = true;

    private static final Integer DEFAULT_REHAB_STATUS = 1;
    private static final Integer UPDATED_REHAB_STATUS = 2;

    private static final Integer DEFAULT_SESSION_NUMBER = 1;
    private static final Integer UPDATED_SESSION_NUMBER = 2;

    private static final Boolean DEFAULT_DELETED = false;
    private static final Boolean UPDATED_DELETED = true;

    private static final String DEFAULT_SCHOLARSHIP = "AAAAAAAAAA";
    private static final String UPDATED_SCHOLARSHIP = "BBBBBBBBBB";

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private PatientMapper patientMapper;

    @Autowired
    private PatientService patientService;

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

    private MockMvc restPatientMockMvc;

    private Patient patient;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PatientResource patientResource = new PatientResource(patientService);
        this.restPatientMockMvc = MockMvcBuilders.standaloneSetup(patientResource)
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
    public static Patient createEntity(EntityManager em) {
        Patient patient = new Patient()
            .code(DEFAULT_CODE)
            .age(DEFAULT_AGE)
            .sex(DEFAULT_SEX)
            .ocupation(DEFAULT_OCUPATION)
            .lastEventOcurred(DEFAULT_LAST_EVENT_OCURRED)
            .deceased(DEFAULT_DECEASED)
            .abandonment(DEFAULT_ABANDONMENT)
            .abandonmentMedicCause(DEFAULT_ABANDONMENT_MEDIC_CAUSE)
            .rehabStatus(DEFAULT_REHAB_STATUS)
            .sessionNumber(DEFAULT_SESSION_NUMBER)
            .deleted(DEFAULT_DELETED)
            .scholarship(DEFAULT_SCHOLARSHIP);
        return patient;
    }

    @Before
    public void initTest() {
        patient = createEntity(em);
    }

    @Test
    @Transactional
    public void createPatient() throws Exception {
        int databaseSizeBeforeCreate = patientRepository.findAll().size();

        // Create the Patient
        PatientDTO patientDTO = patientMapper.toDto(patient);
        restPatientMockMvc.perform(post("/api/patients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(patientDTO)))
            .andExpect(status().isCreated());

        // Validate the Patient in the database
        List<Patient> patientList = patientRepository.findAll();
        assertThat(patientList).hasSize(databaseSizeBeforeCreate + 1);
        Patient testPatient = patientList.get(patientList.size() - 1);
        assertThat(testPatient.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testPatient.getAge()).isEqualTo(DEFAULT_AGE);
        assertThat(testPatient.getSex()).isEqualTo(DEFAULT_SEX);
        assertThat(testPatient.getOcupation()).isEqualTo(DEFAULT_OCUPATION);
        assertThat(testPatient.getLastEventOcurred()).isEqualTo(DEFAULT_LAST_EVENT_OCURRED);
        assertThat(testPatient.isDeceased()).isEqualTo(DEFAULT_DECEASED);
        assertThat(testPatient.isAbandonment()).isEqualTo(DEFAULT_ABANDONMENT);
        assertThat(testPatient.isAbandonmentMedicCause()).isEqualTo(DEFAULT_ABANDONMENT_MEDIC_CAUSE);
        assertThat(testPatient.getRehabStatus()).isEqualTo(DEFAULT_REHAB_STATUS);
        assertThat(testPatient.getSessionNumber()).isEqualTo(DEFAULT_SESSION_NUMBER);
        assertThat(testPatient.isDeleted()).isEqualTo(DEFAULT_DELETED);
        assertThat(testPatient.getScholarship()).isEqualTo(DEFAULT_SCHOLARSHIP);
    }

    @Test
    @Transactional
    public void createPatientWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = patientRepository.findAll().size();

        // Create the Patient with an existing ID
        patient.setId(1L);
        PatientDTO patientDTO = patientMapper.toDto(patient);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPatientMockMvc.perform(post("/api/patients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(patientDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Patient in the database
        List<Patient> patientList = patientRepository.findAll();
        assertThat(patientList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = patientRepository.findAll().size();
        // set the field null
        patient.setCode(null);

        // Create the Patient, which fails.
        PatientDTO patientDTO = patientMapper.toDto(patient);

        restPatientMockMvc.perform(post("/api/patients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(patientDTO)))
            .andExpect(status().isBadRequest());

        List<Patient> patientList = patientRepository.findAll();
        assertThat(patientList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAgeIsRequired() throws Exception {
        int databaseSizeBeforeTest = patientRepository.findAll().size();
        // set the field null
        patient.setAge(null);

        // Create the Patient, which fails.
        PatientDTO patientDTO = patientMapper.toDto(patient);

        restPatientMockMvc.perform(post("/api/patients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(patientDTO)))
            .andExpect(status().isBadRequest());

        List<Patient> patientList = patientRepository.findAll();
        assertThat(patientList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSexIsRequired() throws Exception {
        int databaseSizeBeforeTest = patientRepository.findAll().size();
        // set the field null
        patient.setSex(null);

        // Create the Patient, which fails.
        PatientDTO patientDTO = patientMapper.toDto(patient);

        restPatientMockMvc.perform(post("/api/patients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(patientDTO)))
            .andExpect(status().isBadRequest());

        List<Patient> patientList = patientRepository.findAll();
        assertThat(patientList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkOcupationIsRequired() throws Exception {
        int databaseSizeBeforeTest = patientRepository.findAll().size();
        // set the field null
        patient.setOcupation(null);

        // Create the Patient, which fails.
        PatientDTO patientDTO = patientMapper.toDto(patient);

        restPatientMockMvc.perform(post("/api/patients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(patientDTO)))
            .andExpect(status().isBadRequest());

        List<Patient> patientList = patientRepository.findAll();
        assertThat(patientList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLastEventOcurredIsRequired() throws Exception {
        int databaseSizeBeforeTest = patientRepository.findAll().size();
        // set the field null
        patient.setLastEventOcurred(null);

        // Create the Patient, which fails.
        PatientDTO patientDTO = patientMapper.toDto(patient);

        restPatientMockMvc.perform(post("/api/patients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(patientDTO)))
            .andExpect(status().isBadRequest());

        List<Patient> patientList = patientRepository.findAll();
        assertThat(patientList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPatients() throws Exception {
        // Initialize the database
        patientRepository.saveAndFlush(patient);

        // Get all the patientList
        restPatientMockMvc.perform(get("/api/patients?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(patient.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].age").value(hasItem(DEFAULT_AGE)))
            .andExpect(jsonPath("$.[*].sex").value(hasItem(DEFAULT_SEX.toString())))
            .andExpect(jsonPath("$.[*].ocupation").value(hasItem(DEFAULT_OCUPATION.toString())))
            .andExpect(jsonPath("$.[*].lastEventOcurred").value(hasItem(DEFAULT_LAST_EVENT_OCURRED)))
            .andExpect(jsonPath("$.[*].deceased").value(hasItem(DEFAULT_DECEASED.booleanValue())))
            .andExpect(jsonPath("$.[*].abandonment").value(hasItem(DEFAULT_ABANDONMENT.booleanValue())))
            .andExpect(jsonPath("$.[*].abandonmentMedicCause").value(hasItem(DEFAULT_ABANDONMENT_MEDIC_CAUSE.booleanValue())))
            .andExpect(jsonPath("$.[*].rehabStatus").value(hasItem(DEFAULT_REHAB_STATUS)))
            .andExpect(jsonPath("$.[*].sessionNumber").value(hasItem(DEFAULT_SESSION_NUMBER)))
            .andExpect(jsonPath("$.[*].deleted").value(hasItem(DEFAULT_DELETED.booleanValue())))
            .andExpect(jsonPath("$.[*].scholarship").value(hasItem(DEFAULT_SCHOLARSHIP.toString())));
    }
    
    @Test
    @Transactional
    public void getPatient() throws Exception {
        // Initialize the database
        patientRepository.saveAndFlush(patient);

        // Get the patient
        restPatientMockMvc.perform(get("/api/patients/{id}", patient.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(patient.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.age").value(DEFAULT_AGE))
            .andExpect(jsonPath("$.sex").value(DEFAULT_SEX.toString()))
            .andExpect(jsonPath("$.ocupation").value(DEFAULT_OCUPATION.toString()))
            .andExpect(jsonPath("$.lastEventOcurred").value(DEFAULT_LAST_EVENT_OCURRED))
            .andExpect(jsonPath("$.deceased").value(DEFAULT_DECEASED.booleanValue()))
            .andExpect(jsonPath("$.abandonment").value(DEFAULT_ABANDONMENT.booleanValue()))
            .andExpect(jsonPath("$.abandonmentMedicCause").value(DEFAULT_ABANDONMENT_MEDIC_CAUSE.booleanValue()))
            .andExpect(jsonPath("$.rehabStatus").value(DEFAULT_REHAB_STATUS))
            .andExpect(jsonPath("$.sessionNumber").value(DEFAULT_SESSION_NUMBER))
            .andExpect(jsonPath("$.deleted").value(DEFAULT_DELETED.booleanValue()))
            .andExpect(jsonPath("$.scholarship").value(DEFAULT_SCHOLARSHIP.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPatient() throws Exception {
        // Get the patient
        restPatientMockMvc.perform(get("/api/patients/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePatient() throws Exception {
        // Initialize the database
        patientRepository.saveAndFlush(patient);

        int databaseSizeBeforeUpdate = patientRepository.findAll().size();

        // Update the patient
        Patient updatedPatient = patientRepository.findById(patient.getId()).get();
        // Disconnect from session so that the updates on updatedPatient are not directly saved in db
        em.detach(updatedPatient);
        updatedPatient
            .code(UPDATED_CODE)
            .age(UPDATED_AGE)
            .sex(UPDATED_SEX)
            .ocupation(UPDATED_OCUPATION)
            .lastEventOcurred(UPDATED_LAST_EVENT_OCURRED)
            .deceased(UPDATED_DECEASED)
            .abandonment(UPDATED_ABANDONMENT)
            .abandonmentMedicCause(UPDATED_ABANDONMENT_MEDIC_CAUSE)
            .rehabStatus(UPDATED_REHAB_STATUS)
            .sessionNumber(UPDATED_SESSION_NUMBER)
            .deleted(UPDATED_DELETED)
            .scholarship(UPDATED_SCHOLARSHIP);
        PatientDTO patientDTO = patientMapper.toDto(updatedPatient);

        restPatientMockMvc.perform(put("/api/patients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(patientDTO)))
            .andExpect(status().isOk());

        // Validate the Patient in the database
        List<Patient> patientList = patientRepository.findAll();
        assertThat(patientList).hasSize(databaseSizeBeforeUpdate);
        Patient testPatient = patientList.get(patientList.size() - 1);
        assertThat(testPatient.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testPatient.getAge()).isEqualTo(UPDATED_AGE);
        assertThat(testPatient.getSex()).isEqualTo(UPDATED_SEX);
        assertThat(testPatient.getOcupation()).isEqualTo(UPDATED_OCUPATION);
        assertThat(testPatient.getLastEventOcurred()).isEqualTo(UPDATED_LAST_EVENT_OCURRED);
        assertThat(testPatient.isDeceased()).isEqualTo(UPDATED_DECEASED);
        assertThat(testPatient.isAbandonment()).isEqualTo(UPDATED_ABANDONMENT);
        assertThat(testPatient.isAbandonmentMedicCause()).isEqualTo(UPDATED_ABANDONMENT_MEDIC_CAUSE);
        assertThat(testPatient.getRehabStatus()).isEqualTo(UPDATED_REHAB_STATUS);
        assertThat(testPatient.getSessionNumber()).isEqualTo(UPDATED_SESSION_NUMBER);
        assertThat(testPatient.isDeleted()).isEqualTo(UPDATED_DELETED);
        assertThat(testPatient.getScholarship()).isEqualTo(UPDATED_SCHOLARSHIP);
    }

    @Test
    @Transactional
    public void updateNonExistingPatient() throws Exception {
        int databaseSizeBeforeUpdate = patientRepository.findAll().size();

        // Create the Patient
        PatientDTO patientDTO = patientMapper.toDto(patient);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPatientMockMvc.perform(put("/api/patients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(patientDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Patient in the database
        List<Patient> patientList = patientRepository.findAll();
        assertThat(patientList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePatient() throws Exception {
        // Initialize the database
        patientRepository.saveAndFlush(patient);

        int databaseSizeBeforeDelete = patientRepository.findAll().size();

        // Delete the patient
        restPatientMockMvc.perform(delete("/api/patients/{id}", patient.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Patient> patientList = patientRepository.findAll();
        assertThat(patientList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Patient.class);
        Patient patient1 = new Patient();
        patient1.setId(1L);
        Patient patient2 = new Patient();
        patient2.setId(patient1.getId());
        assertThat(patient1).isEqualTo(patient2);
        patient2.setId(2L);
        assertThat(patient1).isNotEqualTo(patient2);
        patient1.setId(null);
        assertThat(patient1).isNotEqualTo(patient2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PatientDTO.class);
        PatientDTO patientDTO1 = new PatientDTO();
        patientDTO1.setId(1L);
        PatientDTO patientDTO2 = new PatientDTO();
        assertThat(patientDTO1).isNotEqualTo(patientDTO2);
        patientDTO2.setId(patientDTO1.getId());
        assertThat(patientDTO1).isEqualTo(patientDTO2);
        patientDTO2.setId(2L);
        assertThat(patientDTO1).isNotEqualTo(patientDTO2);
        patientDTO1.setId(null);
        assertThat(patientDTO1).isNotEqualTo(patientDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(patientMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(patientMapper.fromId(null)).isNull();
    }
}
