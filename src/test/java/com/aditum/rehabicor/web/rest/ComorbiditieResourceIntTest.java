package com.aditum.rehabicor.web.rest;

import com.aditum.rehabicor.RehabicorApp;

import com.aditum.rehabicor.domain.Comorbiditie;
import com.aditum.rehabicor.repository.ComorbiditieRepository;
import com.aditum.rehabicor.service.ComorbiditieService;
import com.aditum.rehabicor.service.dto.ComorbiditieDTO;
import com.aditum.rehabicor.service.mapper.ComorbiditieMapper;
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
 * Test class for the ComorbiditieResource REST controller.
 *
 * @see ComorbiditieResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RehabicorApp.class)
public class ComorbiditieResourceIntTest {

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_DELETED = false;
    private static final Boolean UPDATED_DELETED = true;

    @Autowired
    private ComorbiditieRepository comorbiditieRepository;

    @Autowired
    private ComorbiditieMapper comorbiditieMapper;

    @Autowired
    private ComorbiditieService comorbiditieService;

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

    private MockMvc restComorbiditieMockMvc;

    private Comorbiditie comorbiditie;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ComorbiditieResource comorbiditieResource = new ComorbiditieResource(comorbiditieService);
        this.restComorbiditieMockMvc = MockMvcBuilders.standaloneSetup(comorbiditieResource)
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
    public static Comorbiditie createEntity(EntityManager em) {
        Comorbiditie comorbiditie = new Comorbiditie()
            .description(DEFAULT_DESCRIPTION)
            .deleted(DEFAULT_DELETED);
        return comorbiditie;
    }

    @Before
    public void initTest() {
        comorbiditie = createEntity(em);
    }

    @Test
    @Transactional
    public void createComorbiditie() throws Exception {
        int databaseSizeBeforeCreate = comorbiditieRepository.findAll().size();

        // Create the Comorbiditie
        ComorbiditieDTO comorbiditieDTO = comorbiditieMapper.toDto(comorbiditie);
        restComorbiditieMockMvc.perform(post("/api/comorbidities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(comorbiditieDTO)))
            .andExpect(status().isCreated());

        // Validate the Comorbiditie in the database
        List<Comorbiditie> comorbiditieList = comorbiditieRepository.findAll();
        assertThat(comorbiditieList).hasSize(databaseSizeBeforeCreate + 1);
        Comorbiditie testComorbiditie = comorbiditieList.get(comorbiditieList.size() - 1);
        assertThat(testComorbiditie.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testComorbiditie.isDeleted()).isEqualTo(DEFAULT_DELETED);
    }

    @Test
    @Transactional
    public void createComorbiditieWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = comorbiditieRepository.findAll().size();

        // Create the Comorbiditie with an existing ID
        comorbiditie.setId(1L);
        ComorbiditieDTO comorbiditieDTO = comorbiditieMapper.toDto(comorbiditie);

        // An entity with an existing ID cannot be created, so this API call must fail
        restComorbiditieMockMvc.perform(post("/api/comorbidities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(comorbiditieDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Comorbiditie in the database
        List<Comorbiditie> comorbiditieList = comorbiditieRepository.findAll();
        assertThat(comorbiditieList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = comorbiditieRepository.findAll().size();
        // set the field null
        comorbiditie.setDescription(null);

        // Create the Comorbiditie, which fails.
        ComorbiditieDTO comorbiditieDTO = comorbiditieMapper.toDto(comorbiditie);

        restComorbiditieMockMvc.perform(post("/api/comorbidities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(comorbiditieDTO)))
            .andExpect(status().isBadRequest());

        List<Comorbiditie> comorbiditieList = comorbiditieRepository.findAll();
        assertThat(comorbiditieList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllComorbidities() throws Exception {
        // Initialize the database
        comorbiditieRepository.saveAndFlush(comorbiditie);

        // Get all the comorbiditieList
        restComorbiditieMockMvc.perform(get("/api/comorbidities?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(comorbiditie.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].deleted").value(hasItem(DEFAULT_DELETED.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getComorbiditie() throws Exception {
        // Initialize the database
        comorbiditieRepository.saveAndFlush(comorbiditie);

        // Get the comorbiditie
        restComorbiditieMockMvc.perform(get("/api/comorbidities/{id}", comorbiditie.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(comorbiditie.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.deleted").value(DEFAULT_DELETED.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingComorbiditie() throws Exception {
        // Get the comorbiditie
        restComorbiditieMockMvc.perform(get("/api/comorbidities/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateComorbiditie() throws Exception {
        // Initialize the database
        comorbiditieRepository.saveAndFlush(comorbiditie);

        int databaseSizeBeforeUpdate = comorbiditieRepository.findAll().size();

        // Update the comorbiditie
        Comorbiditie updatedComorbiditie = comorbiditieRepository.findById(comorbiditie.getId()).get();
        // Disconnect from session so that the updates on updatedComorbiditie are not directly saved in db
        em.detach(updatedComorbiditie);
        updatedComorbiditie
            .description(UPDATED_DESCRIPTION)
            .deleted(UPDATED_DELETED);
        ComorbiditieDTO comorbiditieDTO = comorbiditieMapper.toDto(updatedComorbiditie);

        restComorbiditieMockMvc.perform(put("/api/comorbidities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(comorbiditieDTO)))
            .andExpect(status().isOk());

        // Validate the Comorbiditie in the database
        List<Comorbiditie> comorbiditieList = comorbiditieRepository.findAll();
        assertThat(comorbiditieList).hasSize(databaseSizeBeforeUpdate);
        Comorbiditie testComorbiditie = comorbiditieList.get(comorbiditieList.size() - 1);
        assertThat(testComorbiditie.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testComorbiditie.isDeleted()).isEqualTo(UPDATED_DELETED);
    }

    @Test
    @Transactional
    public void updateNonExistingComorbiditie() throws Exception {
        int databaseSizeBeforeUpdate = comorbiditieRepository.findAll().size();

        // Create the Comorbiditie
        ComorbiditieDTO comorbiditieDTO = comorbiditieMapper.toDto(comorbiditie);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restComorbiditieMockMvc.perform(put("/api/comorbidities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(comorbiditieDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Comorbiditie in the database
        List<Comorbiditie> comorbiditieList = comorbiditieRepository.findAll();
        assertThat(comorbiditieList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteComorbiditie() throws Exception {
        // Initialize the database
        comorbiditieRepository.saveAndFlush(comorbiditie);

        int databaseSizeBeforeDelete = comorbiditieRepository.findAll().size();

        // Delete the comorbiditie
        restComorbiditieMockMvc.perform(delete("/api/comorbidities/{id}", comorbiditie.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Comorbiditie> comorbiditieList = comorbiditieRepository.findAll();
        assertThat(comorbiditieList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Comorbiditie.class);
        Comorbiditie comorbiditie1 = new Comorbiditie();
        comorbiditie1.setId(1L);
        Comorbiditie comorbiditie2 = new Comorbiditie();
        comorbiditie2.setId(comorbiditie1.getId());
        assertThat(comorbiditie1).isEqualTo(comorbiditie2);
        comorbiditie2.setId(2L);
        assertThat(comorbiditie1).isNotEqualTo(comorbiditie2);
        comorbiditie1.setId(null);
        assertThat(comorbiditie1).isNotEqualTo(comorbiditie2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ComorbiditieDTO.class);
        ComorbiditieDTO comorbiditieDTO1 = new ComorbiditieDTO();
        comorbiditieDTO1.setId(1L);
        ComorbiditieDTO comorbiditieDTO2 = new ComorbiditieDTO();
        assertThat(comorbiditieDTO1).isNotEqualTo(comorbiditieDTO2);
        comorbiditieDTO2.setId(comorbiditieDTO1.getId());
        assertThat(comorbiditieDTO1).isEqualTo(comorbiditieDTO2);
        comorbiditieDTO2.setId(2L);
        assertThat(comorbiditieDTO1).isNotEqualTo(comorbiditieDTO2);
        comorbiditieDTO1.setId(null);
        assertThat(comorbiditieDTO1).isNotEqualTo(comorbiditieDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(comorbiditieMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(comorbiditieMapper.fromId(null)).isNull();
    }
}
