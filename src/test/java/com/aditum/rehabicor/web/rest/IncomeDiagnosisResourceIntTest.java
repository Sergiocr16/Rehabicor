package com.aditum.rehabicor.web.rest;

import com.aditum.rehabicor.RehabicorApp;

import com.aditum.rehabicor.domain.IncomeDiagnosis;
import com.aditum.rehabicor.repository.IncomeDiagnosisRepository;
import com.aditum.rehabicor.service.IncomeDiagnosisService;
import com.aditum.rehabicor.service.dto.IncomeDiagnosisDTO;
import com.aditum.rehabicor.service.mapper.IncomeDiagnosisMapper;
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
 * Test class for the IncomeDiagnosisResource REST controller.
 *
 * @see IncomeDiagnosisResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RehabicorApp.class)
public class IncomeDiagnosisResourceIntTest {

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_DELETED = false;
    private static final Boolean UPDATED_DELETED = true;

    @Autowired
    private IncomeDiagnosisRepository incomeDiagnosisRepository;

    @Autowired
    private IncomeDiagnosisMapper incomeDiagnosisMapper;

    @Autowired
    private IncomeDiagnosisService incomeDiagnosisService;

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

    private MockMvc restIncomeDiagnosisMockMvc;

    private IncomeDiagnosis incomeDiagnosis;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final IncomeDiagnosisResource incomeDiagnosisResource = new IncomeDiagnosisResource(incomeDiagnosisService);
        this.restIncomeDiagnosisMockMvc = MockMvcBuilders.standaloneSetup(incomeDiagnosisResource)
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
    public static IncomeDiagnosis createEntity(EntityManager em) {
        IncomeDiagnosis incomeDiagnosis = new IncomeDiagnosis()
            .description(DEFAULT_DESCRIPTION)
            .deleted(DEFAULT_DELETED);
        return incomeDiagnosis;
    }

    @Before
    public void initTest() {
        incomeDiagnosis = createEntity(em);
    }

    @Test
    @Transactional
    public void createIncomeDiagnosis() throws Exception {
        int databaseSizeBeforeCreate = incomeDiagnosisRepository.findAll().size();

        // Create the IncomeDiagnosis
        IncomeDiagnosisDTO incomeDiagnosisDTO = incomeDiagnosisMapper.toDto(incomeDiagnosis);
        restIncomeDiagnosisMockMvc.perform(post("/api/income-diagnoses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(incomeDiagnosisDTO)))
            .andExpect(status().isCreated());

        // Validate the IncomeDiagnosis in the database
        List<IncomeDiagnosis> incomeDiagnosisList = incomeDiagnosisRepository.findAll();
        assertThat(incomeDiagnosisList).hasSize(databaseSizeBeforeCreate + 1);
        IncomeDiagnosis testIncomeDiagnosis = incomeDiagnosisList.get(incomeDiagnosisList.size() - 1);
        assertThat(testIncomeDiagnosis.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testIncomeDiagnosis.isDeleted()).isEqualTo(DEFAULT_DELETED);
    }

    @Test
    @Transactional
    public void createIncomeDiagnosisWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = incomeDiagnosisRepository.findAll().size();

        // Create the IncomeDiagnosis with an existing ID
        incomeDiagnosis.setId(1L);
        IncomeDiagnosisDTO incomeDiagnosisDTO = incomeDiagnosisMapper.toDto(incomeDiagnosis);

        // An entity with an existing ID cannot be created, so this API call must fail
        restIncomeDiagnosisMockMvc.perform(post("/api/income-diagnoses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(incomeDiagnosisDTO)))
            .andExpect(status().isBadRequest());

        // Validate the IncomeDiagnosis in the database
        List<IncomeDiagnosis> incomeDiagnosisList = incomeDiagnosisRepository.findAll();
        assertThat(incomeDiagnosisList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = incomeDiagnosisRepository.findAll().size();
        // set the field null
        incomeDiagnosis.setDescription(null);

        // Create the IncomeDiagnosis, which fails.
        IncomeDiagnosisDTO incomeDiagnosisDTO = incomeDiagnosisMapper.toDto(incomeDiagnosis);

        restIncomeDiagnosisMockMvc.perform(post("/api/income-diagnoses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(incomeDiagnosisDTO)))
            .andExpect(status().isBadRequest());

        List<IncomeDiagnosis> incomeDiagnosisList = incomeDiagnosisRepository.findAll();
        assertThat(incomeDiagnosisList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllIncomeDiagnoses() throws Exception {
        // Initialize the database
        incomeDiagnosisRepository.saveAndFlush(incomeDiagnosis);

        // Get all the incomeDiagnosisList
        restIncomeDiagnosisMockMvc.perform(get("/api/income-diagnoses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(incomeDiagnosis.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].deleted").value(hasItem(DEFAULT_DELETED.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getIncomeDiagnosis() throws Exception {
        // Initialize the database
        incomeDiagnosisRepository.saveAndFlush(incomeDiagnosis);

        // Get the incomeDiagnosis
        restIncomeDiagnosisMockMvc.perform(get("/api/income-diagnoses/{id}", incomeDiagnosis.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(incomeDiagnosis.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.deleted").value(DEFAULT_DELETED.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingIncomeDiagnosis() throws Exception {
        // Get the incomeDiagnosis
        restIncomeDiagnosisMockMvc.perform(get("/api/income-diagnoses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateIncomeDiagnosis() throws Exception {
        // Initialize the database
        incomeDiagnosisRepository.saveAndFlush(incomeDiagnosis);

        int databaseSizeBeforeUpdate = incomeDiagnosisRepository.findAll().size();

        // Update the incomeDiagnosis
        IncomeDiagnosis updatedIncomeDiagnosis = incomeDiagnosisRepository.findById(incomeDiagnosis.getId()).get();
        // Disconnect from session so that the updates on updatedIncomeDiagnosis are not directly saved in db
        em.detach(updatedIncomeDiagnosis);
        updatedIncomeDiagnosis
            .description(UPDATED_DESCRIPTION)
            .deleted(UPDATED_DELETED);
        IncomeDiagnosisDTO incomeDiagnosisDTO = incomeDiagnosisMapper.toDto(updatedIncomeDiagnosis);

        restIncomeDiagnosisMockMvc.perform(put("/api/income-diagnoses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(incomeDiagnosisDTO)))
            .andExpect(status().isOk());

        // Validate the IncomeDiagnosis in the database
        List<IncomeDiagnosis> incomeDiagnosisList = incomeDiagnosisRepository.findAll();
        assertThat(incomeDiagnosisList).hasSize(databaseSizeBeforeUpdate);
        IncomeDiagnosis testIncomeDiagnosis = incomeDiagnosisList.get(incomeDiagnosisList.size() - 1);
        assertThat(testIncomeDiagnosis.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testIncomeDiagnosis.isDeleted()).isEqualTo(UPDATED_DELETED);
    }

    @Test
    @Transactional
    public void updateNonExistingIncomeDiagnosis() throws Exception {
        int databaseSizeBeforeUpdate = incomeDiagnosisRepository.findAll().size();

        // Create the IncomeDiagnosis
        IncomeDiagnosisDTO incomeDiagnosisDTO = incomeDiagnosisMapper.toDto(incomeDiagnosis);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIncomeDiagnosisMockMvc.perform(put("/api/income-diagnoses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(incomeDiagnosisDTO)))
            .andExpect(status().isBadRequest());

        // Validate the IncomeDiagnosis in the database
        List<IncomeDiagnosis> incomeDiagnosisList = incomeDiagnosisRepository.findAll();
        assertThat(incomeDiagnosisList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteIncomeDiagnosis() throws Exception {
        // Initialize the database
        incomeDiagnosisRepository.saveAndFlush(incomeDiagnosis);

        int databaseSizeBeforeDelete = incomeDiagnosisRepository.findAll().size();

        // Delete the incomeDiagnosis
        restIncomeDiagnosisMockMvc.perform(delete("/api/income-diagnoses/{id}", incomeDiagnosis.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<IncomeDiagnosis> incomeDiagnosisList = incomeDiagnosisRepository.findAll();
        assertThat(incomeDiagnosisList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(IncomeDiagnosis.class);
        IncomeDiagnosis incomeDiagnosis1 = new IncomeDiagnosis();
        incomeDiagnosis1.setId(1L);
        IncomeDiagnosis incomeDiagnosis2 = new IncomeDiagnosis();
        incomeDiagnosis2.setId(incomeDiagnosis1.getId());
        assertThat(incomeDiagnosis1).isEqualTo(incomeDiagnosis2);
        incomeDiagnosis2.setId(2L);
        assertThat(incomeDiagnosis1).isNotEqualTo(incomeDiagnosis2);
        incomeDiagnosis1.setId(null);
        assertThat(incomeDiagnosis1).isNotEqualTo(incomeDiagnosis2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(IncomeDiagnosisDTO.class);
        IncomeDiagnosisDTO incomeDiagnosisDTO1 = new IncomeDiagnosisDTO();
        incomeDiagnosisDTO1.setId(1L);
        IncomeDiagnosisDTO incomeDiagnosisDTO2 = new IncomeDiagnosisDTO();
        assertThat(incomeDiagnosisDTO1).isNotEqualTo(incomeDiagnosisDTO2);
        incomeDiagnosisDTO2.setId(incomeDiagnosisDTO1.getId());
        assertThat(incomeDiagnosisDTO1).isEqualTo(incomeDiagnosisDTO2);
        incomeDiagnosisDTO2.setId(2L);
        assertThat(incomeDiagnosisDTO1).isNotEqualTo(incomeDiagnosisDTO2);
        incomeDiagnosisDTO1.setId(null);
        assertThat(incomeDiagnosisDTO1).isNotEqualTo(incomeDiagnosisDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(incomeDiagnosisMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(incomeDiagnosisMapper.fromId(null)).isNull();
    }
}
