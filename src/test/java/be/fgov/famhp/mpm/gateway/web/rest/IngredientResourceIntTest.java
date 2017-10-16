package be.fgov.famhp.mpm.gateway.web.rest;

import be.fgov.famhp.mpm.gateway.MpmNgApp;

import be.fgov.famhp.mpm.gateway.domain.Ingredient;
import be.fgov.famhp.mpm.gateway.repository.IngredientRepository;
import be.fgov.famhp.mpm.gateway.service.IngredientService;
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

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import be.fgov.famhp.mpm.gateway.domain.enumeration.IngredientType;
/**
 * Test class for the IngredientResource REST controller.
 *
 * @see IngredientResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MpmNgApp.class)
public class IngredientResourceIntTest {

    private static final String DEFAULT_EN = "AAAAAAAAAA";
    private static final String UPDATED_EN = "BBBBBBBBBB";

    private static final String DEFAULT_FR = "AAAAAAAAAA";
    private static final String UPDATED_FR = "BBBBBBBBBB";

    private static final String DEFAULT_DE = "AAAAAAAAAA";
    private static final String UPDATED_DE = "BBBBBBBBBB";

    private static final String DEFAULT_NL = "AAAAAAAAAA";
    private static final String UPDATED_NL = "BBBBBBBBBB";

    private static final String DEFAULT_CATEGORY = "AAAAAAAAAA";
    private static final String UPDATED_CATEGORY = "BBBBBBBBBB";

    private static final String DEFAULT_NOTES = "AAAAAAAAAA";
    private static final String UPDATED_NOTES = "BBBBBBBBBB";

    private static final String DEFAULT_SNOMED_CT_ID = "AAAAAAAAAA";
    private static final String UPDATED_SNOMED_CT_ID = "BBBBBBBBBB";

    private static final String DEFAULT_SYNONYMS = "AAAAAAAAAA";
    private static final String UPDATED_SYNONYMS = "BBBBBBBBBB";

    private static final String DEFAULT_CAS_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CAS_CODE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_DEPRECATED = false;
    private static final Boolean UPDATED_DEPRECATED = true;

    private static final Boolean DEFAULT_INNOVATION = false;
    private static final Boolean UPDATED_INNOVATION = true;

    private static final LocalDate DEFAULT_CREATION_DATE_USAGE_HUM = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATION_DATE_USAGE_HUM = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_CREATION_DATE_USAGE_VET = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATION_DATE_USAGE_VET = LocalDate.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_RESIDUAL_PRODUCT = false;
    private static final Boolean UPDATED_RESIDUAL_PRODUCT = true;

    private static final IngredientType DEFAULT_TYPE = IngredientType.ACTIVE_SUBSTANCE;
    private static final IngredientType UPDATED_TYPE = IngredientType.EXCIPIENT;

    @Autowired
    private IngredientRepository ingredientRepository;

    @Autowired
    private IngredientService ingredientService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restIngredientMockMvc;

    private Ingredient ingredient;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final IngredientResource ingredientResource = new IngredientResource(ingredientService);
        this.restIngredientMockMvc = MockMvcBuilders.standaloneSetup(ingredientResource)
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
    public static Ingredient createEntity() {
        Ingredient ingredient = new Ingredient()
            .en(DEFAULT_EN)
            .fr(DEFAULT_FR)
            .de(DEFAULT_DE)
            .nl(DEFAULT_NL)
            .category(DEFAULT_CATEGORY)
            .notes(DEFAULT_NOTES)
            .snomedCTId(DEFAULT_SNOMED_CT_ID)
            .synonyms(DEFAULT_SYNONYMS)
            .casCode(DEFAULT_CAS_CODE)
            .deprecated(DEFAULT_DEPRECATED)
            .innovation(DEFAULT_INNOVATION)
            .creationDateUsageHum(DEFAULT_CREATION_DATE_USAGE_HUM)
            .creationDateUsageVet(DEFAULT_CREATION_DATE_USAGE_VET)
            .residualProduct(DEFAULT_RESIDUAL_PRODUCT)
            .type(DEFAULT_TYPE);
        return ingredient;
    }

    @Before
    public void initTest() {
        ingredientRepository.deleteAll();
        ingredient = createEntity();
    }

    @Test
    public void createIngredient() throws Exception {
        int databaseSizeBeforeCreate = ingredientRepository.findAll().size();

        // Create the Ingredient
        restIngredientMockMvc.perform(post("/api/ingredients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ingredient)))
            .andExpect(status().isCreated());

        // Validate the Ingredient in the database
        List<Ingredient> ingredientList = ingredientRepository.findAll();
        assertThat(ingredientList).hasSize(databaseSizeBeforeCreate + 1);
        Ingredient testIngredient = ingredientList.get(ingredientList.size() - 1);
        assertThat(testIngredient.getEn()).isEqualTo(DEFAULT_EN);
        assertThat(testIngredient.getFr()).isEqualTo(DEFAULT_FR);
        assertThat(testIngredient.getDe()).isEqualTo(DEFAULT_DE);
        assertThat(testIngredient.getNl()).isEqualTo(DEFAULT_NL);
        assertThat(testIngredient.getCategory()).isEqualTo(DEFAULT_CATEGORY);
        assertThat(testIngredient.getNotes()).isEqualTo(DEFAULT_NOTES);
        assertThat(testIngredient.getSnomedCTId()).isEqualTo(DEFAULT_SNOMED_CT_ID);
        assertThat(testIngredient.getSynonyms()).isEqualTo(DEFAULT_SYNONYMS);
        assertThat(testIngredient.getCasCode()).isEqualTo(DEFAULT_CAS_CODE);
        assertThat(testIngredient.isDeprecated()).isEqualTo(DEFAULT_DEPRECATED);
        assertThat(testIngredient.isInnovation()).isEqualTo(DEFAULT_INNOVATION);
        assertThat(testIngredient.getCreationDateUsageHum()).isEqualTo(DEFAULT_CREATION_DATE_USAGE_HUM);
        assertThat(testIngredient.getCreationDateUsageVet()).isEqualTo(DEFAULT_CREATION_DATE_USAGE_VET);
        assertThat(testIngredient.isResidualProduct()).isEqualTo(DEFAULT_RESIDUAL_PRODUCT);
        assertThat(testIngredient.getType()).isEqualTo(DEFAULT_TYPE);
    }

    @Test
    public void createIngredientWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = ingredientRepository.findAll().size();

        // Create the Ingredient with an existing ID
        ingredient.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restIngredientMockMvc.perform(post("/api/ingredients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ingredient)))
            .andExpect(status().isBadRequest());

        // Validate the Ingredient in the database
        List<Ingredient> ingredientList = ingredientRepository.findAll();
        assertThat(ingredientList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = ingredientRepository.findAll().size();
        // set the field null
        ingredient.setType(null);

        // Create the Ingredient, which fails.

        restIngredientMockMvc.perform(post("/api/ingredients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ingredient)))
            .andExpect(status().isBadRequest());

        List<Ingredient> ingredientList = ingredientRepository.findAll();
        assertThat(ingredientList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllIngredients() throws Exception {
        // Initialize the database
        ingredientRepository.save(ingredient);

        // Get all the ingredientList
        restIngredientMockMvc.perform(get("/api/ingredients?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ingredient.getId())))
            .andExpect(jsonPath("$.[*].en").value(hasItem(DEFAULT_EN.toString())))
            .andExpect(jsonPath("$.[*].fr").value(hasItem(DEFAULT_FR.toString())))
            .andExpect(jsonPath("$.[*].de").value(hasItem(DEFAULT_DE.toString())))
            .andExpect(jsonPath("$.[*].nl").value(hasItem(DEFAULT_NL.toString())))
            .andExpect(jsonPath("$.[*].category").value(hasItem(DEFAULT_CATEGORY.toString())))
            .andExpect(jsonPath("$.[*].notes").value(hasItem(DEFAULT_NOTES.toString())))
            .andExpect(jsonPath("$.[*].snomedCTId").value(hasItem(DEFAULT_SNOMED_CT_ID.toString())))
            .andExpect(jsonPath("$.[*].synonyms").value(hasItem(DEFAULT_SYNONYMS.toString())))
            .andExpect(jsonPath("$.[*].casCode").value(hasItem(DEFAULT_CAS_CODE.toString())))
            .andExpect(jsonPath("$.[*].deprecated").value(hasItem(DEFAULT_DEPRECATED.booleanValue())))
            .andExpect(jsonPath("$.[*].innovation").value(hasItem(DEFAULT_INNOVATION.booleanValue())))
            .andExpect(jsonPath("$.[*].creationDateUsageHum").value(hasItem(DEFAULT_CREATION_DATE_USAGE_HUM.toString())))
            .andExpect(jsonPath("$.[*].creationDateUsageVet").value(hasItem(DEFAULT_CREATION_DATE_USAGE_VET.toString())))
            .andExpect(jsonPath("$.[*].residualProduct").value(hasItem(DEFAULT_RESIDUAL_PRODUCT.booleanValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())));
    }

    @Test
    public void getIngredient() throws Exception {
        // Initialize the database
        ingredientRepository.save(ingredient);

        // Get the ingredient
        restIngredientMockMvc.perform(get("/api/ingredients/{id}", ingredient.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(ingredient.getId()))
            .andExpect(jsonPath("$.en").value(DEFAULT_EN.toString()))
            .andExpect(jsonPath("$.fr").value(DEFAULT_FR.toString()))
            .andExpect(jsonPath("$.de").value(DEFAULT_DE.toString()))
            .andExpect(jsonPath("$.nl").value(DEFAULT_NL.toString()))
            .andExpect(jsonPath("$.category").value(DEFAULT_CATEGORY.toString()))
            .andExpect(jsonPath("$.notes").value(DEFAULT_NOTES.toString()))
            .andExpect(jsonPath("$.snomedCTId").value(DEFAULT_SNOMED_CT_ID.toString()))
            .andExpect(jsonPath("$.synonyms").value(DEFAULT_SYNONYMS.toString()))
            .andExpect(jsonPath("$.casCode").value(DEFAULT_CAS_CODE.toString()))
            .andExpect(jsonPath("$.deprecated").value(DEFAULT_DEPRECATED.booleanValue()))
            .andExpect(jsonPath("$.innovation").value(DEFAULT_INNOVATION.booleanValue()))
            .andExpect(jsonPath("$.creationDateUsageHum").value(DEFAULT_CREATION_DATE_USAGE_HUM.toString()))
            .andExpect(jsonPath("$.creationDateUsageVet").value(DEFAULT_CREATION_DATE_USAGE_VET.toString()))
            .andExpect(jsonPath("$.residualProduct").value(DEFAULT_RESIDUAL_PRODUCT.booleanValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()));
    }

    @Test
    public void getNonExistingIngredient() throws Exception {
        // Get the ingredient
        restIngredientMockMvc.perform(get("/api/ingredients/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateIngredient() throws Exception {
        // Initialize the database
        ingredientService.save(ingredient);

        int databaseSizeBeforeUpdate = ingredientRepository.findAll().size();

        // Update the ingredient
        Ingredient updatedIngredient = ingredientRepository.findOne(ingredient.getId());
        updatedIngredient
            .en(UPDATED_EN)
            .fr(UPDATED_FR)
            .de(UPDATED_DE)
            .nl(UPDATED_NL)
            .category(UPDATED_CATEGORY)
            .notes(UPDATED_NOTES)
            .snomedCTId(UPDATED_SNOMED_CT_ID)
            .synonyms(UPDATED_SYNONYMS)
            .casCode(UPDATED_CAS_CODE)
            .deprecated(UPDATED_DEPRECATED)
            .innovation(UPDATED_INNOVATION)
            .creationDateUsageHum(UPDATED_CREATION_DATE_USAGE_HUM)
            .creationDateUsageVet(UPDATED_CREATION_DATE_USAGE_VET)
            .residualProduct(UPDATED_RESIDUAL_PRODUCT)
            .type(UPDATED_TYPE);

        restIngredientMockMvc.perform(put("/api/ingredients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedIngredient)))
            .andExpect(status().isOk());

        // Validate the Ingredient in the database
        List<Ingredient> ingredientList = ingredientRepository.findAll();
        assertThat(ingredientList).hasSize(databaseSizeBeforeUpdate);
        Ingredient testIngredient = ingredientList.get(ingredientList.size() - 1);
        assertThat(testIngredient.getEn()).isEqualTo(UPDATED_EN);
        assertThat(testIngredient.getFr()).isEqualTo(UPDATED_FR);
        assertThat(testIngredient.getDe()).isEqualTo(UPDATED_DE);
        assertThat(testIngredient.getNl()).isEqualTo(UPDATED_NL);
        assertThat(testIngredient.getCategory()).isEqualTo(UPDATED_CATEGORY);
        assertThat(testIngredient.getNotes()).isEqualTo(UPDATED_NOTES);
        assertThat(testIngredient.getSnomedCTId()).isEqualTo(UPDATED_SNOMED_CT_ID);
        assertThat(testIngredient.getSynonyms()).isEqualTo(UPDATED_SYNONYMS);
        assertThat(testIngredient.getCasCode()).isEqualTo(UPDATED_CAS_CODE);
        assertThat(testIngredient.isDeprecated()).isEqualTo(UPDATED_DEPRECATED);
        assertThat(testIngredient.isInnovation()).isEqualTo(UPDATED_INNOVATION);
        assertThat(testIngredient.getCreationDateUsageHum()).isEqualTo(UPDATED_CREATION_DATE_USAGE_HUM);
        assertThat(testIngredient.getCreationDateUsageVet()).isEqualTo(UPDATED_CREATION_DATE_USAGE_VET);
        assertThat(testIngredient.isResidualProduct()).isEqualTo(UPDATED_RESIDUAL_PRODUCT);
        assertThat(testIngredient.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    public void updateNonExistingIngredient() throws Exception {
        int databaseSizeBeforeUpdate = ingredientRepository.findAll().size();

        // Create the Ingredient

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restIngredientMockMvc.perform(put("/api/ingredients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ingredient)))
            .andExpect(status().isCreated());

        // Validate the Ingredient in the database
        List<Ingredient> ingredientList = ingredientRepository.findAll();
        assertThat(ingredientList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteIngredient() throws Exception {
        // Initialize the database
        ingredientService.save(ingredient);

        int databaseSizeBeforeDelete = ingredientRepository.findAll().size();

        // Get the ingredient
        restIngredientMockMvc.perform(delete("/api/ingredients/{id}", ingredient.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Ingredient> ingredientList = ingredientRepository.findAll();
        assertThat(ingredientList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Ingredient.class);
        Ingredient ingredient1 = new Ingredient();
        ingredient1.setId("id1");
        Ingredient ingredient2 = new Ingredient();
        ingredient2.setId(ingredient1.getId());
        assertThat(ingredient1).isEqualTo(ingredient2);
        ingredient2.setId("id2");
        assertThat(ingredient1).isNotEqualTo(ingredient2);
        ingredient1.setId(null);
        assertThat(ingredient1).isNotEqualTo(ingredient2);
    }
}
