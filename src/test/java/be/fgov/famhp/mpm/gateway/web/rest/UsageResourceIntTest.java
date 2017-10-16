package be.fgov.famhp.mpm.gateway.web.rest;

import be.fgov.famhp.mpm.gateway.MpmNgApp;

import be.fgov.famhp.mpm.gateway.domain.Usage;
import be.fgov.famhp.mpm.gateway.repository.UsageRepository;
import be.fgov.famhp.mpm.gateway.service.UsageService;
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
 * Test class for the UsageResource REST controller.
 *
 * @see UsageResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MpmNgApp.class)
public class UsageResourceIntTest {

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
    private UsageRepository usageRepository;

    @Autowired
    private UsageService usageService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restUsageMockMvc;

    private Usage usage;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final UsageResource usageResource = new UsageResource(usageService);
        this.restUsageMockMvc = MockMvcBuilders.standaloneSetup(usageResource)
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
    public static Usage createEntity() {
        Usage usage = new Usage()
            .code(DEFAULT_CODE)
            .en(DEFAULT_EN)
            .fr(DEFAULT_FR)
            .de(DEFAULT_DE)
            .nl(DEFAULT_NL);
        return usage;
    }

    @Before
    public void initTest() {
        usageRepository.deleteAll();
        usage = createEntity();
    }

    @Test
    public void createUsage() throws Exception {
        int databaseSizeBeforeCreate = usageRepository.findAll().size();

        // Create the Usage
        restUsageMockMvc.perform(post("/api/usages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(usage)))
            .andExpect(status().isCreated());

        // Validate the Usage in the database
        List<Usage> usageList = usageRepository.findAll();
        assertThat(usageList).hasSize(databaseSizeBeforeCreate + 1);
        Usage testUsage = usageList.get(usageList.size() - 1);
        assertThat(testUsage.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testUsage.getEn()).isEqualTo(DEFAULT_EN);
        assertThat(testUsage.getFr()).isEqualTo(DEFAULT_FR);
        assertThat(testUsage.getDe()).isEqualTo(DEFAULT_DE);
        assertThat(testUsage.getNl()).isEqualTo(DEFAULT_NL);
    }

    @Test
    public void createUsageWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = usageRepository.findAll().size();

        // Create the Usage with an existing ID
        usage.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restUsageMockMvc.perform(post("/api/usages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(usage)))
            .andExpect(status().isBadRequest());

        // Validate the Usage in the database
        List<Usage> usageList = usageRepository.findAll();
        assertThat(usageList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = usageRepository.findAll().size();
        // set the field null
        usage.setCode(null);

        // Create the Usage, which fails.

        restUsageMockMvc.perform(post("/api/usages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(usage)))
            .andExpect(status().isBadRequest());

        List<Usage> usageList = usageRepository.findAll();
        assertThat(usageList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllUsages() throws Exception {
        // Initialize the database
        usageRepository.save(usage);

        // Get all the usageList
        restUsageMockMvc.perform(get("/api/usages?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(usage.getId())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].en").value(hasItem(DEFAULT_EN.toString())))
            .andExpect(jsonPath("$.[*].fr").value(hasItem(DEFAULT_FR.toString())))
            .andExpect(jsonPath("$.[*].de").value(hasItem(DEFAULT_DE.toString())))
            .andExpect(jsonPath("$.[*].nl").value(hasItem(DEFAULT_NL.toString())));
    }

    @Test
    public void getUsage() throws Exception {
        // Initialize the database
        usageRepository.save(usage);

        // Get the usage
        restUsageMockMvc.perform(get("/api/usages/{id}", usage.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(usage.getId()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.en").value(DEFAULT_EN.toString()))
            .andExpect(jsonPath("$.fr").value(DEFAULT_FR.toString()))
            .andExpect(jsonPath("$.de").value(DEFAULT_DE.toString()))
            .andExpect(jsonPath("$.nl").value(DEFAULT_NL.toString()));
    }

    @Test
    public void getNonExistingUsage() throws Exception {
        // Get the usage
        restUsageMockMvc.perform(get("/api/usages/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateUsage() throws Exception {
        // Initialize the database
        usageService.save(usage);

        int databaseSizeBeforeUpdate = usageRepository.findAll().size();

        // Update the usage
        Usage updatedUsage = usageRepository.findOne(usage.getId());
        updatedUsage
            .code(UPDATED_CODE)
            .en(UPDATED_EN)
            .fr(UPDATED_FR)
            .de(UPDATED_DE)
            .nl(UPDATED_NL);

        restUsageMockMvc.perform(put("/api/usages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedUsage)))
            .andExpect(status().isOk());

        // Validate the Usage in the database
        List<Usage> usageList = usageRepository.findAll();
        assertThat(usageList).hasSize(databaseSizeBeforeUpdate);
        Usage testUsage = usageList.get(usageList.size() - 1);
        assertThat(testUsage.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testUsage.getEn()).isEqualTo(UPDATED_EN);
        assertThat(testUsage.getFr()).isEqualTo(UPDATED_FR);
        assertThat(testUsage.getDe()).isEqualTo(UPDATED_DE);
        assertThat(testUsage.getNl()).isEqualTo(UPDATED_NL);
    }

    @Test
    public void updateNonExistingUsage() throws Exception {
        int databaseSizeBeforeUpdate = usageRepository.findAll().size();

        // Create the Usage

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restUsageMockMvc.perform(put("/api/usages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(usage)))
            .andExpect(status().isCreated());

        // Validate the Usage in the database
        List<Usage> usageList = usageRepository.findAll();
        assertThat(usageList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteUsage() throws Exception {
        // Initialize the database
        usageService.save(usage);

        int databaseSizeBeforeDelete = usageRepository.findAll().size();

        // Get the usage
        restUsageMockMvc.perform(delete("/api/usages/{id}", usage.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Usage> usageList = usageRepository.findAll();
        assertThat(usageList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Usage.class);
        Usage usage1 = new Usage();
        usage1.setId("id1");
        Usage usage2 = new Usage();
        usage2.setId(usage1.getId());
        assertThat(usage1).isEqualTo(usage2);
        usage2.setId("id2");
        assertThat(usage1).isNotEqualTo(usage2);
        usage1.setId(null);
        assertThat(usage1).isNotEqualTo(usage2);
    }
}
