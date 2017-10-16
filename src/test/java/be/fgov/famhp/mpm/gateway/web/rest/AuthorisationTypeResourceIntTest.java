package be.fgov.famhp.mpm.gateway.web.rest;

import be.fgov.famhp.mpm.gateway.MpmNgApp;

import be.fgov.famhp.mpm.gateway.domain.AuthorisationType;
import be.fgov.famhp.mpm.gateway.repository.AuthorisationTypeRepository;
import be.fgov.famhp.mpm.gateway.service.AuthorisationTypeService;
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
 * Test class for the AuthorisationTypeResource REST controller.
 *
 * @see AuthorisationTypeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MpmNgApp.class)
public class AuthorisationTypeResourceIntTest {

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
    private AuthorisationTypeRepository authorisationTypeRepository;

    @Autowired
    private AuthorisationTypeService authorisationTypeService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restAuthorisationTypeMockMvc;

    private AuthorisationType authorisationType;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AuthorisationTypeResource authorisationTypeResource = new AuthorisationTypeResource(authorisationTypeService);
        this.restAuthorisationTypeMockMvc = MockMvcBuilders.standaloneSetup(authorisationTypeResource)
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
    public static AuthorisationType createEntity() {
        AuthorisationType authorisationType = new AuthorisationType()
            .code(DEFAULT_CODE)
            .en(DEFAULT_EN)
            .fr(DEFAULT_FR)
            .de(DEFAULT_DE)
            .nl(DEFAULT_NL);
        return authorisationType;
    }

    @Before
    public void initTest() {
        authorisationTypeRepository.deleteAll();
        authorisationType = createEntity();
    }

    @Test
    public void createAuthorisationType() throws Exception {
        int databaseSizeBeforeCreate = authorisationTypeRepository.findAll().size();

        // Create the AuthorisationType
        restAuthorisationTypeMockMvc.perform(post("/api/authorisation-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(authorisationType)))
            .andExpect(status().isCreated());

        // Validate the AuthorisationType in the database
        List<AuthorisationType> authorisationTypeList = authorisationTypeRepository.findAll();
        assertThat(authorisationTypeList).hasSize(databaseSizeBeforeCreate + 1);
        AuthorisationType testAuthorisationType = authorisationTypeList.get(authorisationTypeList.size() - 1);
        assertThat(testAuthorisationType.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testAuthorisationType.getEn()).isEqualTo(DEFAULT_EN);
        assertThat(testAuthorisationType.getFr()).isEqualTo(DEFAULT_FR);
        assertThat(testAuthorisationType.getDe()).isEqualTo(DEFAULT_DE);
        assertThat(testAuthorisationType.getNl()).isEqualTo(DEFAULT_NL);
    }

    @Test
    public void createAuthorisationTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = authorisationTypeRepository.findAll().size();

        // Create the AuthorisationType with an existing ID
        authorisationType.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restAuthorisationTypeMockMvc.perform(post("/api/authorisation-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(authorisationType)))
            .andExpect(status().isBadRequest());

        // Validate the AuthorisationType in the database
        List<AuthorisationType> authorisationTypeList = authorisationTypeRepository.findAll();
        assertThat(authorisationTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = authorisationTypeRepository.findAll().size();
        // set the field null
        authorisationType.setCode(null);

        // Create the AuthorisationType, which fails.

        restAuthorisationTypeMockMvc.perform(post("/api/authorisation-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(authorisationType)))
            .andExpect(status().isBadRequest());

        List<AuthorisationType> authorisationTypeList = authorisationTypeRepository.findAll();
        assertThat(authorisationTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllAuthorisationTypes() throws Exception {
        // Initialize the database
        authorisationTypeRepository.save(authorisationType);

        // Get all the authorisationTypeList
        restAuthorisationTypeMockMvc.perform(get("/api/authorisation-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(authorisationType.getId())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].en").value(hasItem(DEFAULT_EN.toString())))
            .andExpect(jsonPath("$.[*].fr").value(hasItem(DEFAULT_FR.toString())))
            .andExpect(jsonPath("$.[*].de").value(hasItem(DEFAULT_DE.toString())))
            .andExpect(jsonPath("$.[*].nl").value(hasItem(DEFAULT_NL.toString())));
    }

    @Test
    public void getAuthorisationType() throws Exception {
        // Initialize the database
        authorisationTypeRepository.save(authorisationType);

        // Get the authorisationType
        restAuthorisationTypeMockMvc.perform(get("/api/authorisation-types/{id}", authorisationType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(authorisationType.getId()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.en").value(DEFAULT_EN.toString()))
            .andExpect(jsonPath("$.fr").value(DEFAULT_FR.toString()))
            .andExpect(jsonPath("$.de").value(DEFAULT_DE.toString()))
            .andExpect(jsonPath("$.nl").value(DEFAULT_NL.toString()));
    }

    @Test
    public void getNonExistingAuthorisationType() throws Exception {
        // Get the authorisationType
        restAuthorisationTypeMockMvc.perform(get("/api/authorisation-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateAuthorisationType() throws Exception {
        // Initialize the database
        authorisationTypeService.save(authorisationType);

        int databaseSizeBeforeUpdate = authorisationTypeRepository.findAll().size();

        // Update the authorisationType
        AuthorisationType updatedAuthorisationType = authorisationTypeRepository.findOne(authorisationType.getId());
        updatedAuthorisationType
            .code(UPDATED_CODE)
            .en(UPDATED_EN)
            .fr(UPDATED_FR)
            .de(UPDATED_DE)
            .nl(UPDATED_NL);

        restAuthorisationTypeMockMvc.perform(put("/api/authorisation-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAuthorisationType)))
            .andExpect(status().isOk());

        // Validate the AuthorisationType in the database
        List<AuthorisationType> authorisationTypeList = authorisationTypeRepository.findAll();
        assertThat(authorisationTypeList).hasSize(databaseSizeBeforeUpdate);
        AuthorisationType testAuthorisationType = authorisationTypeList.get(authorisationTypeList.size() - 1);
        assertThat(testAuthorisationType.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testAuthorisationType.getEn()).isEqualTo(UPDATED_EN);
        assertThat(testAuthorisationType.getFr()).isEqualTo(UPDATED_FR);
        assertThat(testAuthorisationType.getDe()).isEqualTo(UPDATED_DE);
        assertThat(testAuthorisationType.getNl()).isEqualTo(UPDATED_NL);
    }

    @Test
    public void updateNonExistingAuthorisationType() throws Exception {
        int databaseSizeBeforeUpdate = authorisationTypeRepository.findAll().size();

        // Create the AuthorisationType

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAuthorisationTypeMockMvc.perform(put("/api/authorisation-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(authorisationType)))
            .andExpect(status().isCreated());

        // Validate the AuthorisationType in the database
        List<AuthorisationType> authorisationTypeList = authorisationTypeRepository.findAll();
        assertThat(authorisationTypeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteAuthorisationType() throws Exception {
        // Initialize the database
        authorisationTypeService.save(authorisationType);

        int databaseSizeBeforeDelete = authorisationTypeRepository.findAll().size();

        // Get the authorisationType
        restAuthorisationTypeMockMvc.perform(delete("/api/authorisation-types/{id}", authorisationType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<AuthorisationType> authorisationTypeList = authorisationTypeRepository.findAll();
        assertThat(authorisationTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AuthorisationType.class);
        AuthorisationType authorisationType1 = new AuthorisationType();
        authorisationType1.setId("id1");
        AuthorisationType authorisationType2 = new AuthorisationType();
        authorisationType2.setId(authorisationType1.getId());
        assertThat(authorisationType1).isEqualTo(authorisationType2);
        authorisationType2.setId("id2");
        assertThat(authorisationType1).isNotEqualTo(authorisationType2);
        authorisationType1.setId(null);
        assertThat(authorisationType1).isNotEqualTo(authorisationType2);
    }
}
