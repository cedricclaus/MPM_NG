package be.fgov.famhp.mpm.gateway.web.rest;

import be.fgov.famhp.mpm.gateway.MpmNgApp;

import be.fgov.famhp.mpm.gateway.domain.ProcedureType;
import be.fgov.famhp.mpm.gateway.repository.ProcedureTypeRepository;
import be.fgov.famhp.mpm.gateway.service.ProcedureTypeService;
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
 * Test class for the ProcedureTypeResource REST controller.
 *
 * @see ProcedureTypeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MpmNgApp.class)
public class ProcedureTypeResourceIntTest {

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
    private ProcedureTypeRepository procedureTypeRepository;

    @Autowired
    private ProcedureTypeService procedureTypeService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restProcedureTypeMockMvc;

    private ProcedureType procedureType;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProcedureTypeResource procedureTypeResource = new ProcedureTypeResource(procedureTypeService);
        this.restProcedureTypeMockMvc = MockMvcBuilders.standaloneSetup(procedureTypeResource)
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
    public static ProcedureType createEntity() {
        ProcedureType procedureType = new ProcedureType()
            .code(DEFAULT_CODE)
            .en(DEFAULT_EN)
            .fr(DEFAULT_FR)
            .de(DEFAULT_DE)
            .nl(DEFAULT_NL);
        return procedureType;
    }

    @Before
    public void initTest() {
        procedureTypeRepository.deleteAll();
        procedureType = createEntity();
    }

    @Test
    public void createProcedureType() throws Exception {
        int databaseSizeBeforeCreate = procedureTypeRepository.findAll().size();

        // Create the ProcedureType
        restProcedureTypeMockMvc.perform(post("/api/procedure-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(procedureType)))
            .andExpect(status().isCreated());

        // Validate the ProcedureType in the database
        List<ProcedureType> procedureTypeList = procedureTypeRepository.findAll();
        assertThat(procedureTypeList).hasSize(databaseSizeBeforeCreate + 1);
        ProcedureType testProcedureType = procedureTypeList.get(procedureTypeList.size() - 1);
        assertThat(testProcedureType.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testProcedureType.getEn()).isEqualTo(DEFAULT_EN);
        assertThat(testProcedureType.getFr()).isEqualTo(DEFAULT_FR);
        assertThat(testProcedureType.getDe()).isEqualTo(DEFAULT_DE);
        assertThat(testProcedureType.getNl()).isEqualTo(DEFAULT_NL);
    }

    @Test
    public void createProcedureTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = procedureTypeRepository.findAll().size();

        // Create the ProcedureType with an existing ID
        procedureType.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restProcedureTypeMockMvc.perform(post("/api/procedure-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(procedureType)))
            .andExpect(status().isBadRequest());

        // Validate the ProcedureType in the database
        List<ProcedureType> procedureTypeList = procedureTypeRepository.findAll();
        assertThat(procedureTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = procedureTypeRepository.findAll().size();
        // set the field null
        procedureType.setCode(null);

        // Create the ProcedureType, which fails.

        restProcedureTypeMockMvc.perform(post("/api/procedure-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(procedureType)))
            .andExpect(status().isBadRequest());

        List<ProcedureType> procedureTypeList = procedureTypeRepository.findAll();
        assertThat(procedureTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllProcedureTypes() throws Exception {
        // Initialize the database
        procedureTypeRepository.save(procedureType);

        // Get all the procedureTypeList
        restProcedureTypeMockMvc.perform(get("/api/procedure-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(procedureType.getId())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].en").value(hasItem(DEFAULT_EN.toString())))
            .andExpect(jsonPath("$.[*].fr").value(hasItem(DEFAULT_FR.toString())))
            .andExpect(jsonPath("$.[*].de").value(hasItem(DEFAULT_DE.toString())))
            .andExpect(jsonPath("$.[*].nl").value(hasItem(DEFAULT_NL.toString())));
    }

    @Test
    public void getProcedureType() throws Exception {
        // Initialize the database
        procedureTypeRepository.save(procedureType);

        // Get the procedureType
        restProcedureTypeMockMvc.perform(get("/api/procedure-types/{id}", procedureType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(procedureType.getId()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.en").value(DEFAULT_EN.toString()))
            .andExpect(jsonPath("$.fr").value(DEFAULT_FR.toString()))
            .andExpect(jsonPath("$.de").value(DEFAULT_DE.toString()))
            .andExpect(jsonPath("$.nl").value(DEFAULT_NL.toString()));
    }

    @Test
    public void getNonExistingProcedureType() throws Exception {
        // Get the procedureType
        restProcedureTypeMockMvc.perform(get("/api/procedure-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateProcedureType() throws Exception {
        // Initialize the database
        procedureTypeService.save(procedureType);

        int databaseSizeBeforeUpdate = procedureTypeRepository.findAll().size();

        // Update the procedureType
        ProcedureType updatedProcedureType = procedureTypeRepository.findOne(procedureType.getId());
        updatedProcedureType
            .code(UPDATED_CODE)
            .en(UPDATED_EN)
            .fr(UPDATED_FR)
            .de(UPDATED_DE)
            .nl(UPDATED_NL);

        restProcedureTypeMockMvc.perform(put("/api/procedure-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedProcedureType)))
            .andExpect(status().isOk());

        // Validate the ProcedureType in the database
        List<ProcedureType> procedureTypeList = procedureTypeRepository.findAll();
        assertThat(procedureTypeList).hasSize(databaseSizeBeforeUpdate);
        ProcedureType testProcedureType = procedureTypeList.get(procedureTypeList.size() - 1);
        assertThat(testProcedureType.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testProcedureType.getEn()).isEqualTo(UPDATED_EN);
        assertThat(testProcedureType.getFr()).isEqualTo(UPDATED_FR);
        assertThat(testProcedureType.getDe()).isEqualTo(UPDATED_DE);
        assertThat(testProcedureType.getNl()).isEqualTo(UPDATED_NL);
    }

    @Test
    public void updateNonExistingProcedureType() throws Exception {
        int databaseSizeBeforeUpdate = procedureTypeRepository.findAll().size();

        // Create the ProcedureType

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restProcedureTypeMockMvc.perform(put("/api/procedure-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(procedureType)))
            .andExpect(status().isCreated());

        // Validate the ProcedureType in the database
        List<ProcedureType> procedureTypeList = procedureTypeRepository.findAll();
        assertThat(procedureTypeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteProcedureType() throws Exception {
        // Initialize the database
        procedureTypeService.save(procedureType);

        int databaseSizeBeforeDelete = procedureTypeRepository.findAll().size();

        // Get the procedureType
        restProcedureTypeMockMvc.perform(delete("/api/procedure-types/{id}", procedureType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ProcedureType> procedureTypeList = procedureTypeRepository.findAll();
        assertThat(procedureTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProcedureType.class);
        ProcedureType procedureType1 = new ProcedureType();
        procedureType1.setId("id1");
        ProcedureType procedureType2 = new ProcedureType();
        procedureType2.setId(procedureType1.getId());
        assertThat(procedureType1).isEqualTo(procedureType2);
        procedureType2.setId("id2");
        assertThat(procedureType1).isNotEqualTo(procedureType2);
        procedureType1.setId(null);
        assertThat(procedureType1).isNotEqualTo(procedureType2);
    }
}
