package com.aditum.rehabicor.web.rest;

import com.aditum.rehabicor.RehabicorApp;

import com.aditum.rehabicor.domain.RehabilitationGroup;
import com.aditum.rehabicor.repository.RehabilitationGroupRepository;
import com.aditum.rehabicor.service.RehabilitationGroupService;
import com.aditum.rehabicor.service.dto.RehabilitationGroupDTO;
import com.aditum.rehabicor.service.mapper.RehabilitationGroupMapper;
import com.aditum.rehabicor.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
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
import java.util.ArrayList;
import java.util.List;


import static com.aditum.rehabicor.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the RehabilitationGroupResource REST controller.
 *
 * @see RehabilitationGroupResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RehabicorApp.class)
public class RehabilitationGroupResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATION_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATION_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_PROGRAM_STATUS = 1;
    private static final Integer UPDATED_PROGRAM_STATUS = 2;

    private static final Boolean DEFAULT_DELETED = false;
    private static final Boolean UPDATED_DELETED = true;

    @Autowired
    private RehabilitationGroupRepository rehabilitationGroupRepository;

    @Mock
    private RehabilitationGroupRepository rehabilitationGroupRepositoryMock;

    @Autowired
    private RehabilitationGroupMapper rehabilitationGroupMapper;

    @Mock
    private RehabilitationGroupService rehabilitationGroupServiceMock;

    @Autowired
    private RehabilitationGroupService rehabilitationGroupService;

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

    private MockMvc restRehabilitationGroupMockMvc;

    private RehabilitationGroup rehabilitationGroup;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RehabilitationGroupResource rehabilitationGroupResource = new RehabilitationGroupResource(rehabilitationGroupService);
        this.restRehabilitationGroupMockMvc = MockMvcBuilders.standaloneSetup(rehabilitationGroupResource)
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
    public static RehabilitationGroup createEntity(EntityManager em) {
        RehabilitationGroup rehabilitationGroup = new RehabilitationGroup()
            .name(DEFAULT_NAME)
            .creationDate(DEFAULT_CREATION_DATE)
            .programStatus(DEFAULT_PROGRAM_STATUS)
            .deleted(DEFAULT_DELETED);
        return rehabilitationGroup;
    }

    @Before
    public void initTest() {
        rehabilitationGroup = createEntity(em);
    }

    @Test
    @Transactional
    public void createRehabilitationGroup() throws Exception {
        int databaseSizeBeforeCreate = rehabilitationGroupRepository.findAll().size();

        // Create the RehabilitationGroup
        RehabilitationGroupDTO rehabilitationGroupDTO = rehabilitationGroupMapper.toDto(rehabilitationGroup);
        restRehabilitationGroupMockMvc.perform(post("/api/rehabilitation-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rehabilitationGroupDTO)))
            .andExpect(status().isCreated());

        // Validate the RehabilitationGroup in the database
        List<RehabilitationGroup> rehabilitationGroupList = rehabilitationGroupRepository.findAll();
        assertThat(rehabilitationGroupList).hasSize(databaseSizeBeforeCreate + 1);
        RehabilitationGroup testRehabilitationGroup = rehabilitationGroupList.get(rehabilitationGroupList.size() - 1);
        assertThat(testRehabilitationGroup.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testRehabilitationGroup.getCreationDate()).isEqualTo(DEFAULT_CREATION_DATE);
        assertThat(testRehabilitationGroup.getProgramStatus()).isEqualTo(DEFAULT_PROGRAM_STATUS);
        assertThat(testRehabilitationGroup.isDeleted()).isEqualTo(DEFAULT_DELETED);
    }

    @Test
    @Transactional
    public void createRehabilitationGroupWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = rehabilitationGroupRepository.findAll().size();

        // Create the RehabilitationGroup with an existing ID
        rehabilitationGroup.setId(1L);
        RehabilitationGroupDTO rehabilitationGroupDTO = rehabilitationGroupMapper.toDto(rehabilitationGroup);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRehabilitationGroupMockMvc.perform(post("/api/rehabilitation-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rehabilitationGroupDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RehabilitationGroup in the database
        List<RehabilitationGroup> rehabilitationGroupList = rehabilitationGroupRepository.findAll();
        assertThat(rehabilitationGroupList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = rehabilitationGroupRepository.findAll().size();
        // set the field null
        rehabilitationGroup.setName(null);

        // Create the RehabilitationGroup, which fails.
        RehabilitationGroupDTO rehabilitationGroupDTO = rehabilitationGroupMapper.toDto(rehabilitationGroup);

        restRehabilitationGroupMockMvc.perform(post("/api/rehabilitation-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rehabilitationGroupDTO)))
            .andExpect(status().isBadRequest());

        List<RehabilitationGroup> rehabilitationGroupList = rehabilitationGroupRepository.findAll();
        assertThat(rehabilitationGroupList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRehabilitationGroups() throws Exception {
        // Initialize the database
        rehabilitationGroupRepository.saveAndFlush(rehabilitationGroup);

        // Get all the rehabilitationGroupList
        restRehabilitationGroupMockMvc.perform(get("/api/rehabilitation-groups?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rehabilitationGroup.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(DEFAULT_CREATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].programStatus").value(hasItem(DEFAULT_PROGRAM_STATUS)))
            .andExpect(jsonPath("$.[*].deleted").value(hasItem(DEFAULT_DELETED.booleanValue())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllRehabilitationGroupsWithEagerRelationshipsIsEnabled() throws Exception {
        RehabilitationGroupResource rehabilitationGroupResource = new RehabilitationGroupResource(rehabilitationGroupServiceMock);
        when(rehabilitationGroupServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restRehabilitationGroupMockMvc = MockMvcBuilders.standaloneSetup(rehabilitationGroupResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restRehabilitationGroupMockMvc.perform(get("/api/rehabilitation-groups?eagerload=true"))
        .andExpect(status().isOk());

        verify(rehabilitationGroupServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllRehabilitationGroupsWithEagerRelationshipsIsNotEnabled() throws Exception {
        RehabilitationGroupResource rehabilitationGroupResource = new RehabilitationGroupResource(rehabilitationGroupServiceMock);
            when(rehabilitationGroupServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restRehabilitationGroupMockMvc = MockMvcBuilders.standaloneSetup(rehabilitationGroupResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restRehabilitationGroupMockMvc.perform(get("/api/rehabilitation-groups?eagerload=true"))
        .andExpect(status().isOk());

            verify(rehabilitationGroupServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getRehabilitationGroup() throws Exception {
        // Initialize the database
        rehabilitationGroupRepository.saveAndFlush(rehabilitationGroup);

        // Get the rehabilitationGroup
        restRehabilitationGroupMockMvc.perform(get("/api/rehabilitation-groups/{id}", rehabilitationGroup.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(rehabilitationGroup.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.creationDate").value(DEFAULT_CREATION_DATE.toString()))
            .andExpect(jsonPath("$.programStatus").value(DEFAULT_PROGRAM_STATUS))
            .andExpect(jsonPath("$.deleted").value(DEFAULT_DELETED.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingRehabilitationGroup() throws Exception {
        // Get the rehabilitationGroup
        restRehabilitationGroupMockMvc.perform(get("/api/rehabilitation-groups/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRehabilitationGroup() throws Exception {
        // Initialize the database
        rehabilitationGroupRepository.saveAndFlush(rehabilitationGroup);

        int databaseSizeBeforeUpdate = rehabilitationGroupRepository.findAll().size();

        // Update the rehabilitationGroup
        RehabilitationGroup updatedRehabilitationGroup = rehabilitationGroupRepository.findById(rehabilitationGroup.getId()).get();
        // Disconnect from session so that the updates on updatedRehabilitationGroup are not directly saved in db
        em.detach(updatedRehabilitationGroup);
        updatedRehabilitationGroup
            .name(UPDATED_NAME)
            .creationDate(UPDATED_CREATION_DATE)
            .programStatus(UPDATED_PROGRAM_STATUS)
            .deleted(UPDATED_DELETED);
        RehabilitationGroupDTO rehabilitationGroupDTO = rehabilitationGroupMapper.toDto(updatedRehabilitationGroup);

        restRehabilitationGroupMockMvc.perform(put("/api/rehabilitation-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rehabilitationGroupDTO)))
            .andExpect(status().isOk());

        // Validate the RehabilitationGroup in the database
        List<RehabilitationGroup> rehabilitationGroupList = rehabilitationGroupRepository.findAll();
        assertThat(rehabilitationGroupList).hasSize(databaseSizeBeforeUpdate);
        RehabilitationGroup testRehabilitationGroup = rehabilitationGroupList.get(rehabilitationGroupList.size() - 1);
        assertThat(testRehabilitationGroup.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testRehabilitationGroup.getCreationDate()).isEqualTo(UPDATED_CREATION_DATE);
        assertThat(testRehabilitationGroup.getProgramStatus()).isEqualTo(UPDATED_PROGRAM_STATUS);
        assertThat(testRehabilitationGroup.isDeleted()).isEqualTo(UPDATED_DELETED);
    }

    @Test
    @Transactional
    public void updateNonExistingRehabilitationGroup() throws Exception {
        int databaseSizeBeforeUpdate = rehabilitationGroupRepository.findAll().size();

        // Create the RehabilitationGroup
        RehabilitationGroupDTO rehabilitationGroupDTO = rehabilitationGroupMapper.toDto(rehabilitationGroup);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRehabilitationGroupMockMvc.perform(put("/api/rehabilitation-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rehabilitationGroupDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RehabilitationGroup in the database
        List<RehabilitationGroup> rehabilitationGroupList = rehabilitationGroupRepository.findAll();
        assertThat(rehabilitationGroupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRehabilitationGroup() throws Exception {
        // Initialize the database
        rehabilitationGroupRepository.saveAndFlush(rehabilitationGroup);

        int databaseSizeBeforeDelete = rehabilitationGroupRepository.findAll().size();

        // Delete the rehabilitationGroup
        restRehabilitationGroupMockMvc.perform(delete("/api/rehabilitation-groups/{id}", rehabilitationGroup.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<RehabilitationGroup> rehabilitationGroupList = rehabilitationGroupRepository.findAll();
        assertThat(rehabilitationGroupList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RehabilitationGroup.class);
        RehabilitationGroup rehabilitationGroup1 = new RehabilitationGroup();
        rehabilitationGroup1.setId(1L);
        RehabilitationGroup rehabilitationGroup2 = new RehabilitationGroup();
        rehabilitationGroup2.setId(rehabilitationGroup1.getId());
        assertThat(rehabilitationGroup1).isEqualTo(rehabilitationGroup2);
        rehabilitationGroup2.setId(2L);
        assertThat(rehabilitationGroup1).isNotEqualTo(rehabilitationGroup2);
        rehabilitationGroup1.setId(null);
        assertThat(rehabilitationGroup1).isNotEqualTo(rehabilitationGroup2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RehabilitationGroupDTO.class);
        RehabilitationGroupDTO rehabilitationGroupDTO1 = new RehabilitationGroupDTO();
        rehabilitationGroupDTO1.setId(1L);
        RehabilitationGroupDTO rehabilitationGroupDTO2 = new RehabilitationGroupDTO();
        assertThat(rehabilitationGroupDTO1).isNotEqualTo(rehabilitationGroupDTO2);
        rehabilitationGroupDTO2.setId(rehabilitationGroupDTO1.getId());
        assertThat(rehabilitationGroupDTO1).isEqualTo(rehabilitationGroupDTO2);
        rehabilitationGroupDTO2.setId(2L);
        assertThat(rehabilitationGroupDTO1).isNotEqualTo(rehabilitationGroupDTO2);
        rehabilitationGroupDTO1.setId(null);
        assertThat(rehabilitationGroupDTO1).isNotEqualTo(rehabilitationGroupDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(rehabilitationGroupMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(rehabilitationGroupMapper.fromId(null)).isNull();
    }
}
