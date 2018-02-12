package com.dmmiralles.challenges.shop.web.rest;

import com.dmmiralles.challenges.shop.ShopchallengeApp;

import com.dmmiralles.challenges.shop.domain.Assistance;
import com.dmmiralles.challenges.shop.repository.AssistanceRepository;
import com.dmmiralles.challenges.shop.service.AssistanceService;
import com.dmmiralles.challenges.shop.repository.search.AssistanceSearchRepository;
import com.dmmiralles.challenges.shop.service.dto.AssistanceDTO;
import com.dmmiralles.challenges.shop.service.mapper.AssistanceMapper;
import com.dmmiralles.challenges.shop.web.rest.errors.ExceptionTranslator;

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

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.dmmiralles.challenges.shop.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the AssistanceResource REST controller.
 *
 * @see AssistanceResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ShopchallengeApp.class)
public class AssistanceResourceIntTest {

    private static final Instant DEFAULT_INIT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_INIT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_END = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_END = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private AssistanceRepository assistanceRepository;

    @Autowired
    private AssistanceMapper assistanceMapper;

    @Autowired
    private AssistanceService assistanceService;

    @Autowired
    private AssistanceSearchRepository assistanceSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAssistanceMockMvc;

    private Assistance assistance;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AssistanceResource assistanceResource = new AssistanceResource(assistanceService);
        this.restAssistanceMockMvc = MockMvcBuilders.standaloneSetup(assistanceResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Assistance createEntity(EntityManager em) {
        Assistance assistance = new Assistance()
            .init(DEFAULT_INIT)
            .end(DEFAULT_END);
        return assistance;
    }

    @Before
    public void initTest() {
        assistanceSearchRepository.deleteAll();
        assistance = createEntity(em);
    }

    @Test
    @Transactional
    public void createAssistance() throws Exception {
        int databaseSizeBeforeCreate = assistanceRepository.findAll().size();

        // Create the Assistance
        AssistanceDTO assistanceDTO = assistanceMapper.toDto(assistance);
        restAssistanceMockMvc.perform(post("/api/assistances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(assistanceDTO)))
            .andExpect(status().isCreated());

        // Validate the Assistance in the database
        List<Assistance> assistanceList = assistanceRepository.findAll();
        assertThat(assistanceList).hasSize(databaseSizeBeforeCreate + 1);
        Assistance testAssistance = assistanceList.get(assistanceList.size() - 1);
        assertThat(testAssistance.getInit()).isEqualTo(DEFAULT_INIT);
        assertThat(testAssistance.getEnd()).isEqualTo(DEFAULT_END);

        // Validate the Assistance in Elasticsearch
        Assistance assistanceEs = assistanceSearchRepository.findOne(testAssistance.getId());
        assertThat(assistanceEs).isEqualToIgnoringGivenFields(testAssistance);
    }

    @Test
    @Transactional
    public void createAssistanceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = assistanceRepository.findAll().size();

        // Create the Assistance with an existing ID
        assistance.setId(1L);
        AssistanceDTO assistanceDTO = assistanceMapper.toDto(assistance);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAssistanceMockMvc.perform(post("/api/assistances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(assistanceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Assistance in the database
        List<Assistance> assistanceList = assistanceRepository.findAll();
        assertThat(assistanceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkInitIsRequired() throws Exception {
        int databaseSizeBeforeTest = assistanceRepository.findAll().size();
        // set the field null
        assistance.setInit(null);

        // Create the Assistance, which fails.
        AssistanceDTO assistanceDTO = assistanceMapper.toDto(assistance);

        restAssistanceMockMvc.perform(post("/api/assistances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(assistanceDTO)))
            .andExpect(status().isBadRequest());

        List<Assistance> assistanceList = assistanceRepository.findAll();
        assertThat(assistanceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEndIsRequired() throws Exception {
        int databaseSizeBeforeTest = assistanceRepository.findAll().size();
        // set the field null
        assistance.setEnd(null);

        // Create the Assistance, which fails.
        AssistanceDTO assistanceDTO = assistanceMapper.toDto(assistance);

        restAssistanceMockMvc.perform(post("/api/assistances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(assistanceDTO)))
            .andExpect(status().isBadRequest());

        List<Assistance> assistanceList = assistanceRepository.findAll();
        assertThat(assistanceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAssistances() throws Exception {
        // Initialize the database
        assistanceRepository.saveAndFlush(assistance);

        // Get all the assistanceList
        restAssistanceMockMvc.perform(get("/api/assistances?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(assistance.getId().intValue())))
            .andExpect(jsonPath("$.[*].init").value(hasItem(DEFAULT_INIT.toString())))
            .andExpect(jsonPath("$.[*].end").value(hasItem(DEFAULT_END.toString())));
    }

    @Test
    @Transactional
    public void getAssistance() throws Exception {
        // Initialize the database
        assistanceRepository.saveAndFlush(assistance);

        // Get the assistance
        restAssistanceMockMvc.perform(get("/api/assistances/{id}", assistance.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(assistance.getId().intValue()))
            .andExpect(jsonPath("$.init").value(DEFAULT_INIT.toString()))
            .andExpect(jsonPath("$.end").value(DEFAULT_END.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAssistance() throws Exception {
        // Get the assistance
        restAssistanceMockMvc.perform(get("/api/assistances/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAssistance() throws Exception {
        // Initialize the database
        assistanceRepository.saveAndFlush(assistance);
        assistanceSearchRepository.save(assistance);
        int databaseSizeBeforeUpdate = assistanceRepository.findAll().size();

        // Update the assistance
        Assistance updatedAssistance = assistanceRepository.findOne(assistance.getId());
        // Disconnect from session so that the updates on updatedAssistance are not directly saved in db
        em.detach(updatedAssistance);
        updatedAssistance
            .init(UPDATED_INIT)
            .end(UPDATED_END);
        AssistanceDTO assistanceDTO = assistanceMapper.toDto(updatedAssistance);

        restAssistanceMockMvc.perform(put("/api/assistances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(assistanceDTO)))
            .andExpect(status().isOk());

        // Validate the Assistance in the database
        List<Assistance> assistanceList = assistanceRepository.findAll();
        assertThat(assistanceList).hasSize(databaseSizeBeforeUpdate);
        Assistance testAssistance = assistanceList.get(assistanceList.size() - 1);
        assertThat(testAssistance.getInit()).isEqualTo(UPDATED_INIT);
        assertThat(testAssistance.getEnd()).isEqualTo(UPDATED_END);

        // Validate the Assistance in Elasticsearch
        Assistance assistanceEs = assistanceSearchRepository.findOne(testAssistance.getId());
        assertThat(assistanceEs).isEqualToIgnoringGivenFields(testAssistance);
    }

    @Test
    @Transactional
    public void updateNonExistingAssistance() throws Exception {
        int databaseSizeBeforeUpdate = assistanceRepository.findAll().size();

        // Create the Assistance
        AssistanceDTO assistanceDTO = assistanceMapper.toDto(assistance);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAssistanceMockMvc.perform(put("/api/assistances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(assistanceDTO)))
            .andExpect(status().isCreated());

        // Validate the Assistance in the database
        List<Assistance> assistanceList = assistanceRepository.findAll();
        assertThat(assistanceList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteAssistance() throws Exception {
        // Initialize the database
        assistanceRepository.saveAndFlush(assistance);
        assistanceSearchRepository.save(assistance);
        int databaseSizeBeforeDelete = assistanceRepository.findAll().size();

        // Get the assistance
        restAssistanceMockMvc.perform(delete("/api/assistances/{id}", assistance.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean assistanceExistsInEs = assistanceSearchRepository.exists(assistance.getId());
        assertThat(assistanceExistsInEs).isFalse();

        // Validate the database is empty
        List<Assistance> assistanceList = assistanceRepository.findAll();
        assertThat(assistanceList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchAssistance() throws Exception {
        // Initialize the database
        assistanceRepository.saveAndFlush(assistance);
        assistanceSearchRepository.save(assistance);

        // Search the assistance
        restAssistanceMockMvc.perform(get("/api/_search/assistances?query=id:" + assistance.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(assistance.getId().intValue())))
            .andExpect(jsonPath("$.[*].init").value(hasItem(DEFAULT_INIT.toString())))
            .andExpect(jsonPath("$.[*].end").value(hasItem(DEFAULT_END.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Assistance.class);
        Assistance assistance1 = new Assistance();
        assistance1.setId(1L);
        Assistance assistance2 = new Assistance();
        assistance2.setId(assistance1.getId());
        assertThat(assistance1).isEqualTo(assistance2);
        assistance2.setId(2L);
        assertThat(assistance1).isNotEqualTo(assistance2);
        assistance1.setId(null);
        assertThat(assistance1).isNotEqualTo(assistance2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AssistanceDTO.class);
        AssistanceDTO assistanceDTO1 = new AssistanceDTO();
        assistanceDTO1.setId(1L);
        AssistanceDTO assistanceDTO2 = new AssistanceDTO();
        assertThat(assistanceDTO1).isNotEqualTo(assistanceDTO2);
        assistanceDTO2.setId(assistanceDTO1.getId());
        assertThat(assistanceDTO1).isEqualTo(assistanceDTO2);
        assistanceDTO2.setId(2L);
        assertThat(assistanceDTO1).isNotEqualTo(assistanceDTO2);
        assistanceDTO1.setId(null);
        assertThat(assistanceDTO1).isNotEqualTo(assistanceDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(assistanceMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(assistanceMapper.fromId(null)).isNull();
    }
}
