package be.fgov.famhp.mpm.gateway.web.rest;

import be.fgov.famhp.mpm.gateway.MpmNgApp;

import be.fgov.famhp.mpm.gateway.domain.RouteOfAdministration;
import be.fgov.famhp.mpm.gateway.repository.RouteOfAdministrationRepository;
import be.fgov.famhp.mpm.gateway.service.RouteOfAdministrationService;
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
 * Test class for the RouteOfAdministrationResource REST controller.
 *
 * @see RouteOfAdministrationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MpmNgApp.class)
public class RouteOfAdministrationResourceIntTest {

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
    private RouteOfAdministrationRepository routeOfAdministrationRepository;

    @Autowired
    private RouteOfAdministrationService routeOfAdministrationService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restRouteOfAdministrationMockMvc;

    private RouteOfAdministration routeOfAdministration;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RouteOfAdministrationResource routeOfAdministrationResource = new RouteOfAdministrationResource(routeOfAdministrationService);
        this.restRouteOfAdministrationMockMvc = MockMvcBuilders.standaloneSetup(routeOfAdministrationResource)
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
    public static RouteOfAdministration createEntity() {
        RouteOfAdministration routeOfAdministration = new RouteOfAdministration()
            .code(DEFAULT_CODE)
            .en(DEFAULT_EN)
            .fr(DEFAULT_FR)
            .de(DEFAULT_DE)
            .nl(DEFAULT_NL);
        return routeOfAdministration;
    }

    @Before
    public void initTest() {
        routeOfAdministrationRepository.deleteAll();
        routeOfAdministration = createEntity();
    }

    @Test
    public void createRouteOfAdministration() throws Exception {
        int databaseSizeBeforeCreate = routeOfAdministrationRepository.findAll().size();

        // Create the RouteOfAdministration
        restRouteOfAdministrationMockMvc.perform(post("/api/route-of-administrations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(routeOfAdministration)))
            .andExpect(status().isCreated());

        // Validate the RouteOfAdministration in the database
        List<RouteOfAdministration> routeOfAdministrationList = routeOfAdministrationRepository.findAll();
        assertThat(routeOfAdministrationList).hasSize(databaseSizeBeforeCreate + 1);
        RouteOfAdministration testRouteOfAdministration = routeOfAdministrationList.get(routeOfAdministrationList.size() - 1);
        assertThat(testRouteOfAdministration.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testRouteOfAdministration.getEn()).isEqualTo(DEFAULT_EN);
        assertThat(testRouteOfAdministration.getFr()).isEqualTo(DEFAULT_FR);
        assertThat(testRouteOfAdministration.getDe()).isEqualTo(DEFAULT_DE);
        assertThat(testRouteOfAdministration.getNl()).isEqualTo(DEFAULT_NL);
    }

    @Test
    public void createRouteOfAdministrationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = routeOfAdministrationRepository.findAll().size();

        // Create the RouteOfAdministration with an existing ID
        routeOfAdministration.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restRouteOfAdministrationMockMvc.perform(post("/api/route-of-administrations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(routeOfAdministration)))
            .andExpect(status().isBadRequest());

        // Validate the RouteOfAdministration in the database
        List<RouteOfAdministration> routeOfAdministrationList = routeOfAdministrationRepository.findAll();
        assertThat(routeOfAdministrationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = routeOfAdministrationRepository.findAll().size();
        // set the field null
        routeOfAdministration.setCode(null);

        // Create the RouteOfAdministration, which fails.

        restRouteOfAdministrationMockMvc.perform(post("/api/route-of-administrations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(routeOfAdministration)))
            .andExpect(status().isBadRequest());

        List<RouteOfAdministration> routeOfAdministrationList = routeOfAdministrationRepository.findAll();
        assertThat(routeOfAdministrationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllRouteOfAdministrations() throws Exception {
        // Initialize the database
        routeOfAdministrationRepository.save(routeOfAdministration);

        // Get all the routeOfAdministrationList
        restRouteOfAdministrationMockMvc.perform(get("/api/route-of-administrations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(routeOfAdministration.getId())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].en").value(hasItem(DEFAULT_EN.toString())))
            .andExpect(jsonPath("$.[*].fr").value(hasItem(DEFAULT_FR.toString())))
            .andExpect(jsonPath("$.[*].de").value(hasItem(DEFAULT_DE.toString())))
            .andExpect(jsonPath("$.[*].nl").value(hasItem(DEFAULT_NL.toString())));
    }

    @Test
    public void getRouteOfAdministration() throws Exception {
        // Initialize the database
        routeOfAdministrationRepository.save(routeOfAdministration);

        // Get the routeOfAdministration
        restRouteOfAdministrationMockMvc.perform(get("/api/route-of-administrations/{id}", routeOfAdministration.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(routeOfAdministration.getId()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.en").value(DEFAULT_EN.toString()))
            .andExpect(jsonPath("$.fr").value(DEFAULT_FR.toString()))
            .andExpect(jsonPath("$.de").value(DEFAULT_DE.toString()))
            .andExpect(jsonPath("$.nl").value(DEFAULT_NL.toString()));
    }

    @Test
    public void getNonExistingRouteOfAdministration() throws Exception {
        // Get the routeOfAdministration
        restRouteOfAdministrationMockMvc.perform(get("/api/route-of-administrations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateRouteOfAdministration() throws Exception {
        // Initialize the database
        routeOfAdministrationService.save(routeOfAdministration);

        int databaseSizeBeforeUpdate = routeOfAdministrationRepository.findAll().size();

        // Update the routeOfAdministration
        RouteOfAdministration updatedRouteOfAdministration = routeOfAdministrationRepository.findOne(routeOfAdministration.getId());
        updatedRouteOfAdministration
            .code(UPDATED_CODE)
            .en(UPDATED_EN)
            .fr(UPDATED_FR)
            .de(UPDATED_DE)
            .nl(UPDATED_NL);

        restRouteOfAdministrationMockMvc.perform(put("/api/route-of-administrations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedRouteOfAdministration)))
            .andExpect(status().isOk());

        // Validate the RouteOfAdministration in the database
        List<RouteOfAdministration> routeOfAdministrationList = routeOfAdministrationRepository.findAll();
        assertThat(routeOfAdministrationList).hasSize(databaseSizeBeforeUpdate);
        RouteOfAdministration testRouteOfAdministration = routeOfAdministrationList.get(routeOfAdministrationList.size() - 1);
        assertThat(testRouteOfAdministration.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testRouteOfAdministration.getEn()).isEqualTo(UPDATED_EN);
        assertThat(testRouteOfAdministration.getFr()).isEqualTo(UPDATED_FR);
        assertThat(testRouteOfAdministration.getDe()).isEqualTo(UPDATED_DE);
        assertThat(testRouteOfAdministration.getNl()).isEqualTo(UPDATED_NL);
    }

    @Test
    public void updateNonExistingRouteOfAdministration() throws Exception {
        int databaseSizeBeforeUpdate = routeOfAdministrationRepository.findAll().size();

        // Create the RouteOfAdministration

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restRouteOfAdministrationMockMvc.perform(put("/api/route-of-administrations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(routeOfAdministration)))
            .andExpect(status().isCreated());

        // Validate the RouteOfAdministration in the database
        List<RouteOfAdministration> routeOfAdministrationList = routeOfAdministrationRepository.findAll();
        assertThat(routeOfAdministrationList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteRouteOfAdministration() throws Exception {
        // Initialize the database
        routeOfAdministrationService.save(routeOfAdministration);

        int databaseSizeBeforeDelete = routeOfAdministrationRepository.findAll().size();

        // Get the routeOfAdministration
        restRouteOfAdministrationMockMvc.perform(delete("/api/route-of-administrations/{id}", routeOfAdministration.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<RouteOfAdministration> routeOfAdministrationList = routeOfAdministrationRepository.findAll();
        assertThat(routeOfAdministrationList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RouteOfAdministration.class);
        RouteOfAdministration routeOfAdministration1 = new RouteOfAdministration();
        routeOfAdministration1.setId("id1");
        RouteOfAdministration routeOfAdministration2 = new RouteOfAdministration();
        routeOfAdministration2.setId(routeOfAdministration1.getId());
        assertThat(routeOfAdministration1).isEqualTo(routeOfAdministration2);
        routeOfAdministration2.setId("id2");
        assertThat(routeOfAdministration1).isNotEqualTo(routeOfAdministration2);
        routeOfAdministration1.setId(null);
        assertThat(routeOfAdministration1).isNotEqualTo(routeOfAdministration2);
    }
}
