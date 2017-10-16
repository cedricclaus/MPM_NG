package be.fgov.famhp.mpm.gateway.web.rest;

import be.fgov.famhp.mpm.gateway.MpmNgApp;

import be.fgov.famhp.mpm.gateway.domain.Unit;
import be.fgov.famhp.mpm.gateway.repository.UnitRepository;
import be.fgov.famhp.mpm.gateway.service.UnitService;
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
 * Test class for the UnitResource REST controller.
 *
 * @see UnitResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MpmNgApp.class)
public class UnitResourceIntTest {

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

    private static final String DEFAULT_UCUM = "AAAAAAAAAA";
    private static final String UPDATED_UCUM = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_DEPRECATED = false;
    private static final Boolean UPDATED_DEPRECATED = true;

    @Autowired
    private UnitRepository unitRepository;

    @Autowired
    private UnitService unitService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restUnitMockMvc;

    private Unit unit;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final UnitResource unitResource = new UnitResource(unitService);
        this.restUnitMockMvc = MockMvcBuilders.standaloneSetup(unitResource)
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
    public static Unit createEntity() {
        Unit unit = new Unit()
            .code(DEFAULT_CODE)
            .en(DEFAULT_EN)
            .fr(DEFAULT_FR)
            .de(DEFAULT_DE)
            .nl(DEFAULT_NL)
            .ucum(DEFAULT_UCUM)
            .description(DEFAULT_DESCRIPTION)
            .deprecated(DEFAULT_DEPRECATED);
        return unit;
    }

    @Before
    public void initTest() {
        unitRepository.deleteAll();
        unit = createEntity();
    }

    @Test
    public void createUnit() throws Exception {
        int databaseSizeBeforeCreate = unitRepository.findAll().size();

        // Create the Unit
        restUnitMockMvc.perform(post("/api/units")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(unit)))
            .andExpect(status().isCreated());

        // Validate the Unit in the database
        List<Unit> unitList = unitRepository.findAll();
        assertThat(unitList).hasSize(databaseSizeBeforeCreate + 1);
        Unit testUnit = unitList.get(unitList.size() - 1);
        assertThat(testUnit.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testUnit.getEn()).isEqualTo(DEFAULT_EN);
        assertThat(testUnit.getFr()).isEqualTo(DEFAULT_FR);
        assertThat(testUnit.getDe()).isEqualTo(DEFAULT_DE);
        assertThat(testUnit.getNl()).isEqualTo(DEFAULT_NL);
        assertThat(testUnit.getUcum()).isEqualTo(DEFAULT_UCUM);
        assertThat(testUnit.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testUnit.isDeprecated()).isEqualTo(DEFAULT_DEPRECATED);
    }

    @Test
    public void createUnitWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = unitRepository.findAll().size();

        // Create the Unit with an existing ID
        unit.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restUnitMockMvc.perform(post("/api/units")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(unit)))
            .andExpect(status().isBadRequest());

        // Validate the Unit in the database
        List<Unit> unitList = unitRepository.findAll();
        assertThat(unitList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = unitRepository.findAll().size();
        // set the field null
        unit.setCode(null);

        // Create the Unit, which fails.

        restUnitMockMvc.perform(post("/api/units")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(unit)))
            .andExpect(status().isBadRequest());

        List<Unit> unitList = unitRepository.findAll();
        assertThat(unitList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllUnits() throws Exception {
        // Initialize the database
        unitRepository.save(unit);

        // Get all the unitList
        restUnitMockMvc.perform(get("/api/units?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(unit.getId())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].en").value(hasItem(DEFAULT_EN.toString())))
            .andExpect(jsonPath("$.[*].fr").value(hasItem(DEFAULT_FR.toString())))
            .andExpect(jsonPath("$.[*].de").value(hasItem(DEFAULT_DE.toString())))
            .andExpect(jsonPath("$.[*].nl").value(hasItem(DEFAULT_NL.toString())))
            .andExpect(jsonPath("$.[*].ucum").value(hasItem(DEFAULT_UCUM.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].deprecated").value(hasItem(DEFAULT_DEPRECATED.booleanValue())));
    }

    @Test
    public void getUnit() throws Exception {
        // Initialize the database
        unitRepository.save(unit);

        // Get the unit
        restUnitMockMvc.perform(get("/api/units/{id}", unit.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(unit.getId()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.en").value(DEFAULT_EN.toString()))
            .andExpect(jsonPath("$.fr").value(DEFAULT_FR.toString()))
            .andExpect(jsonPath("$.de").value(DEFAULT_DE.toString()))
            .andExpect(jsonPath("$.nl").value(DEFAULT_NL.toString()))
            .andExpect(jsonPath("$.ucum").value(DEFAULT_UCUM.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.deprecated").value(DEFAULT_DEPRECATED.booleanValue()));
    }

    @Test
    public void getNonExistingUnit() throws Exception {
        // Get the unit
        restUnitMockMvc.perform(get("/api/units/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateUnit() throws Exception {
        // Initialize the database
        unitService.save(unit);

        int databaseSizeBeforeUpdate = unitRepository.findAll().size();

        // Update the unit
        Unit updatedUnit = unitRepository.findOne(unit.getId());
        updatedUnit
            .code(UPDATED_CODE)
            .en(UPDATED_EN)
            .fr(UPDATED_FR)
            .de(UPDATED_DE)
            .nl(UPDATED_NL)
            .ucum(UPDATED_UCUM)
            .description(UPDATED_DESCRIPTION)
            .deprecated(UPDATED_DEPRECATED);

        restUnitMockMvc.perform(put("/api/units")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedUnit)))
            .andExpect(status().isOk());

        // Validate the Unit in the database
        List<Unit> unitList = unitRepository.findAll();
        assertThat(unitList).hasSize(databaseSizeBeforeUpdate);
        Unit testUnit = unitList.get(unitList.size() - 1);
        assertThat(testUnit.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testUnit.getEn()).isEqualTo(UPDATED_EN);
        assertThat(testUnit.getFr()).isEqualTo(UPDATED_FR);
        assertThat(testUnit.getDe()).isEqualTo(UPDATED_DE);
        assertThat(testUnit.getNl()).isEqualTo(UPDATED_NL);
        assertThat(testUnit.getUcum()).isEqualTo(UPDATED_UCUM);
        assertThat(testUnit.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testUnit.isDeprecated()).isEqualTo(UPDATED_DEPRECATED);
    }

    @Test
    public void updateNonExistingUnit() throws Exception {
        int databaseSizeBeforeUpdate = unitRepository.findAll().size();

        // Create the Unit

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restUnitMockMvc.perform(put("/api/units")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(unit)))
            .andExpect(status().isCreated());

        // Validate the Unit in the database
        List<Unit> unitList = unitRepository.findAll();
        assertThat(unitList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteUnit() throws Exception {
        // Initialize the database
        unitService.save(unit);

        int databaseSizeBeforeDelete = unitRepository.findAll().size();

        // Get the unit
        restUnitMockMvc.perform(delete("/api/units/{id}", unit.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Unit> unitList = unitRepository.findAll();
        assertThat(unitList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Unit.class);
        Unit unit1 = new Unit();
        unit1.setId("id1");
        Unit unit2 = new Unit();
        unit2.setId(unit1.getId());
        assertThat(unit1).isEqualTo(unit2);
        unit2.setId("id2");
        assertThat(unit1).isNotEqualTo(unit2);
        unit1.setId(null);
        assertThat(unit1).isNotEqualTo(unit2);
    }
}
