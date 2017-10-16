package be.fgov.famhp.mpm.gateway.web.rest;

import be.fgov.famhp.mpm.gateway.MpmNgApp;

import be.fgov.famhp.mpm.gateway.domain.AuthorisationStatus;
import be.fgov.famhp.mpm.gateway.repository.AuthorisationStatusRepository;
import be.fgov.famhp.mpm.gateway.service.AuthorisationStatusService;
import be.fgov.famhp.mpm.gateway.web.rest.errors.ExceptionTranslator;

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

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the AuthorisationStatusResource REST controller.
 *
 * @see AuthorisationStatusResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MpmNgApp.class)
public class AuthorisationStatusResourceIntTest {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_EN = "AAAAAAAAAA";
    private static final String UPDATED_EN = "BBBBBBBBBB";

    private static final String DEFAULT_FR = "AAAAAAAAAA";
    private static final String UPDATED_FR = "BBBBBBBBBB";

    private static final String DEFAULT_DE = "AAAAAAAAAA";
    private static final String UPDATED_DE = "BBBBBBBBBB";

    private static final String DEFAULT_NL = "AAAAAAAAAA";
    private static final String UPDATED_NL = "BBBBBBBBBB";

    @Autowired
    private AuthorisationStatusRepository authorisationStatusRepository;

    @Autowired
    private AuthorisationStatusService authorisationStatusService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restAuthorisationStatusMockMvc;

    private AuthorisationStatus authorisationStatus;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AuthorisationStatusResource authorisationStatusResource = new AuthorisationStatusResource(authorisationStatusService);
        this.restAuthorisationStatusMockMvc = MockMvcBuilders.standaloneSetup(authorisationStatusResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AuthorisationStatus createEntity() {
        AuthorisationStatus authorisationStatus = new AuthorisationStatus()
            .code(DEFAULT_CODE)
            .en(DEFAULT_EN)
            .fr(DEFAULT_FR)
            .de(DEFAULT_DE)
            .nl(DEFAULT_NL);
        return authorisationStatus;
    }

    @Before
    public void initTest() {
        authorisationStatusRepository.deleteAll();
        authorisationStatus = createEntity();
    }

    @Test
    public void createAuthorisationStatus() throws Exception {
        int databaseSizeBeforeCreate = authorisationStatusRepository.findAll().size();

        // Create the AuthorisationStatus
        restAuthorisationStatusMockMvc.perform(post("/api/authorisation-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(authorisationStatus)))
            .andExpect(status().isCreated());

        // Validate the AuthorisationStatus in the database
        List<AuthorisationStatus> authorisationStatusList = authorisationStatusRepository.findAll();
        assertThat(authorisationStatusList).hasSize(databaseSizeBeforeCreate + 1);
        AuthorisationStatus testAuthorisationStatus = authorisationStatusList.get(authorisationStatusList.size() - 1);
        assertThat(testAuthorisationStatus.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testAuthorisationStatus.getEn()).isEqualTo(DEFAULT_EN);
        assertThat(testAuthorisationStatus.getFr()).isEqualTo(DEFAULT_FR);
        assertThat(testAuthorisationStatus.getDe()).isEqualTo(DEFAULT_DE);
        assertThat(testAuthorisationStatus.getNl()).isEqualTo(DEFAULT_NL);
    }

    @Test
    public void createAuthorisationStatusWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = authorisationStatusRepository.findAll().size();

        // Create the AuthorisationStatus with an existing ID
        authorisationStatus.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restAuthorisationStatusMockMvc.perform(post("/api/authorisation-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(authorisationStatus)))
            .andExpect(status().isBadRequest());

        // Validate the AuthorisationStatus in the database
        List<AuthorisationStatus> authorisationStatusList = authorisationStatusRepository.findAll();
        assertThat(authorisationStatusList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = authorisationStatusRepository.findAll().size();
        // set the field null
        authorisationStatus.setCode(null);

        // Create the AuthorisationStatus, which fails.

        restAuthorisationStatusMockMvc.perform(post("/api/authorisation-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(authorisationStatus)))
            .andExpect(status().isBadRequest());

        List<AuthorisationStatus> authorisationStatusList = authorisationStatusRepository.findAll();
        assertThat(authorisationStatusList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllAuthorisationStatuses() throws Exception {
        // Initialize the database
        authorisationStatusRepository.save(authorisationStatus);

        // Get all the authorisationStatusList
        restAuthorisationStatusMockMvc.perform(get("/api/authorisation-statuses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(authorisationStatus.getId())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].en").value(hasItem(DEFAULT_EN.toString())))
            .andExpect(jsonPath("$.[*].fr").value(hasItem(DEFAULT_FR.toString())))
            .andExpect(jsonPath("$.[*].de").value(hasItem(DEFAULT_DE.toString())))
            .andExpect(jsonPath("$.[*].nl").value(hasItem(DEFAULT_NL.toString())));
    }

    @Test
    public void getAuthorisationStatus() throws Exception {
        // Initialize the database
        authorisationStatusRepository.save(authorisationStatus);

        // Get the authorisationStatus
        restAuthorisationStatusMockMvc.perform(get("/api/authorisation-statuses/{id}", authorisationStatus.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(authorisationStatus.getId()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.en").value(DEFAULT_EN.toString()))
            .andExpect(jsonPath("$.fr").value(DEFAULT_FR.toString()))
            .andExpect(jsonPath("$.de").value(DEFAULT_DE.toString()))
            .andExpect(jsonPath("$.nl").value(DEFAULT_NL.toString()));
    }

    @Test
    public void getNonExistingAuthorisationStatus() throws Exception {
        // Get the authorisationStatus
        restAuthorisationStatusMockMvc.perform(get("/api/authorisation-statuses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateAuthorisationStatus() throws Exception {
        // Initialize the database
        authorisationStatusService.save(authorisationStatus);

        int databaseSizeBeforeUpdate = authorisationStatusRepository.findAll().size();

        // Update the authorisationStatus
        AuthorisationStatus updatedAuthorisationStatus = authorisationStatusRepository.findOne(authorisationStatus.getId());
        updatedAuthorisationStatus
            .code(UPDATED_CODE)
            .en(UPDATED_EN)
            .fr(UPDATED_FR)
            .de(UPDATED_DE)
            .nl(UPDATED_NL);

        restAuthorisationStatusMockMvc.perform(put("/api/authorisation-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAuthorisationStatus)))
            .andExpect(status().isOk());

        // Validate the AuthorisationStatus in the database
        List<AuthorisationStatus> authorisationStatusList = authorisationStatusRepository.findAll();
        assertThat(authorisationStatusList).hasSize(databaseSizeBeforeUpdate);
        AuthorisationStatus testAuthorisationStatus = authorisationStatusList.get(authorisationStatusList.size() - 1);
        assertThat(testAuthorisationStatus.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testAuthorisationStatus.getEn()).isEqualTo(UPDATED_EN);
        assertThat(testAuthorisationStatus.getFr()).isEqualTo(UPDATED_FR);
        assertThat(testAuthorisationStatus.getDe()).isEqualTo(UPDATED_DE);
        assertThat(testAuthorisationStatus.getNl()).isEqualTo(UPDATED_NL);
    }

    @Test
    public void updateNonExistingAuthorisationStatus() throws Exception {
        int databaseSizeBeforeUpdate = authorisationStatusRepository.findAll().size();

        // Create the AuthorisationStatus

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAuthorisationStatusMockMvc.perform(put("/api/authorisation-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(authorisationStatus)))
            .andExpect(status().isCreated());

        // Validate the AuthorisationStatus in the database
        List<AuthorisationStatus> authorisationStatusList = authorisationStatusRepository.findAll();
        assertThat(authorisationStatusList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteAuthorisationStatus() throws Exception {
        // Initialize the database
        authorisationStatusService.save(authorisationStatus);

        int databaseSizeBeforeDelete = authorisationStatusRepository.findAll().size();

        // Get the authorisationStatus
        restAuthorisationStatusMockMvc.perform(delete("/api/authorisation-statuses/{id}", authorisationStatus.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<AuthorisationStatus> authorisationStatusList = authorisationStatusRepository.findAll();
        assertThat(authorisationStatusList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AuthorisationStatus.class);
        AuthorisationStatus authorisationStatus1 = new AuthorisationStatus();
        authorisationStatus1.setId("id1");
        AuthorisationStatus authorisationStatus2 = new AuthorisationStatus();
        authorisationStatus2.setId(authorisationStatus1.getId());
        assertThat(authorisationStatus1).isEqualTo(authorisationStatus2);
        authorisationStatus2.setId("id2");
        assertThat(authorisationStatus1).isNotEqualTo(authorisationStatus2);
        authorisationStatus1.setId(null);
        assertThat(authorisationStatus1).isNotEqualTo(authorisationStatus2);
    }
}
