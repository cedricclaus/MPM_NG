package be.fgov.famhp.mpm.gateway.web.rest;

import be.fgov.famhp.mpm.gateway.MpmNgApp;

import be.fgov.famhp.mpm.gateway.domain.ActiveSubstance;
import be.fgov.famhp.mpm.gateway.repository.ActiveSubstanceRepository;
import be.fgov.famhp.mpm.gateway.service.ActiveSubstanceService;
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
 * Test class for the ActiveSubstanceResource REST controller.
 *
 * @see ActiveSubstanceResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MpmNgApp.class)
public class ActiveSubstanceResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_INGREDIENT_ID = "AAAAAAAAAA";
    private static final String UPDATED_INGREDIENT_ID = "BBBBBBBBBB";

    private static final String DEFAULT_NOTES = "AAAAAAAAAA";
    private static final String UPDATED_NOTES = "BBBBBBBBBB";

    private static final String DEFAULT_AS_EQUIVALENT = "AAAAAAAAAA";
    private static final String UPDATED_AS_EQUIVALENT = "BBBBBBBBBB";

    private static final String DEFAULT_ADDITIONAL_INFO = "AAAAAAAAAA";
    private static final String UPDATED_ADDITIONAL_INFO = "BBBBBBBBBB";

    private static final Double DEFAULT_AMOUNT = 1D;
    private static final Double UPDATED_AMOUNT = 2D;

    private static final Double DEFAULT_AMOUNT_EQ = 1D;
    private static final Double UPDATED_AMOUNT_EQ = 2D;

    private static final String DEFAULT_NON_NUMERICAL_AMOUNT = "AAAAAAAAAA";
    private static final String UPDATED_NON_NUMERICAL_AMOUNT = "BBBBBBBBBB";

    private static final String DEFAULT_NON_NUMERICAL_AMOUNT_EQ = "AAAAAAAAAA";
    private static final String UPDATED_NON_NUMERICAL_AMOUNT_EQ = "BBBBBBBBBB";

    private static final String DEFAULT_UNIT_ID = "AAAAAAAAAA";
    private static final String UPDATED_UNIT_ID = "BBBBBBBBBB";

    private static final String DEFAULT_UNIT = "AAAAAAAAAA";
    private static final String UPDATED_UNIT = "BBBBBBBBBB";

    private static final String DEFAULT_UNIT_EQ = "AAAAAAAAAA";
    private static final String UPDATED_UNIT_EQ = "BBBBBBBBBB";

    private static final String DEFAULT_UNIT_EQ_ID = "AAAAAAAAAA";
    private static final String UPDATED_UNIT_EQ_ID = "BBBBBBBBBB";

    private static final String DEFAULT_PC_ID = "AAAAAAAAAA";
    private static final String UPDATED_PC_ID = "BBBBBBBBBB";

    private static final String DEFAULT_PCC_ID = "AAAAAAAAAA";
    private static final String UPDATED_PCC_ID = "BBBBBBBBBB";

    private static final String DEFAULT_PCC_EN = "AAAAAAAAAA";
    private static final String UPDATED_PCC_EN = "BBBBBBBBBB";

    private static final String DEFAULT_INGREDIENT_EQ_ID = "AAAAAAAAAA";
    private static final String UPDATED_INGREDIENT_EQ_ID = "BBBBBBBBBB";

    @Autowired
    private ActiveSubstanceRepository activeSubstanceRepository;

    @Autowired
    private ActiveSubstanceService activeSubstanceService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restActiveSubstanceMockMvc;

    private ActiveSubstance activeSubstance;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ActiveSubstanceResource activeSubstanceResource = new ActiveSubstanceResource(activeSubstanceService);
        this.restActiveSubstanceMockMvc = MockMvcBuilders.standaloneSetup(activeSubstanceResource)
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
    public static ActiveSubstance createEntity() {
        ActiveSubstance activeSubstance = new ActiveSubstance()
            .name(DEFAULT_NAME)
            .ingredientId(DEFAULT_INGREDIENT_ID)
            .notes(DEFAULT_NOTES)
            .asEquivalent(DEFAULT_AS_EQUIVALENT)
            .additionalInfo(DEFAULT_ADDITIONAL_INFO)
            .amount(DEFAULT_AMOUNT)
            .amountEq(DEFAULT_AMOUNT_EQ)
            .nonNumericalAmount(DEFAULT_NON_NUMERICAL_AMOUNT)
            .nonNumericalAmountEq(DEFAULT_NON_NUMERICAL_AMOUNT_EQ)
            .unitId(DEFAULT_UNIT_ID)
            .unit(DEFAULT_UNIT)
            .unitEq(DEFAULT_UNIT_EQ)
            .unitEqId(DEFAULT_UNIT_EQ_ID)
            .pcId(DEFAULT_PC_ID)
            .pccId(DEFAULT_PCC_ID)
            .pccEn(DEFAULT_PCC_EN)
            .ingredientEqId(DEFAULT_INGREDIENT_EQ_ID);
        return activeSubstance;
    }

    @Before
    public void initTest() {
        activeSubstanceRepository.deleteAll();
        activeSubstance = createEntity();
    }

    @Test
    public void createActiveSubstance() throws Exception {
        int databaseSizeBeforeCreate = activeSubstanceRepository.findAll().size();

        // Create the ActiveSubstance
        restActiveSubstanceMockMvc.perform(post("/api/active-substances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(activeSubstance)))
            .andExpect(status().isCreated());

        // Validate the ActiveSubstance in the database
        List<ActiveSubstance> activeSubstanceList = activeSubstanceRepository.findAll();
        assertThat(activeSubstanceList).hasSize(databaseSizeBeforeCreate + 1);
        ActiveSubstance testActiveSubstance = activeSubstanceList.get(activeSubstanceList.size() - 1);
        assertThat(testActiveSubstance.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testActiveSubstance.getIngredientId()).isEqualTo(DEFAULT_INGREDIENT_ID);
        assertThat(testActiveSubstance.getNotes()).isEqualTo(DEFAULT_NOTES);
        assertThat(testActiveSubstance.getAsEquivalent()).isEqualTo(DEFAULT_AS_EQUIVALENT);
        assertThat(testActiveSubstance.getAdditionalInfo()).isEqualTo(DEFAULT_ADDITIONAL_INFO);
        assertThat(testActiveSubstance.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testActiveSubstance.getAmountEq()).isEqualTo(DEFAULT_AMOUNT_EQ);
        assertThat(testActiveSubstance.getNonNumericalAmount()).isEqualTo(DEFAULT_NON_NUMERICAL_AMOUNT);
        assertThat(testActiveSubstance.getNonNumericalAmountEq()).isEqualTo(DEFAULT_NON_NUMERICAL_AMOUNT_EQ);
        assertThat(testActiveSubstance.getUnitId()).isEqualTo(DEFAULT_UNIT_ID);
        assertThat(testActiveSubstance.getUnit()).isEqualTo(DEFAULT_UNIT);
        assertThat(testActiveSubstance.getUnitEq()).isEqualTo(DEFAULT_UNIT_EQ);
        assertThat(testActiveSubstance.getUnitEqId()).isEqualTo(DEFAULT_UNIT_EQ_ID);
        assertThat(testActiveSubstance.getPcId()).isEqualTo(DEFAULT_PC_ID);
        assertThat(testActiveSubstance.getPccId()).isEqualTo(DEFAULT_PCC_ID);
        assertThat(testActiveSubstance.getPccEn()).isEqualTo(DEFAULT_PCC_EN);
        assertThat(testActiveSubstance.getIngredientEqId()).isEqualTo(DEFAULT_INGREDIENT_EQ_ID);
    }

    @Test
    public void createActiveSubstanceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = activeSubstanceRepository.findAll().size();

        // Create the ActiveSubstance with an existing ID
        activeSubstance.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restActiveSubstanceMockMvc.perform(post("/api/active-substances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(activeSubstance)))
            .andExpect(status().isBadRequest());

        // Validate the ActiveSubstance in the database
        List<ActiveSubstance> activeSubstanceList = activeSubstanceRepository.findAll();
        assertThat(activeSubstanceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void checkIngredientEqIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = activeSubstanceRepository.findAll().size();
        // set the field null
        activeSubstance.setIngredientEqId(null);

        // Create the ActiveSubstance, which fails.

        restActiveSubstanceMockMvc.perform(post("/api/active-substances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(activeSubstance)))
            .andExpect(status().isBadRequest());

        List<ActiveSubstance> activeSubstanceList = activeSubstanceRepository.findAll();
        assertThat(activeSubstanceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllActiveSubstances() throws Exception {
        // Initialize the database
        activeSubstanceRepository.save(activeSubstance);

        // Get all the activeSubstanceList
        restActiveSubstanceMockMvc.perform(get("/api/active-substances?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(activeSubstance.getId())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].ingredientId").value(hasItem(DEFAULT_INGREDIENT_ID.toString())))
            .andExpect(jsonPath("$.[*].notes").value(hasItem(DEFAULT_NOTES.toString())))
            .andExpect(jsonPath("$.[*].asEquivalent").value(hasItem(DEFAULT_AS_EQUIVALENT.toString())))
            .andExpect(jsonPath("$.[*].additionalInfo").value(hasItem(DEFAULT_ADDITIONAL_INFO.toString())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].amountEq").value(hasItem(DEFAULT_AMOUNT_EQ.doubleValue())))
            .andExpect(jsonPath("$.[*].nonNumericalAmount").value(hasItem(DEFAULT_NON_NUMERICAL_AMOUNT.toString())))
            .andExpect(jsonPath("$.[*].nonNumericalAmountEq").value(hasItem(DEFAULT_NON_NUMERICAL_AMOUNT_EQ.toString())))
            .andExpect(jsonPath("$.[*].unitId").value(hasItem(DEFAULT_UNIT_ID.toString())))
            .andExpect(jsonPath("$.[*].unit").value(hasItem(DEFAULT_UNIT.toString())))
            .andExpect(jsonPath("$.[*].unitEq").value(hasItem(DEFAULT_UNIT_EQ.toString())))
            .andExpect(jsonPath("$.[*].unitEqId").value(hasItem(DEFAULT_UNIT_EQ_ID.toString())))
            .andExpect(jsonPath("$.[*].pcId").value(hasItem(DEFAULT_PC_ID.toString())))
            .andExpect(jsonPath("$.[*].pccId").value(hasItem(DEFAULT_PCC_ID.toString())))
            .andExpect(jsonPath("$.[*].pccEn").value(hasItem(DEFAULT_PCC_EN.toString())))
            .andExpect(jsonPath("$.[*].ingredientEqId").value(hasItem(DEFAULT_INGREDIENT_EQ_ID.toString())));
    }

    @Test
    public void getActiveSubstance() throws Exception {
        // Initialize the database
        activeSubstanceRepository.save(activeSubstance);

        // Get the activeSubstance
        restActiveSubstanceMockMvc.perform(get("/api/active-substances/{id}", activeSubstance.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(activeSubstance.getId()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.ingredientId").value(DEFAULT_INGREDIENT_ID.toString()))
            .andExpect(jsonPath("$.notes").value(DEFAULT_NOTES.toString()))
            .andExpect(jsonPath("$.asEquivalent").value(DEFAULT_AS_EQUIVALENT.toString()))
            .andExpect(jsonPath("$.additionalInfo").value(DEFAULT_ADDITIONAL_INFO.toString()))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.amountEq").value(DEFAULT_AMOUNT_EQ.doubleValue()))
            .andExpect(jsonPath("$.nonNumericalAmount").value(DEFAULT_NON_NUMERICAL_AMOUNT.toString()))
            .andExpect(jsonPath("$.nonNumericalAmountEq").value(DEFAULT_NON_NUMERICAL_AMOUNT_EQ.toString()))
            .andExpect(jsonPath("$.unitId").value(DEFAULT_UNIT_ID.toString()))
            .andExpect(jsonPath("$.unit").value(DEFAULT_UNIT.toString()))
            .andExpect(jsonPath("$.unitEq").value(DEFAULT_UNIT_EQ.toString()))
            .andExpect(jsonPath("$.unitEqId").value(DEFAULT_UNIT_EQ_ID.toString()))
            .andExpect(jsonPath("$.pcId").value(DEFAULT_PC_ID.toString()))
            .andExpect(jsonPath("$.pccId").value(DEFAULT_PCC_ID.toString()))
            .andExpect(jsonPath("$.pccEn").value(DEFAULT_PCC_EN.toString()))
            .andExpect(jsonPath("$.ingredientEqId").value(DEFAULT_INGREDIENT_EQ_ID.toString()));
    }

    @Test
    public void getNonExistingActiveSubstance() throws Exception {
        // Get the activeSubstance
        restActiveSubstanceMockMvc.perform(get("/api/active-substances/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateActiveSubstance() throws Exception {
        // Initialize the database
        activeSubstanceService.save(activeSubstance);

        int databaseSizeBeforeUpdate = activeSubstanceRepository.findAll().size();

        // Update the activeSubstance
        ActiveSubstance updatedActiveSubstance = activeSubstanceRepository.findOne(activeSubstance.getId());
        updatedActiveSubstance
            .name(UPDATED_NAME)
            .ingredientId(UPDATED_INGREDIENT_ID)
            .notes(UPDATED_NOTES)
            .asEquivalent(UPDATED_AS_EQUIVALENT)
            .additionalInfo(UPDATED_ADDITIONAL_INFO)
            .amount(UPDATED_AMOUNT)
            .amountEq(UPDATED_AMOUNT_EQ)
            .nonNumericalAmount(UPDATED_NON_NUMERICAL_AMOUNT)
            .nonNumericalAmountEq(UPDATED_NON_NUMERICAL_AMOUNT_EQ)
            .unitId(UPDATED_UNIT_ID)
            .unit(UPDATED_UNIT)
            .unitEq(UPDATED_UNIT_EQ)
            .unitEqId(UPDATED_UNIT_EQ_ID)
            .pcId(UPDATED_PC_ID)
            .pccId(UPDATED_PCC_ID)
            .pccEn(UPDATED_PCC_EN)
            .ingredientEqId(UPDATED_INGREDIENT_EQ_ID);

        restActiveSubstanceMockMvc.perform(put("/api/active-substances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedActiveSubstance)))
            .andExpect(status().isOk());

        // Validate the ActiveSubstance in the database
        List<ActiveSubstance> activeSubstanceList = activeSubstanceRepository.findAll();
        assertThat(activeSubstanceList).hasSize(databaseSizeBeforeUpdate);
        ActiveSubstance testActiveSubstance = activeSubstanceList.get(activeSubstanceList.size() - 1);
        assertThat(testActiveSubstance.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testActiveSubstance.getIngredientId()).isEqualTo(UPDATED_INGREDIENT_ID);
        assertThat(testActiveSubstance.getNotes()).isEqualTo(UPDATED_NOTES);
        assertThat(testActiveSubstance.getAsEquivalent()).isEqualTo(UPDATED_AS_EQUIVALENT);
        assertThat(testActiveSubstance.getAdditionalInfo()).isEqualTo(UPDATED_ADDITIONAL_INFO);
        assertThat(testActiveSubstance.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testActiveSubstance.getAmountEq()).isEqualTo(UPDATED_AMOUNT_EQ);
        assertThat(testActiveSubstance.getNonNumericalAmount()).isEqualTo(UPDATED_NON_NUMERICAL_AMOUNT);
        assertThat(testActiveSubstance.getNonNumericalAmountEq()).isEqualTo(UPDATED_NON_NUMERICAL_AMOUNT_EQ);
        assertThat(testActiveSubstance.getUnitId()).isEqualTo(UPDATED_UNIT_ID);
        assertThat(testActiveSubstance.getUnit()).isEqualTo(UPDATED_UNIT);
        assertThat(testActiveSubstance.getUnitEq()).isEqualTo(UPDATED_UNIT_EQ);
        assertThat(testActiveSubstance.getUnitEqId()).isEqualTo(UPDATED_UNIT_EQ_ID);
        assertThat(testActiveSubstance.getPcId()).isEqualTo(UPDATED_PC_ID);
        assertThat(testActiveSubstance.getPccId()).isEqualTo(UPDATED_PCC_ID);
        assertThat(testActiveSubstance.getPccEn()).isEqualTo(UPDATED_PCC_EN);
        assertThat(testActiveSubstance.getIngredientEqId()).isEqualTo(UPDATED_INGREDIENT_EQ_ID);
    }

    @Test
    public void updateNonExistingActiveSubstance() throws Exception {
        int databaseSizeBeforeUpdate = activeSubstanceRepository.findAll().size();

        // Create the ActiveSubstance

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restActiveSubstanceMockMvc.perform(put("/api/active-substances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(activeSubstance)))
            .andExpect(status().isCreated());

        // Validate the ActiveSubstance in the database
        List<ActiveSubstance> activeSubstanceList = activeSubstanceRepository.findAll();
        assertThat(activeSubstanceList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteActiveSubstance() throws Exception {
        // Initialize the database
        activeSubstanceService.save(activeSubstance);

        int databaseSizeBeforeDelete = activeSubstanceRepository.findAll().size();

        // Get the activeSubstance
        restActiveSubstanceMockMvc.perform(delete("/api/active-substances/{id}", activeSubstance.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ActiveSubstance> activeSubstanceList = activeSubstanceRepository.findAll();
        assertThat(activeSubstanceList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ActiveSubstance.class);
        ActiveSubstance activeSubstance1 = new ActiveSubstance();
        activeSubstance1.setId("id1");
        ActiveSubstance activeSubstance2 = new ActiveSubstance();
        activeSubstance2.setId(activeSubstance1.getId());
        assertThat(activeSubstance1).isEqualTo(activeSubstance2);
        activeSubstance2.setId("id2");
        assertThat(activeSubstance1).isNotEqualTo(activeSubstance2);
        activeSubstance1.setId(null);
        assertThat(activeSubstance1).isNotEqualTo(activeSubstance2);
    }
}
