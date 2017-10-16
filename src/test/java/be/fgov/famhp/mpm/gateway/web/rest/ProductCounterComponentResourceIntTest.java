package be.fgov.famhp.mpm.gateway.web.rest;

import be.fgov.famhp.mpm.gateway.MpmNgApp;

import be.fgov.famhp.mpm.gateway.domain.ProductCounterComponent;
import be.fgov.famhp.mpm.gateway.repository.ProductCounterComponentRepository;
import be.fgov.famhp.mpm.gateway.service.ProductCounterComponentService;
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
 * Test class for the ProductCounterComponentResource REST controller.
 *
 * @see ProductCounterComponentResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MpmNgApp.class)
public class ProductCounterComponentResourceIntTest {

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
    private ProductCounterComponentRepository productCounterComponentRepository;

    @Autowired
    private ProductCounterComponentService productCounterComponentService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restProductCounterComponentMockMvc;

    private ProductCounterComponent productCounterComponent;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProductCounterComponentResource productCounterComponentResource = new ProductCounterComponentResource(productCounterComponentService);
        this.restProductCounterComponentMockMvc = MockMvcBuilders.standaloneSetup(productCounterComponentResource)
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
    public static ProductCounterComponent createEntity() {
        ProductCounterComponent productCounterComponent = new ProductCounterComponent()
            .code(DEFAULT_CODE)
            .en(DEFAULT_EN)
            .fr(DEFAULT_FR)
            .de(DEFAULT_DE)
            .nl(DEFAULT_NL);
        return productCounterComponent;
    }

    @Before
    public void initTest() {
        productCounterComponentRepository.deleteAll();
        productCounterComponent = createEntity();
    }

    @Test
    public void createProductCounterComponent() throws Exception {
        int databaseSizeBeforeCreate = productCounterComponentRepository.findAll().size();

        // Create the ProductCounterComponent
        restProductCounterComponentMockMvc.perform(post("/api/product-counter-components")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productCounterComponent)))
            .andExpect(status().isCreated());

        // Validate the ProductCounterComponent in the database
        List<ProductCounterComponent> productCounterComponentList = productCounterComponentRepository.findAll();
        assertThat(productCounterComponentList).hasSize(databaseSizeBeforeCreate + 1);
        ProductCounterComponent testProductCounterComponent = productCounterComponentList.get(productCounterComponentList.size() - 1);
        assertThat(testProductCounterComponent.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testProductCounterComponent.getEn()).isEqualTo(DEFAULT_EN);
        assertThat(testProductCounterComponent.getFr()).isEqualTo(DEFAULT_FR);
        assertThat(testProductCounterComponent.getDe()).isEqualTo(DEFAULT_DE);
        assertThat(testProductCounterComponent.getNl()).isEqualTo(DEFAULT_NL);
    }

    @Test
    public void createProductCounterComponentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = productCounterComponentRepository.findAll().size();

        // Create the ProductCounterComponent with an existing ID
        productCounterComponent.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductCounterComponentMockMvc.perform(post("/api/product-counter-components")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productCounterComponent)))
            .andExpect(status().isBadRequest());

        // Validate the ProductCounterComponent in the database
        List<ProductCounterComponent> productCounterComponentList = productCounterComponentRepository.findAll();
        assertThat(productCounterComponentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = productCounterComponentRepository.findAll().size();
        // set the field null
        productCounterComponent.setCode(null);

        // Create the ProductCounterComponent, which fails.

        restProductCounterComponentMockMvc.perform(post("/api/product-counter-components")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productCounterComponent)))
            .andExpect(status().isBadRequest());

        List<ProductCounterComponent> productCounterComponentList = productCounterComponentRepository.findAll();
        assertThat(productCounterComponentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllProductCounterComponents() throws Exception {
        // Initialize the database
        productCounterComponentRepository.save(productCounterComponent);

        // Get all the productCounterComponentList
        restProductCounterComponentMockMvc.perform(get("/api/product-counter-components?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productCounterComponent.getId())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].en").value(hasItem(DEFAULT_EN.toString())))
            .andExpect(jsonPath("$.[*].fr").value(hasItem(DEFAULT_FR.toString())))
            .andExpect(jsonPath("$.[*].de").value(hasItem(DEFAULT_DE.toString())))
            .andExpect(jsonPath("$.[*].nl").value(hasItem(DEFAULT_NL.toString())));
    }

    @Test
    public void getProductCounterComponent() throws Exception {
        // Initialize the database
        productCounterComponentRepository.save(productCounterComponent);

        // Get the productCounterComponent
        restProductCounterComponentMockMvc.perform(get("/api/product-counter-components/{id}", productCounterComponent.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(productCounterComponent.getId()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.en").value(DEFAULT_EN.toString()))
            .andExpect(jsonPath("$.fr").value(DEFAULT_FR.toString()))
            .andExpect(jsonPath("$.de").value(DEFAULT_DE.toString()))
            .andExpect(jsonPath("$.nl").value(DEFAULT_NL.toString()));
    }

    @Test
    public void getNonExistingProductCounterComponent() throws Exception {
        // Get the productCounterComponent
        restProductCounterComponentMockMvc.perform(get("/api/product-counter-components/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateProductCounterComponent() throws Exception {
        // Initialize the database
        productCounterComponentService.save(productCounterComponent);

        int databaseSizeBeforeUpdate = productCounterComponentRepository.findAll().size();

        // Update the productCounterComponent
        ProductCounterComponent updatedProductCounterComponent = productCounterComponentRepository.findOne(productCounterComponent.getId());
        updatedProductCounterComponent
            .code(UPDATED_CODE)
            .en(UPDATED_EN)
            .fr(UPDATED_FR)
            .de(UPDATED_DE)
            .nl(UPDATED_NL);

        restProductCounterComponentMockMvc.perform(put("/api/product-counter-components")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedProductCounterComponent)))
            .andExpect(status().isOk());

        // Validate the ProductCounterComponent in the database
        List<ProductCounterComponent> productCounterComponentList = productCounterComponentRepository.findAll();
        assertThat(productCounterComponentList).hasSize(databaseSizeBeforeUpdate);
        ProductCounterComponent testProductCounterComponent = productCounterComponentList.get(productCounterComponentList.size() - 1);
        assertThat(testProductCounterComponent.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testProductCounterComponent.getEn()).isEqualTo(UPDATED_EN);
        assertThat(testProductCounterComponent.getFr()).isEqualTo(UPDATED_FR);
        assertThat(testProductCounterComponent.getDe()).isEqualTo(UPDATED_DE);
        assertThat(testProductCounterComponent.getNl()).isEqualTo(UPDATED_NL);
    }

    @Test
    public void updateNonExistingProductCounterComponent() throws Exception {
        int databaseSizeBeforeUpdate = productCounterComponentRepository.findAll().size();

        // Create the ProductCounterComponent

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restProductCounterComponentMockMvc.perform(put("/api/product-counter-components")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productCounterComponent)))
            .andExpect(status().isCreated());

        // Validate the ProductCounterComponent in the database
        List<ProductCounterComponent> productCounterComponentList = productCounterComponentRepository.findAll();
        assertThat(productCounterComponentList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteProductCounterComponent() throws Exception {
        // Initialize the database
        productCounterComponentService.save(productCounterComponent);

        int databaseSizeBeforeDelete = productCounterComponentRepository.findAll().size();

        // Get the productCounterComponent
        restProductCounterComponentMockMvc.perform(delete("/api/product-counter-components/{id}", productCounterComponent.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ProductCounterComponent> productCounterComponentList = productCounterComponentRepository.findAll();
        assertThat(productCounterComponentList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductCounterComponent.class);
        ProductCounterComponent productCounterComponent1 = new ProductCounterComponent();
        productCounterComponent1.setId("id1");
        ProductCounterComponent productCounterComponent2 = new ProductCounterComponent();
        productCounterComponent2.setId(productCounterComponent1.getId());
        assertThat(productCounterComponent1).isEqualTo(productCounterComponent2);
        productCounterComponent2.setId("id2");
        assertThat(productCounterComponent1).isNotEqualTo(productCounterComponent2);
        productCounterComponent1.setId(null);
        assertThat(productCounterComponent1).isNotEqualTo(productCounterComponent2);
    }
}
