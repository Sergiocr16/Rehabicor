package com.aditum.rehabicor.web.rest;

import com.aditum.rehabicor.RehabicorApp;

import com.aditum.rehabicor.domain.NonSpecificPain;
import com.aditum.rehabicor.repository.NonSpecificPainRepository;
import com.aditum.rehabicor.service.NonSpecificPainService;
import com.aditum.rehabicor.service.dto.NonSpecificPainDTO;
import com.aditum.rehabicor.service.mapper.NonSpecificPainMapper;
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
 * Test class for the NonSpecificPainResource REST controller.
 *
 * @see NonSpecificPainResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RehabicorApp.class)
public class NonSpecificPainResourceIntTest {

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_DELETED = false;
    private static final Boolean UPDATED_DELETED = true;

    @Autowired
    private NonSpecificPainRepository nonSpecificPainRepository;

    @Autowired
    private NonSpecificPainMapper nonSpecificPainMapper;

    @Autowired
    private NonSpecificPainService nonSpecificPainService;

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

    private MockMvc restNonSpecificPainMockMvc;

    private NonSpecificPain nonSpecificPain;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final NonSpecificPainResource nonSpecificPainResource = new NonSpecificPainResource(nonSpecificPainService);
        this.restNonSpecificPainMockMvc = MockMvcBuilders.standaloneSetup(nonSpecificPainResource)
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
    public static NonSpecificPain createEntity(EntityManager em) {
        NonSpecificPain nonSpecificPain = new NonSpecificPain()
            .description(DEFAULT_DESCRIPTION)
            .code(DEFAULT_CODE)
            .deleted(DEFAULT_DELETED);
        return nonSpecificPain;
    }

    @Before
    public void initTest() {
        nonSpecificPain = createEntity(em);
    }

    @Test
    @Transactional
    public void createNonSpecificPain() throws Exception {
        int databaseSizeBeforeCreate = nonSpecificPainRepository.findAll().size();

        // Create the NonSpecificPain
        NonSpecificPainDTO nonSpecificPainDTO = nonSpecificPainMapper.toDto(nonSpecificPain);
        restNonSpecificPainMockMvc.perform(post("/api/non-specific-pains")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nonSpecificPainDTO)))
            .andExpect(status().isCreated());

        // Validate the NonSpecificPain in the database
        List<NonSpecificPain> nonSpecificPainList = nonSpecificPainRepository.findAll();
        assertThat(nonSpecificPainList).hasSize(databaseSizeBeforeCreate + 1);
        NonSpecificPain testNonSpecificPain = nonSpecificPainList.get(nonSpecificPainList.size() - 1);
        assertThat(testNonSpecificPain.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testNonSpecificPain.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testNonSpecificPain.isDeleted()).isEqualTo(DEFAULT_DELETED);
    }

    @Test
    @Transactional
    public void createNonSpecificPainWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = nonSpecificPainRepository.findAll().size();

        // Create the NonSpecificPain with an existing ID
        nonSpecificPain.setId(1L);
        NonSpecificPainDTO nonSpecificPainDTO = nonSpecificPainMapper.toDto(nonSpecificPain);

        // An entity with an existing ID cannot be created, so this API call must fail
        restNonSpecificPainMockMvc.perform(post("/api/non-specific-pains")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nonSpecificPainDTO)))
            .andExpect(status().isBadRequest());

        // Validate the NonSpecificPain in the database
        List<NonSpecificPain> nonSpecificPainList = nonSpecificPainRepository.findAll();
        assertThat(nonSpecificPainList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = nonSpecificPainRepository.findAll().size();
        // set the field null
        nonSpecificPain.setDescription(null);

        // Create the NonSpecificPain, which fails.
        NonSpecificPainDTO nonSpecificPainDTO = nonSpecificPainMapper.toDto(nonSpecificPain);

        restNonSpecificPainMockMvc.perform(post("/api/non-specific-pains")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nonSpecificPainDTO)))
            .andExpect(status().isBadRequest());

        List<NonSpecificPain> nonSpecificPainList = nonSpecificPainRepository.findAll();
        assertThat(nonSpecificPainList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllNonSpecificPains() throws Exception {
        // Initialize the database
        nonSpecificPainRepository.saveAndFlush(nonSpecificPain);

        // Get all the nonSpecificPainList
        restNonSpecificPainMockMvc.perform(get("/api/non-specific-pains?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(nonSpecificPain.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].deleted").value(hasItem(DEFAULT_DELETED.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getNonSpecificPain() throws Exception {
        // Initialize the database
        nonSpecificPainRepository.saveAndFlush(nonSpecificPain);

        // Get the nonSpecificPain
        restNonSpecificPainMockMvc.perform(get("/api/non-specific-pains/{id}", nonSpecificPain.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(nonSpecificPain.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.deleted").value(DEFAULT_DELETED.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingNonSpecificPain() throws Exception {
        // Get the nonSpecificPain
        restNonSpecificPainMockMvc.perform(get("/api/non-specific-pains/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateNonSpecificPain() throws Exception {
        // Initialize the database
        nonSpecificPainRepository.saveAndFlush(nonSpecificPain);

        int databaseSizeBeforeUpdate = nonSpecificPainRepository.findAll().size();

        // Update the nonSpecificPain
        NonSpecificPain updatedNonSpecificPain = nonSpecificPainRepository.findById(nonSpecificPain.getId()).get();
        // Disconnect from session so that the updates on updatedNonSpecificPain are not directly saved in db
        em.detach(updatedNonSpecificPain);
        updatedNonSpecificPain
            .description(UPDATED_DESCRIPTION)
            .code(UPDATED_CODE)
            .deleted(UPDATED_DELETED);
        NonSpecificPainDTO nonSpecificPainDTO = nonSpecificPainMapper.toDto(updatedNonSpecificPain);

        restNonSpecificPainMockMvc.perform(put("/api/non-specific-pains")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nonSpecificPainDTO)))
            .andExpect(status().isOk());

        // Validate the NonSpecificPain in the database
        List<NonSpecificPain> nonSpecificPainList = nonSpecificPainRepository.findAll();
        assertThat(nonSpecificPainList).hasSize(databaseSizeBeforeUpdate);
        NonSpecificPain testNonSpecificPain = nonSpecificPainList.get(nonSpecificPainList.size() - 1);
        assertThat(testNonSpecificPain.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testNonSpecificPain.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testNonSpecificPain.isDeleted()).isEqualTo(UPDATED_DELETED);
    }

    @Test
    @Transactional
    public void updateNonExistingNonSpecificPain() throws Exception {
        int databaseSizeBeforeUpdate = nonSpecificPainRepository.findAll().size();

        // Create the NonSpecificPain
        NonSpecificPainDTO nonSpecificPainDTO = nonSpecificPainMapper.toDto(nonSpecificPain);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNonSpecificPainMockMvc.perform(put("/api/non-specific-pains")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nonSpecificPainDTO)))
            .andExpect(status().isBadRequest());

        // Validate the NonSpecificPain in the database
        List<NonSpecificPain> nonSpecificPainList = nonSpecificPainRepository.findAll();
        assertThat(nonSpecificPainList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteNonSpecificPain() throws Exception {
        // Initialize the database
        nonSpecificPainRepository.saveAndFlush(nonSpecificPain);

        int databaseSizeBeforeDelete = nonSpecificPainRepository.findAll().size();

        // Delete the nonSpecificPain
        restNonSpecificPainMockMvc.perform(delete("/api/non-specific-pains/{id}", nonSpecificPain.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<NonSpecificPain> nonSpecificPainList = nonSpecificPainRepository.findAll();
        assertThat(nonSpecificPainList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(NonSpecificPain.class);
        NonSpecificPain nonSpecificPain1 = new NonSpecificPain();
        nonSpecificPain1.setId(1L);
        NonSpecificPain nonSpecificPain2 = new NonSpecificPain();
        nonSpecificPain2.setId(nonSpecificPain1.getId());
        assertThat(nonSpecificPain1).isEqualTo(nonSpecificPain2);
        nonSpecificPain2.setId(2L);
        assertThat(nonSpecificPain1).isNotEqualTo(nonSpecificPain2);
        nonSpecificPain1.setId(null);
        assertThat(nonSpecificPain1).isNotEqualTo(nonSpecificPain2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(NonSpecificPainDTO.class);
        NonSpecificPainDTO nonSpecificPainDTO1 = new NonSpecificPainDTO();
        nonSpecificPainDTO1.setId(1L);
        NonSpecificPainDTO nonSpecificPainDTO2 = new NonSpecificPainDTO();
        assertThat(nonSpecificPainDTO1).isNotEqualTo(nonSpecificPainDTO2);
        nonSpecificPainDTO2.setId(nonSpecificPainDTO1.getId());
        assertThat(nonSpecificPainDTO1).isEqualTo(nonSpecificPainDTO2);
        nonSpecificPainDTO2.setId(2L);
        assertThat(nonSpecificPainDTO1).isNotEqualTo(nonSpecificPainDTO2);
        nonSpecificPainDTO1.setId(null);
        assertThat(nonSpecificPainDTO1).isNotEqualTo(nonSpecificPainDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(nonSpecificPainMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(nonSpecificPainMapper.fromId(null)).isNull();
    }
}
