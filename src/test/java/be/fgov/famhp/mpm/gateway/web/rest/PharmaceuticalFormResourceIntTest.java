package be.fgov.famhp.mpm.gateway.web.rest;

import be.fgov.famhp.mpm.gateway.MpmNgApp;

import be.fgov.famhp.mpm.gateway.domain.PharmaceuticalForm;
import be.fgov.famhp.mpm.gateway.repository.PharmaceuticalFormRepository;
import be.fgov.famhp.mpm.gateway.service.PharmaceuticalFormService;
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
 * Test class for the PharmaceuticalFormResource REST controller.
 *
 * @see PharmaceuticalFormResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MpmNgApp.class)
public class PharmaceuticalFormResourceIntTest {

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

    private static final String DEFAULT_EDQM_CODE = "AAAAAAAAAA";
    private static final String UPDATED_EDQM_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_EDQM_DEFINITION = "AAAAAAAAAA";
    private static final String UPDATED_EDQM_DEFINITION = "BBBBBBBBBB";

    @Autowired
    private PharmaceuticalFormRepository pharmaceuticalFormRepository;

    @Autowired
    private PharmaceuticalFormService pharmaceuticalFormService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restPharmaceuticalFormMockMvc;

    private PharmaceuticalForm pharmaceuticalForm;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PharmaceuticalFormResource pharmaceuticalFormResource = new PharmaceuticalFormResource(pharmaceuticalFormService);
        this.restPharmaceuticalFormMockMvc = MockMvcBuilders.standaloneSetup(pharmaceuticalFormResource)
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
    public static PharmaceuticalForm createEntity() {
        PharmaceuticalForm pharmaceuticalForm = new PharmaceuticalForm()
            .code(DEFAULT_CODE)
            .en(DEFAULT_EN)
            .fr(DEFAULT_FR)
            .de(DEFAULT_DE)
            .nl(DEFAULT_NL)
            .edqmCode(DEFAULT_EDQM_CODE)
            .edqmDefinition(DEFAULT_EDQM_DEFINITION);
        return pharmaceuticalForm;
    }

    @Before
    public void initTest() {
        pharmaceuticalFormRepository.deleteAll();
        pharmaceuticalForm = createEntity();
    }

    @Test
    public void createPharmaceuticalForm() throws Exception {
        int databaseSizeBeforeCreate = pharmaceuticalFormRepository.findAll().size();

        // Create the PharmaceuticalForm
        restPharmaceuticalFormMockMvc.perform(post("/api/pharmaceutical-forms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pharmaceuticalForm)))
            .andExpect(status().isCreated());

        // Validate the PharmaceuticalForm in the database
        List<PharmaceuticalForm> pharmaceuticalFormList = pharmaceuticalFormRepository.findAll();
        assertThat(pharmaceuticalFormList).hasSize(databaseSizeBeforeCreate + 1);
        PharmaceuticalForm testPharmaceuticalForm = pharmaceuticalFormList.get(pharmaceuticalFormList.size() - 1);
        assertThat(testPharmaceuticalForm.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testPharmaceuticalForm.getEn()).isEqualTo(DEFAULT_EN);
        assertThat(testPharmaceuticalForm.getFr()).isEqualTo(DEFAULT_FR);
        assertThat(testPharmaceuticalForm.getDe()).isEqualTo(DEFAULT_DE);
        assertThat(testPharmaceuticalForm.getNl()).isEqualTo(DEFAULT_NL);
        assertThat(testPharmaceuticalForm.getEdqmCode()).isEqualTo(DEFAULT_EDQM_CODE);
        assertThat(testPharmaceuticalForm.getEdqmDefinition()).isEqualTo(DEFAULT_EDQM_DEFINITION);
    }

    @Test
    public void createPharmaceuticalFormWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pharmaceuticalFormRepository.findAll().size();

        // Create the PharmaceuticalForm with an existing ID
        pharmaceuticalForm.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restPharmaceuticalFormMockMvc.perform(post("/api/pharmaceutical-forms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pharmaceuticalForm)))
            .andExpect(status().isBadRequest());

        // Validate the PharmaceuticalForm in the database
        List<PharmaceuticalForm> pharmaceuticalFormList = pharmaceuticalFormRepository.findAll();
        assertThat(pharmaceuticalFormList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = pharmaceuticalFormRepository.findAll().size();
        // set the field null
        pharmaceuticalForm.setCode(null);

        // Create the PharmaceuticalForm, which fails.

        restPharmaceuticalFormMockMvc.perform(post("/api/pharmaceutical-forms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pharmaceuticalForm)))
            .andExpect(status().isBadRequest());

        List<PharmaceuticalForm> pharmaceuticalFormList = pharmaceuticalFormRepository.findAll();
        assertThat(pharmaceuticalFormList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllPharmaceuticalForms() throws Exception {
        // Initialize the database
        pharmaceuticalFormRepository.save(pharmaceuticalForm);

        // Get all the pharmaceuticalFormList
        restPharmaceuticalFormMockMvc.perform(get("/api/pharmaceutical-forms?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pharmaceuticalForm.getId())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].en").value(hasItem(DEFAULT_EN.toString())))
            .andExpect(jsonPath("$.[*].fr").value(hasItem(DEFAULT_FR.toString())))
            .andExpect(jsonPath("$.[*].de").value(hasItem(DEFAULT_DE.toString())))
            .andExpect(jsonPath("$.[*].nl").value(hasItem(DEFAULT_NL.toString())))
            .andExpect(jsonPath("$.[*].edqmCode").value(hasItem(DEFAULT_EDQM_CODE.toString())))
            .andExpect(jsonPath("$.[*].edqmDefinition").value(hasItem(DEFAULT_EDQM_DEFINITION.toString())));
    }

    @Test
    public void getPharmaceuticalForm() throws Exception {
        // Initialize the database
        pharmaceuticalFormRepository.save(pharmaceuticalForm);

        // Get the pharmaceuticalForm
        restPharmaceuticalFormMockMvc.perform(get("/api/pharmaceutical-forms/{id}", pharmaceuticalForm.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(pharmaceuticalForm.getId()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.en").value(DEFAULT_EN.toString()))
            .andExpect(jsonPath("$.fr").value(DEFAULT_FR.toString()))
            .andExpect(jsonPath("$.de").value(DEFAULT_DE.toString()))
            .andExpect(jsonPath("$.nl").value(DEFAULT_NL.toString()))
            .andExpect(jsonPath("$.edqmCode").value(DEFAULT_EDQM_CODE.toString()))
            .andExpect(jsonPath("$.edqmDefinition").value(DEFAULT_EDQM_DEFINITION.toString()));
    }

    @Test
    public void getNonExistingPharmaceuticalForm() throws Exception {
        // Get the pharmaceuticalForm
        restPharmaceuticalFormMockMvc.perform(get("/api/pharmaceutical-forms/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updatePharmaceuticalForm() throws Exception {
        // Initialize the database
        pharmaceuticalFormService.save(pharmaceuticalForm);

        int databaseSizeBeforeUpdate = pharmaceuticalFormRepository.findAll().size();

        // Update the pharmaceuticalForm
        PharmaceuticalForm updatedPharmaceuticalForm = pharmaceuticalFormRepository.findOne(pharmaceuticalForm.getId());
        updatedPharmaceuticalForm
            .code(UPDATED_CODE)
            .en(UPDATED_EN)
            .fr(UPDATED_FR)
            .de(UPDATED_DE)
            .nl(UPDATED_NL)
            .edqmCode(UPDATED_EDQM_CODE)
            .edqmDefinition(UPDATED_EDQM_DEFINITION);

        restPharmaceuticalFormMockMvc.perform(put("/api/pharmaceutical-forms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPharmaceuticalForm)))
            .andExpect(status().isOk());

        // Validate the PharmaceuticalForm in the database
        List<PharmaceuticalForm> pharmaceuticalFormList = pharmaceuticalFormRepository.findAll();
        assertThat(pharmaceuticalFormList).hasSize(databaseSizeBeforeUpdate);
        PharmaceuticalForm testPharmaceuticalForm = pharmaceuticalFormList.get(pharmaceuticalFormList.size() - 1);
        assertThat(testPharmaceuticalForm.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testPharmaceuticalForm.getEn()).isEqualTo(UPDATED_EN);
        assertThat(testPharmaceuticalForm.getFr()).isEqualTo(UPDATED_FR);
        assertThat(testPharmaceuticalForm.getDe()).isEqualTo(UPDATED_DE);
        assertThat(testPharmaceuticalForm.getNl()).isEqualTo(UPDATED_NL);
        assertThat(testPharmaceuticalForm.getEdqmCode()).isEqualTo(UPDATED_EDQM_CODE);
        assertThat(testPharmaceuticalForm.getEdqmDefinition()).isEqualTo(UPDATED_EDQM_DEFINITION);
    }

    @Test
    public void updateNonExistingPharmaceuticalForm() throws Exception {
        int databaseSizeBeforeUpdate = pharmaceuticalFormRepository.findAll().size();

        // Create the PharmaceuticalForm

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPharmaceuticalFormMockMvc.perform(put("/api/pharmaceutical-forms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pharmaceuticalForm)))
            .andExpect(status().isCreated());

        // Validate the PharmaceuticalForm in the database
        List<PharmaceuticalForm> pharmaceuticalFormList = pharmaceuticalFormRepository.findAll();
        assertThat(pharmaceuticalFormList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deletePharmaceuticalForm() throws Exception {
        // Initialize the database
        pharmaceuticalFormService.save(pharmaceuticalForm);

        int databaseSizeBeforeDelete = pharmaceuticalFormRepository.findAll().size();

        // Get the pharmaceuticalForm
        restPharmaceuticalFormMockMvc.perform(delete("/api/pharmaceutical-forms/{id}", pharmaceuticalForm.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PharmaceuticalForm> pharmaceuticalFormList = pharmaceuticalFormRepository.findAll();
        assertThat(pharmaceuticalFormList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PharmaceuticalForm.class);
        PharmaceuticalForm pharmaceuticalForm1 = new PharmaceuticalForm();
        pharmaceuticalForm1.setId("id1");
        PharmaceuticalForm pharmaceuticalForm2 = new PharmaceuticalForm();
        pharmaceuticalForm2.setId(pharmaceuticalForm1.getId());
        assertThat(pharmaceuticalForm1).isEqualTo(pharmaceuticalForm2);
        pharmaceuticalForm2.setId("id2");
        assertThat(pharmaceuticalForm1).isNotEqualTo(pharmaceuticalForm2);
        pharmaceuticalForm1.setId(null);
        assertThat(pharmaceuticalForm1).isNotEqualTo(pharmaceuticalForm2);
    }
}
