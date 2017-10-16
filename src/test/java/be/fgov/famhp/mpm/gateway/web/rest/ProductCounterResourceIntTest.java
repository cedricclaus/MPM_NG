package be.fgov.famhp.mpm.gateway.web.rest;

import be.fgov.famhp.mpm.gateway.MpmNgApp;

import be.fgov.famhp.mpm.gateway.domain.ProductCounter;
import be.fgov.famhp.mpm.gateway.repository.ProductCounterRepository;
import be.fgov.famhp.mpm.gateway.service.ProductCounterService;
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
 * Test class for the ProductCounterResource REST controller.
 *
 * @see ProductCounterResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MpmNgApp.class)
public class ProductCounterResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_STRENGTH = "AAAAAAAAAA";
    private static final String UPDATED_STRENGTH = "BBBBBBBBBB";

    private static final String DEFAULT_PHARMACEUTICAL_FORM = "AAAAAAAAAA";
    private static final String UPDATED_PHARMACEUTICAL_FORM = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS_ID = "AAAAAAAAAA";
    private static final String UPDATED_STATUS_ID = "BBBBBBBBBB";

    private static final String DEFAULT_PC_ID = "AAAAAAAAAA";
    private static final String UPDATED_PC_ID = "BBBBBBBBBB";

    @Autowired
    private ProductCounterRepository productCounterRepository;

    @Autowired
    private ProductCounterService productCounterService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restProductCounterMockMvc;

    private ProductCounter productCounter;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProductCounterResource productCounterResource = new ProductCounterResource(productCounterService);
        this.restProductCounterMockMvc = MockMvcBuilders.standaloneSetup(productCounterResource)
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
    public static ProductCounter createEntity() {
        ProductCounter productCounter = new ProductCounter()
            .name(DEFAULT_NAME)
            .number(DEFAULT_NUMBER)
            .strength(DEFAULT_STRENGTH)
            .pharmaceuticalForm(DEFAULT_PHARMACEUTICAL_FORM)
            .status(DEFAULT_STATUS)
            .statusId(DEFAULT_STATUS_ID)
            .pcId(DEFAULT_PC_ID);
        return productCounter;
    }

    @Before
    public void initTest() {
        productCounterRepository.deleteAll();
        productCounter = createEntity();
    }

    @Test
    public void createProductCounter() throws Exception {
        int databaseSizeBeforeCreate = productCounterRepository.findAll().size();

        // Create the ProductCounter
        restProductCounterMockMvc.perform(post("/api/product-counters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productCounter)))
            .andExpect(status().isCreated());

        // Validate the ProductCounter in the database
        List<ProductCounter> productCounterList = productCounterRepository.findAll();
        assertThat(productCounterList).hasSize(databaseSizeBeforeCreate + 1);
        ProductCounter testProductCounter = productCounterList.get(productCounterList.size() - 1);
        assertThat(testProductCounter.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testProductCounter.getNumber()).isEqualTo(DEFAULT_NUMBER);
        assertThat(testProductCounter.getStrength()).isEqualTo(DEFAULT_STRENGTH);
        assertThat(testProductCounter.getPharmaceuticalForm()).isEqualTo(DEFAULT_PHARMACEUTICAL_FORM);
        assertThat(testProductCounter.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testProductCounter.getStatusId()).isEqualTo(DEFAULT_STATUS_ID);
        assertThat(testProductCounter.getPcId()).isEqualTo(DEFAULT_PC_ID);
    }

    @Test
    public void createProductCounterWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = productCounterRepository.findAll().size();

        // Create the ProductCounter with an existing ID
        productCounter.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductCounterMockMvc.perform(post("/api/product-counters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productCounter)))
            .andExpect(status().isBadRequest());

        // Validate the ProductCounter in the database
        List<ProductCounter> productCounterList = productCounterRepository.findAll();
        assertThat(productCounterList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = productCounterRepository.findAll().size();
        // set the field null
        productCounter.setName(null);

        // Create the ProductCounter, which fails.

        restProductCounterMockMvc.perform(post("/api/product-counters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productCounter)))
            .andExpect(status().isBadRequest());

        List<ProductCounter> productCounterList = productCounterRepository.findAll();
        assertThat(productCounterList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = productCounterRepository.findAll().size();
        // set the field null
        productCounter.setNumber(null);

        // Create the ProductCounter, which fails.

        restProductCounterMockMvc.perform(post("/api/product-counters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productCounter)))
            .andExpect(status().isBadRequest());

        List<ProductCounter> productCounterList = productCounterRepository.findAll();
        assertThat(productCounterList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = productCounterRepository.findAll().size();
        // set the field null
        productCounter.setStatus(null);

        // Create the ProductCounter, which fails.

        restProductCounterMockMvc.perform(post("/api/product-counters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productCounter)))
            .andExpect(status().isBadRequest());

        List<ProductCounter> productCounterList = productCounterRepository.findAll();
        assertThat(productCounterList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkStatusIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = productCounterRepository.findAll().size();
        // set the field null
        productCounter.setStatusId(null);

        // Create the ProductCounter, which fails.

        restProductCounterMockMvc.perform(post("/api/product-counters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productCounter)))
            .andExpect(status().isBadRequest());

        List<ProductCounter> productCounterList = productCounterRepository.findAll();
        assertThat(productCounterList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllProductCounters() throws Exception {
        // Initialize the database
        productCounterRepository.save(productCounter);

        // Get all the productCounterList
        restProductCounterMockMvc.perform(get("/api/product-counters?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productCounter.getId())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].number").value(hasItem(DEFAULT_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].strength").value(hasItem(DEFAULT_STRENGTH.toString())))
            .andExpect(jsonPath("$.[*].pharmaceuticalForm").value(hasItem(DEFAULT_PHARMACEUTICAL_FORM.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].statusId").value(hasItem(DEFAULT_STATUS_ID.toString())))
            .andExpect(jsonPath("$.[*].pcId").value(hasItem(DEFAULT_PC_ID.toString())));
    }

    @Test
    public void getProductCounter() throws Exception {
        // Initialize the database
        productCounterRepository.save(productCounter);

        // Get the productCounter
        restProductCounterMockMvc.perform(get("/api/product-counters/{id}", productCounter.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(productCounter.getId()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.number").value(DEFAULT_NUMBER.toString()))
            .andExpect(jsonPath("$.strength").value(DEFAULT_STRENGTH.toString()))
            .andExpect(jsonPath("$.pharmaceuticalForm").value(DEFAULT_PHARMACEUTICAL_FORM.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.statusId").value(DEFAULT_STATUS_ID.toString()))
            .andExpect(jsonPath("$.pcId").value(DEFAULT_PC_ID.toString()));
    }

    @Test
    public void getNonExistingProductCounter() throws Exception {
        // Get the productCounter
        restProductCounterMockMvc.perform(get("/api/product-counters/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateProductCounter() throws Exception {
        // Initialize the database
        productCounterService.save(productCounter);

        int databaseSizeBeforeUpdate = productCounterRepository.findAll().size();

        // Update the productCounter
        ProductCounter updatedProductCounter = productCounterRepository.findOne(productCounter.getId());
        updatedProductCounter
            .name(UPDATED_NAME)
            .number(UPDATED_NUMBER)
            .strength(UPDATED_STRENGTH)
            .pharmaceuticalForm(UPDATED_PHARMACEUTICAL_FORM)
            .status(UPDATED_STATUS)
            .statusId(UPDATED_STATUS_ID)
            .pcId(UPDATED_PC_ID);

        restProductCounterMockMvc.perform(put("/api/product-counters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedProductCounter)))
            .andExpect(status().isOk());

        // Validate the ProductCounter in the database
        List<ProductCounter> productCounterList = productCounterRepository.findAll();
        assertThat(productCounterList).hasSize(databaseSizeBeforeUpdate);
        ProductCounter testProductCounter = productCounterList.get(productCounterList.size() - 1);
        assertThat(testProductCounter.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProductCounter.getNumber()).isEqualTo(UPDATED_NUMBER);
        assertThat(testProductCounter.getStrength()).isEqualTo(UPDATED_STRENGTH);
        assertThat(testProductCounter.getPharmaceuticalForm()).isEqualTo(UPDATED_PHARMACEUTICAL_FORM);
        assertThat(testProductCounter.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testProductCounter.getStatusId()).isEqualTo(UPDATED_STATUS_ID);
        assertThat(testProductCounter.getPcId()).isEqualTo(UPDATED_PC_ID);
    }

    @Test
    public void updateNonExistingProductCounter() throws Exception {
        int databaseSizeBeforeUpdate = productCounterRepository.findAll().size();

        // Create the ProductCounter

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restProductCounterMockMvc.perform(put("/api/product-counters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productCounter)))
            .andExpect(status().isCreated());

        // Validate the ProductCounter in the database
        List<ProductCounter> productCounterList = productCounterRepository.findAll();
        assertThat(productCounterList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteProductCounter() throws Exception {
        // Initialize the database
        productCounterService.save(productCounter);

        int databaseSizeBeforeDelete = productCounterRepository.findAll().size();

        // Get the productCounter
        restProductCounterMockMvc.perform(delete("/api/product-counters/{id}", productCounter.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ProductCounter> productCounterList = productCounterRepository.findAll();
        assertThat(productCounterList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductCounter.class);
        ProductCounter productCounter1 = new ProductCounter();
        productCounter1.setId("id1");
        ProductCounter productCounter2 = new ProductCounter();
        productCounter2.setId(productCounter1.getId());
        assertThat(productCounter1).isEqualTo(productCounter2);
        productCounter2.setId("id2");
        assertThat(productCounter1).isNotEqualTo(productCounter2);
        productCounter1.setId(null);
        assertThat(productCounter1).isNotEqualTo(productCounter2);
    }
}
