package be.fgov.famhp.mpm.gateway.web.rest;

import be.fgov.famhp.mpm.gateway.MpmNgApp;

import be.fgov.famhp.mpm.gateway.domain.Authorisation;
import be.fgov.famhp.mpm.gateway.repository.AuthorisationRepository;
import be.fgov.famhp.mpm.gateway.service.AuthorisationService;
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

/**
 * Test class for the AuthorisationResource REST controller.
 *
 * @see AuthorisationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MpmNgApp.class)
public class AuthorisationResourceIntTest {

    private static final String DEFAULT_CTI = "AAAAAAAAAA";
    private static final String UPDATED_CTI = "BBBBBBBBBB";

    private static final String DEFAULT_AUTHORISATION_NBR = "AAAAAAAAAA";
    private static final String UPDATED_AUTHORISATION_NBR = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_AUTHORISATION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_AUTHORISATION_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_RADIATION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_RADIATION_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_SUSPENSION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_SUSPENSION_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_RADIATION_NOTE = "AAAAAAAAAA";
    private static final String UPDATED_RADIATION_NOTE = "BBBBBBBBBB";

    private static final String DEFAULT_SUSPENSION_NOTE = "AAAAAAAAAA";
    private static final String UPDATED_SUSPENSION_NOTE = "BBBBBBBBBB";

    private static final String DEFAULT_STRENGTH = "AAAAAAAAAA";
    private static final String UPDATED_STRENGTH = "BBBBBBBBBB";

    private static final String DEFAULT_PC_NAME = "AAAAAAAAAA";
    private static final String UPDATED_PC_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PC_ID = "AAAAAAAAAA";
    private static final String UPDATED_PC_ID = "BBBBBBBBBB";

    private static final String DEFAULT_PC_NBR = "AAAAAAAAAA";
    private static final String UPDATED_PC_NBR = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS_ID = "AAAAAAAAAA";
    private static final String UPDATED_STATUS_ID = "BBBBBBBBBB";

    private static final String DEFAULT_PG_USAGE = "AAAAAAAAAA";
    private static final String UPDATED_PG_USAGE = "BBBBBBBBBB";

    private static final String DEFAULT_PG_PROCEDURE_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_PG_PROCEDURE_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_PG_AUTHORISATION_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_PG_AUTHORISATION_TYPE = "BBBBBBBBBB";

    @Autowired
    private AuthorisationRepository authorisationRepository;

    @Autowired
    private AuthorisationService authorisationService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restAuthorisationMockMvc;

    private Authorisation authorisation;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AuthorisationResource authorisationResource = new AuthorisationResource(authorisationService);
        this.restAuthorisationMockMvc = MockMvcBuilders.standaloneSetup(authorisationResource)
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
    public static Authorisation createEntity() {
        Authorisation authorisation = new Authorisation()
            .cti(DEFAULT_CTI)
            .authorisationNbr(DEFAULT_AUTHORISATION_NBR)
            .authorisationDate(DEFAULT_AUTHORISATION_DATE)
            .radiationDate(DEFAULT_RADIATION_DATE)
            .suspensionDate(DEFAULT_SUSPENSION_DATE)
            .radiationNote(DEFAULT_RADIATION_NOTE)
            .suspensionNote(DEFAULT_SUSPENSION_NOTE)
            .strength(DEFAULT_STRENGTH)
            .pcName(DEFAULT_PC_NAME)
            .pcId(DEFAULT_PC_ID)
            .pcNbr(DEFAULT_PC_NBR)
            .name(DEFAULT_NAME)
            .status(DEFAULT_STATUS)
            .statusId(DEFAULT_STATUS_ID)
            .pgUsage(DEFAULT_PG_USAGE)
            .pgProcedureType(DEFAULT_PG_PROCEDURE_TYPE)
            .pgAuthorisationType(DEFAULT_PG_AUTHORISATION_TYPE);
        return authorisation;
    }

    @Before
    public void initTest() {
        authorisationRepository.deleteAll();
        authorisation = createEntity();
    }

    @Test
    public void createAuthorisation() throws Exception {
        int databaseSizeBeforeCreate = authorisationRepository.findAll().size();

        // Create the Authorisation
        restAuthorisationMockMvc.perform(post("/api/authorisations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(authorisation)))
            .andExpect(status().isCreated());

        // Validate the Authorisation in the database
        List<Authorisation> authorisationList = authorisationRepository.findAll();
        assertThat(authorisationList).hasSize(databaseSizeBeforeCreate + 1);
        Authorisation testAuthorisation = authorisationList.get(authorisationList.size() - 1);
        assertThat(testAuthorisation.getCti()).isEqualTo(DEFAULT_CTI);
        assertThat(testAuthorisation.getAuthorisationNbr()).isEqualTo(DEFAULT_AUTHORISATION_NBR);
        assertThat(testAuthorisation.getAuthorisationDate()).isEqualTo(DEFAULT_AUTHORISATION_DATE);
        assertThat(testAuthorisation.getRadiationDate()).isEqualTo(DEFAULT_RADIATION_DATE);
        assertThat(testAuthorisation.getSuspensionDate()).isEqualTo(DEFAULT_SUSPENSION_DATE);
        assertThat(testAuthorisation.getRadiationNote()).isEqualTo(DEFAULT_RADIATION_NOTE);
        assertThat(testAuthorisation.getSuspensionNote()).isEqualTo(DEFAULT_SUSPENSION_NOTE);
        assertThat(testAuthorisation.getStrength()).isEqualTo(DEFAULT_STRENGTH);
        assertThat(testAuthorisation.getPcName()).isEqualTo(DEFAULT_PC_NAME);
        assertThat(testAuthorisation.getPcId()).isEqualTo(DEFAULT_PC_ID);
        assertThat(testAuthorisation.getPcNbr()).isEqualTo(DEFAULT_PC_NBR);
        assertThat(testAuthorisation.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testAuthorisation.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testAuthorisation.getStatusId()).isEqualTo(DEFAULT_STATUS_ID);
        assertThat(testAuthorisation.getPgUsage()).isEqualTo(DEFAULT_PG_USAGE);
        assertThat(testAuthorisation.getPgProcedureType()).isEqualTo(DEFAULT_PG_PROCEDURE_TYPE);
        assertThat(testAuthorisation.getPgAuthorisationType()).isEqualTo(DEFAULT_PG_AUTHORISATION_TYPE);
    }

    @Test
    public void createAuthorisationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = authorisationRepository.findAll().size();

        // Create the Authorisation with an existing ID
        authorisation.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restAuthorisationMockMvc.perform(post("/api/authorisations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(authorisation)))
            .andExpect(status().isBadRequest());

        // Validate the Authorisation in the database
        List<Authorisation> authorisationList = authorisationRepository.findAll();
        assertThat(authorisationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void getAllAuthorisations() throws Exception {
        // Initialize the database
        authorisationRepository.save(authorisation);

        // Get all the authorisationList
        restAuthorisationMockMvc.perform(get("/api/authorisations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(authorisation.getId())))
            .andExpect(jsonPath("$.[*].cti").value(hasItem(DEFAULT_CTI.toString())))
            .andExpect(jsonPath("$.[*].authorisationNbr").value(hasItem(DEFAULT_AUTHORISATION_NBR.toString())))
            .andExpect(jsonPath("$.[*].authorisationDate").value(hasItem(DEFAULT_AUTHORISATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].radiationDate").value(hasItem(DEFAULT_RADIATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].suspensionDate").value(hasItem(DEFAULT_SUSPENSION_DATE.toString())))
            .andExpect(jsonPath("$.[*].radiationNote").value(hasItem(DEFAULT_RADIATION_NOTE.toString())))
            .andExpect(jsonPath("$.[*].suspensionNote").value(hasItem(DEFAULT_SUSPENSION_NOTE.toString())))
            .andExpect(jsonPath("$.[*].strength").value(hasItem(DEFAULT_STRENGTH.toString())))
            .andExpect(jsonPath("$.[*].pcName").value(hasItem(DEFAULT_PC_NAME.toString())))
            .andExpect(jsonPath("$.[*].pcId").value(hasItem(DEFAULT_PC_ID.toString())))
            .andExpect(jsonPath("$.[*].pcNbr").value(hasItem(DEFAULT_PC_NBR.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].statusId").value(hasItem(DEFAULT_STATUS_ID.toString())))
            .andExpect(jsonPath("$.[*].pgUsage").value(hasItem(DEFAULT_PG_USAGE.toString())))
            .andExpect(jsonPath("$.[*].pgProcedureType").value(hasItem(DEFAULT_PG_PROCEDURE_TYPE.toString())))
            .andExpect(jsonPath("$.[*].pgAuthorisationType").value(hasItem(DEFAULT_PG_AUTHORISATION_TYPE.toString())));
    }

    @Test
    public void getAuthorisation() throws Exception {
        // Initialize the database
        authorisationRepository.save(authorisation);

        // Get the authorisation
        restAuthorisationMockMvc.perform(get("/api/authorisations/{id}", authorisation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(authorisation.getId()))
            .andExpect(jsonPath("$.cti").value(DEFAULT_CTI.toString()))
            .andExpect(jsonPath("$.authorisationNbr").value(DEFAULT_AUTHORISATION_NBR.toString()))
            .andExpect(jsonPath("$.authorisationDate").value(DEFAULT_AUTHORISATION_DATE.toString()))
            .andExpect(jsonPath("$.radiationDate").value(DEFAULT_RADIATION_DATE.toString()))
            .andExpect(jsonPath("$.suspensionDate").value(DEFAULT_SUSPENSION_DATE.toString()))
            .andExpect(jsonPath("$.radiationNote").value(DEFAULT_RADIATION_NOTE.toString()))
            .andExpect(jsonPath("$.suspensionNote").value(DEFAULT_SUSPENSION_NOTE.toString()))
            .andExpect(jsonPath("$.strength").value(DEFAULT_STRENGTH.toString()))
            .andExpect(jsonPath("$.pcName").value(DEFAULT_PC_NAME.toString()))
            .andExpect(jsonPath("$.pcId").value(DEFAULT_PC_ID.toString()))
            .andExpect(jsonPath("$.pcNbr").value(DEFAULT_PC_NBR.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.statusId").value(DEFAULT_STATUS_ID.toString()))
            .andExpect(jsonPath("$.pgUsage").value(DEFAULT_PG_USAGE.toString()))
            .andExpect(jsonPath("$.pgProcedureType").value(DEFAULT_PG_PROCEDURE_TYPE.toString()))
            .andExpect(jsonPath("$.pgAuthorisationType").value(DEFAULT_PG_AUTHORISATION_TYPE.toString()));
    }

    @Test
    public void getNonExistingAuthorisation() throws Exception {
        // Get the authorisation
        restAuthorisationMockMvc.perform(get("/api/authorisations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateAuthorisation() throws Exception {
        // Initialize the database
        authorisationService.save(authorisation);

        int databaseSizeBeforeUpdate = authorisationRepository.findAll().size();

        // Update the authorisation
        Authorisation updatedAuthorisation = authorisationRepository.findOne(authorisation.getId());
        updatedAuthorisation
            .cti(UPDATED_CTI)
            .authorisationNbr(UPDATED_AUTHORISATION_NBR)
            .authorisationDate(UPDATED_AUTHORISATION_DATE)
            .radiationDate(UPDATED_RADIATION_DATE)
            .suspensionDate(UPDATED_SUSPENSION_DATE)
            .radiationNote(UPDATED_RADIATION_NOTE)
            .suspensionNote(UPDATED_SUSPENSION_NOTE)
            .strength(UPDATED_STRENGTH)
            .pcName(UPDATED_PC_NAME)
            .pcId(UPDATED_PC_ID)
            .pcNbr(UPDATED_PC_NBR)
            .name(UPDATED_NAME)
            .status(UPDATED_STATUS)
            .statusId(UPDATED_STATUS_ID)
            .pgUsage(UPDATED_PG_USAGE)
            .pgProcedureType(UPDATED_PG_PROCEDURE_TYPE)
            .pgAuthorisationType(UPDATED_PG_AUTHORISATION_TYPE);

        restAuthorisationMockMvc.perform(put("/api/authorisations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAuthorisation)))
            .andExpect(status().isOk());

        // Validate the Authorisation in the database
        List<Authorisation> authorisationList = authorisationRepository.findAll();
        assertThat(authorisationList).hasSize(databaseSizeBeforeUpdate);
        Authorisation testAuthorisation = authorisationList.get(authorisationList.size() - 1);
        assertThat(testAuthorisation.getCti()).isEqualTo(UPDATED_CTI);
        assertThat(testAuthorisation.getAuthorisationNbr()).isEqualTo(UPDATED_AUTHORISATION_NBR);
        assertThat(testAuthorisation.getAuthorisationDate()).isEqualTo(UPDATED_AUTHORISATION_DATE);
        assertThat(testAuthorisation.getRadiationDate()).isEqualTo(UPDATED_RADIATION_DATE);
        assertThat(testAuthorisation.getSuspensionDate()).isEqualTo(UPDATED_SUSPENSION_DATE);
        assertThat(testAuthorisation.getRadiationNote()).isEqualTo(UPDATED_RADIATION_NOTE);
        assertThat(testAuthorisation.getSuspensionNote()).isEqualTo(UPDATED_SUSPENSION_NOTE);
        assertThat(testAuthorisation.getStrength()).isEqualTo(UPDATED_STRENGTH);
        assertThat(testAuthorisation.getPcName()).isEqualTo(UPDATED_PC_NAME);
        assertThat(testAuthorisation.getPcId()).isEqualTo(UPDATED_PC_ID);
        assertThat(testAuthorisation.getPcNbr()).isEqualTo(UPDATED_PC_NBR);
        assertThat(testAuthorisation.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAuthorisation.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testAuthorisation.getStatusId()).isEqualTo(UPDATED_STATUS_ID);
        assertThat(testAuthorisation.getPgUsage()).isEqualTo(UPDATED_PG_USAGE);
        assertThat(testAuthorisation.getPgProcedureType()).isEqualTo(UPDATED_PG_PROCEDURE_TYPE);
        assertThat(testAuthorisation.getPgAuthorisationType()).isEqualTo(UPDATED_PG_AUTHORISATION_TYPE);
    }

    @Test
    public void updateNonExistingAuthorisation() throws Exception {
        int databaseSizeBeforeUpdate = authorisationRepository.findAll().size();

        // Create the Authorisation

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAuthorisationMockMvc.perform(put("/api/authorisations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(authorisation)))
            .andExpect(status().isCreated());

        // Validate the Authorisation in the database
        List<Authorisation> authorisationList = authorisationRepository.findAll();
        assertThat(authorisationList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteAuthorisation() throws Exception {
        // Initialize the database
        authorisationService.save(authorisation);

        int databaseSizeBeforeDelete = authorisationRepository.findAll().size();

        // Get the authorisation
        restAuthorisationMockMvc.perform(delete("/api/authorisations/{id}", authorisation.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Authorisation> authorisationList = authorisationRepository.findAll();
        assertThat(authorisationList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Authorisation.class);
        Authorisation authorisation1 = new Authorisation();
        authorisation1.setId("id1");
        Authorisation authorisation2 = new Authorisation();
        authorisation2.setId(authorisation1.getId());
        assertThat(authorisation1).isEqualTo(authorisation2);
        authorisation2.setId("id2");
        assertThat(authorisation1).isNotEqualTo(authorisation2);
        authorisation1.setId(null);
        assertThat(authorisation1).isNotEqualTo(authorisation2);
    }
}
