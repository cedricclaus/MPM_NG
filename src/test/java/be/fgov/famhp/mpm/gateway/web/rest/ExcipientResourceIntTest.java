package be.fgov.famhp.mpm.gateway.web.rest;

import be.fgov.famhp.mpm.gateway.MpmNgApp;

import be.fgov.famhp.mpm.gateway.domain.Excipient;
import be.fgov.famhp.mpm.gateway.repository.ExcipientRepository;
import be.fgov.famhp.mpm.gateway.service.ExcipientService;
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
 * Test class for the ExcipientResource REST controller.
 *
 * @see ExcipientResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MpmNgApp.class)
public class ExcipientResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_NOTES = "AAAAAAAAAA";
    private static final String UPDATED_NOTES = "BBBBBBBBBB";

    private static final String DEFAULT_ADDITIONAL_INFO = "AAAAAAAAAA";
    private static final String UPDATED_ADDITIONAL_INFO = "BBBBBBBBBB";

    private static final Double DEFAULT_AMOUNT = 1D;
    private static final Double UPDATED_AMOUNT = 2D;

    private static final String DEFAULT_NON_NUMERICAL_AMOUNT = "AAAAAAAAAA";
    private static final String UPDATED_NON_NUMERICAL_AMOUNT = "BBBBBBBBBB";

    private static final String DEFAULT_UNIT_ID = "AAAAAAAAAA";
    private static final String UPDATED_UNIT_ID = "BBBBBBBBBB";

    private static final String DEFAULT_UNIT = "AAAAAAAAAA";
    private static final String UPDATED_UNIT = "BBBBBBBBBB";

    private static final String DEFAULT_PC_ID = "AAAAAAAAAA";
    private static final String UPDATED_PC_ID = "BBBBBBBBBB";

    private static final String DEFAULT_PCC_ID = "AAAAAAAAAA";
    private static final String UPDATED_PCC_ID = "BBBBBBBBBB";

    private static final String DEFAULT_PCC_EN = "AAAAAAAAAA";
    private static final String UPDATED_PCC_EN = "BBBBBBBBBB";

    private static final String DEFAULT_INGREDIENT_ID = "AAAAAAAAAA";
    private static final String UPDATED_INGREDIENT_ID = "BBBBBBBBBB";

    private static final String DEFAULT_BELONGS_TO = "AAAAAAAAAA";
    private static final String UPDATED_BELONGS_TO = "BBBBBBBBBB";

    private static final Boolean DEFAULT_KNOWN_EFFECT = false;
    private static final Boolean UPDATED_KNOWN_EFFECT = true;

    @Autowired
    private ExcipientRepository excipientRepository;

    @Autowired
    private ExcipientService excipientService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restExcipientMockMvc;

    private Excipient excipient;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ExcipientResource excipientResource = new ExcipientResource(excipientService);
        this.restExcipientMockMvc = MockMvcBuilders.standaloneSetup(excipientResource)
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
    public static Excipient createEntity() {
        Excipient excipient = new Excipient()
            .name(DEFAULT_NAME)
            .notes(DEFAULT_NOTES)
            .additionalInfo(DEFAULT_ADDITIONAL_INFO)
            .amount(DEFAULT_AMOUNT)
            .nonNumericalAmount(DEFAULT_NON_NUMERICAL_AMOUNT)
            .unitId(DEFAULT_UNIT_ID)
            .unit(DEFAULT_UNIT)
            .pcId(DEFAULT_PC_ID)
            .pccId(DEFAULT_PCC_ID)
            .pccEn(DEFAULT_PCC_EN)
            .ingredientId(DEFAULT_INGREDIENT_ID)
            .belongsTo(DEFAULT_BELONGS_TO)
            .knownEffect(DEFAULT_KNOWN_EFFECT);
        return excipient;
    }

    @Before
    public void initTest() {
        excipientRepository.deleteAll();
        excipient = createEntity();
    }

    @Test
    public void createExcipient() throws Exception {
        int databaseSizeBeforeCreate = excipientRepository.findAll().size();

        // Create the Excipient
        restExcipientMockMvc.perform(post("/api/excipients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(excipient)))
            .andExpect(status().isCreated());

        // Validate the Excipient in the database
        List<Excipient> excipientList = excipientRepository.findAll();
        assertThat(excipientList).hasSize(databaseSizeBeforeCreate + 1);
        Excipient testExcipient = excipientList.get(excipientList.size() - 1);
        assertThat(testExcipient.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testExcipient.getNotes()).isEqualTo(DEFAULT_NOTES);
        assertThat(testExcipient.getAdditionalInfo()).isEqualTo(DEFAULT_ADDITIONAL_INFO);
        assertThat(testExcipient.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testExcipient.getNonNumericalAmount()).isEqualTo(DEFAULT_NON_NUMERICAL_AMOUNT);
        assertThat(testExcipient.getUnitId()).isEqualTo(DEFAULT_UNIT_ID);
        assertThat(testExcipient.getUnit()).isEqualTo(DEFAULT_UNIT);
        assertThat(testExcipient.getPcId()).isEqualTo(DEFAULT_PC_ID);
        assertThat(testExcipient.getPccId()).isEqualTo(DEFAULT_PCC_ID);
        assertThat(testExcipient.getPccEn()).isEqualTo(DEFAULT_PCC_EN);
        assertThat(testExcipient.getIngredientId()).isEqualTo(DEFAULT_INGREDIENT_ID);
        assertThat(testExcipient.getBelongsTo()).isEqualTo(DEFAULT_BELONGS_TO);
        assertThat(testExcipient.isKnownEffect()).isEqualTo(DEFAULT_KNOWN_EFFECT);
    }

    @Test
    public void createExcipientWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = excipientRepository.findAll().size();

        // Create the Excipient with an existing ID
        excipient.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restExcipientMockMvc.perform(post("/api/excipients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(excipient)))
            .andExpect(status().isBadRequest());

        // Validate the Excipient in the database
        List<Excipient> excipientList = excipientRepository.findAll();
        assertThat(excipientList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void checkIngredientIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = excipientRepository.findAll().size();
        // set the field null
        excipient.setIngredientId(null);

        // Create the Excipient, which fails.

        restExcipientMockMvc.perform(post("/api/excipients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(excipient)))
            .andExpect(status().isBadRequest());

        List<Excipient> excipientList = excipientRepository.findAll();
        assertThat(excipientList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllExcipients() throws Exception {
        // Initialize the database
        excipientRepository.save(excipient);

        // Get all the excipientList
        restExcipientMockMvc.perform(get("/api/excipients?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(excipient.getId())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].notes").value(hasItem(DEFAULT_NOTES.toString())))
            .andExpect(jsonPath("$.[*].additionalInfo").value(hasItem(DEFAULT_ADDITIONAL_INFO.toString())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].nonNumericalAmount").value(hasItem(DEFAULT_NON_NUMERICAL_AMOUNT.toString())))
            .andExpect(jsonPath("$.[*].unitId").value(hasItem(DEFAULT_UNIT_ID.toString())))
            .andExpect(jsonPath("$.[*].unit").value(hasItem(DEFAULT_UNIT.toString())))
            .andExpect(jsonPath("$.[*].pcId").value(hasItem(DEFAULT_PC_ID.toString())))
            .andExpect(jsonPath("$.[*].pccId").value(hasItem(DEFAULT_PCC_ID.toString())))
            .andExpect(jsonPath("$.[*].pccEn").value(hasItem(DEFAULT_PCC_EN.toString())))
            .andExpect(jsonPath("$.[*].ingredientId").value(hasItem(DEFAULT_INGREDIENT_ID.toString())))
            .andExpect(jsonPath("$.[*].belongsTo").value(hasItem(DEFAULT_BELONGS_TO.toString())))
            .andExpect(jsonPath("$.[*].knownEffect").value(hasItem(DEFAULT_KNOWN_EFFECT.booleanValue())));
    }

    @Test
    public void getExcipient() throws Exception {
        // Initialize the database
        excipientRepository.save(excipient);

        // Get the excipient
        restExcipientMockMvc.perform(get("/api/excipients/{id}", excipient.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(excipient.getId()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.notes").value(DEFAULT_NOTES.toString()))
            .andExpect(jsonPath("$.additionalInfo").value(DEFAULT_ADDITIONAL_INFO.toString()))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.nonNumericalAmount").value(DEFAULT_NON_NUMERICAL_AMOUNT.toString()))
            .andExpect(jsonPath("$.unitId").value(DEFAULT_UNIT_ID.toString()))
            .andExpect(jsonPath("$.unit").value(DEFAULT_UNIT.toString()))
            .andExpect(jsonPath("$.pcId").value(DEFAULT_PC_ID.toString()))
            .andExpect(jsonPath("$.pccId").value(DEFAULT_PCC_ID.toString()))
            .andExpect(jsonPath("$.pccEn").value(DEFAULT_PCC_EN.toString()))
            .andExpect(jsonPath("$.ingredientId").value(DEFAULT_INGREDIENT_ID.toString()))
            .andExpect(jsonPath("$.belongsTo").value(DEFAULT_BELONGS_TO.toString()))
            .andExpect(jsonPath("$.knownEffect").value(DEFAULT_KNOWN_EFFECT.booleanValue()));
    }

    @Test
    public void getNonExistingExcipient() throws Exception {
        // Get the excipient
        restExcipientMockMvc.perform(get("/api/excipients/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateExcipient() throws Exception {
        // Initialize the database
        excipientService.save(excipient);

        int databaseSizeBeforeUpdate = excipientRepository.findAll().size();

        // Update the excipient
        Excipient updatedExcipient = excipientRepository.findOne(excipient.getId());
        updatedExcipient
            .name(UPDATED_NAME)
            .notes(UPDATED_NOTES)
            .additionalInfo(UPDATED_ADDITIONAL_INFO)
            .amount(UPDATED_AMOUNT)
            .nonNumericalAmount(UPDATED_NON_NUMERICAL_AMOUNT)
            .unitId(UPDATED_UNIT_ID)
            .unit(UPDATED_UNIT)
            .pcId(UPDATED_PC_ID)
            .pccId(UPDATED_PCC_ID)
            .pccEn(UPDATED_PCC_EN)
            .ingredientId(UPDATED_INGREDIENT_ID)
            .belongsTo(UPDATED_BELONGS_TO)
            .knownEffect(UPDATED_KNOWN_EFFECT);

        restExcipientMockMvc.perform(put("/api/excipients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedExcipient)))
            .andExpect(status().isOk());

        // Validate the Excipient in the database
        List<Excipient> excipientList = excipientRepository.findAll();
        assertThat(excipientList).hasSize(databaseSizeBeforeUpdate);
        Excipient testExcipient = excipientList.get(excipientList.size() - 1);
        assertThat(testExcipient.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testExcipient.getNotes()).isEqualTo(UPDATED_NOTES);
        assertThat(testExcipient.getAdditionalInfo()).isEqualTo(UPDATED_ADDITIONAL_INFO);
        assertThat(testExcipient.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testExcipient.getNonNumericalAmount()).isEqualTo(UPDATED_NON_NUMERICAL_AMOUNT);
        assertThat(testExcipient.getUnitId()).isEqualTo(UPDATED_UNIT_ID);
        assertThat(testExcipient.getUnit()).isEqualTo(UPDATED_UNIT);
        assertThat(testExcipient.getPcId()).isEqualTo(UPDATED_PC_ID);
        assertThat(testExcipient.getPccId()).isEqualTo(UPDATED_PCC_ID);
        assertThat(testExcipient.getPccEn()).isEqualTo(UPDATED_PCC_EN);
        assertThat(testExcipient.getIngredientId()).isEqualTo(UPDATED_INGREDIENT_ID);
        assertThat(testExcipient.getBelongsTo()).isEqualTo(UPDATED_BELONGS_TO);
        assertThat(testExcipient.isKnownEffect()).isEqualTo(UPDATED_KNOWN_EFFECT);
    }

    @Test
    public void updateNonExistingExcipient() throws Exception {
        int databaseSizeBeforeUpdate = excipientRepository.findAll().size();

        // Create the Excipient

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restExcipientMockMvc.perform(put("/api/excipients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(excipient)))
            .andExpect(status().isCreated());

        // Validate the Excipient in the database
        List<Excipient> excipientList = excipientRepository.findAll();
        assertThat(excipientList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteExcipient() throws Exception {
        // Initialize the database
        excipientService.save(excipient);

        int databaseSizeBeforeDelete = excipientRepository.findAll().size();

        // Get the excipient
        restExcipientMockMvc.perform(delete("/api/excipients/{id}", excipient.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Excipient> excipientList = excipientRepository.findAll();
        assertThat(excipientList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Excipient.class);
        Excipient excipient1 = new Excipient();
        excipient1.setId("id1");
        Excipient excipient2 = new Excipient();
        excipient2.setId(excipient1.getId());
        assertThat(excipient1).isEqualTo(excipient2);
        excipient2.setId("id2");
        assertThat(excipient1).isNotEqualTo(excipient2);
        excipient1.setId(null);
        assertThat(excipient1).isNotEqualTo(excipient2);
    }
}
