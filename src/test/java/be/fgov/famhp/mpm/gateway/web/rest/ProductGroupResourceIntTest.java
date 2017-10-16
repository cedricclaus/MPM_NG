package be.fgov.famhp.mpm.gateway.web.rest;

import be.fgov.famhp.mpm.gateway.MpmNgApp;

import be.fgov.famhp.mpm.gateway.domain.ProductGroup;
import be.fgov.famhp.mpm.gateway.repository.ProductGroupRepository;
import be.fgov.famhp.mpm.gateway.service.ProductGroupService;
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

import be.fgov.famhp.mpm.gateway.domain.enumeration.AlloHomeoType;
/**
 * Test class for the ProductGroupResource REST controller.
 *
 * @see ProductGroupResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MpmNgApp.class)
public class ProductGroupResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_NUMBER = "BBBBBBBBBB";

    private static final Boolean DEFAULT_DEPRECATED = false;
    private static final Boolean UPDATED_DEPRECATED = true;

    private static final String DEFAULT_USAGE_CODE = "AAAAAAAAAA";
    private static final String UPDATED_USAGE_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_USAGE_ID = "AAAAAAAAAA";
    private static final String UPDATED_USAGE_ID = "BBBBBBBBBB";

    private static final String DEFAULT_PROCEDURE_TYPE_CODE = "AAAAAAAAAA";
    private static final String UPDATED_PROCEDURE_TYPE_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_PROCEDURE_TYPE_ID = "AAAAAAAAAA";
    private static final String UPDATED_PROCEDURE_TYPE_ID = "BBBBBBBBBB";

    private static final String DEFAULT_AUTORIZATION_TYPE_CODE = "AAAAAAAAAA";
    private static final String UPDATED_AUTORIZATION_TYPE_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_AUTORIZATION_TYPE_ID = "AAAAAAAAAA";
    private static final String UPDATED_AUTORIZATION_TYPE_ID = "BBBBBBBBBB";

    private static final String DEFAULT_PRODUCT_TYPE_CODE = "AAAAAAAAAA";
    private static final String UPDATED_PRODUCT_TYPE_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_PRODUCT_TYPE_ID = "AAAAAAAAAA";
    private static final String UPDATED_PRODUCT_TYPE_ID = "BBBBBBBBBB";

    private static final AlloHomeoType DEFAULT_ALLO_HOMEO = AlloHomeoType.ALLOPATHIC;
    private static final AlloHomeoType UPDATED_ALLO_HOMEO = AlloHomeoType.HOMEOPATHIC;

    @Autowired
    private ProductGroupRepository productGroupRepository;

    @Autowired
    private ProductGroupService productGroupService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restProductGroupMockMvc;

    private ProductGroup productGroup;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProductGroupResource productGroupResource = new ProductGroupResource(productGroupService);
        this.restProductGroupMockMvc = MockMvcBuilders.standaloneSetup(productGroupResource)
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
    public static ProductGroup createEntity() {
        ProductGroup productGroup = new ProductGroup()
            .name(DEFAULT_NAME)
            .number(DEFAULT_NUMBER)
            .deprecated(DEFAULT_DEPRECATED)
            .usageCode(DEFAULT_USAGE_CODE)
            .usageId(DEFAULT_USAGE_ID)
            .procedureTypeCode(DEFAULT_PROCEDURE_TYPE_CODE)
            .procedureTypeId(DEFAULT_PROCEDURE_TYPE_ID)
            .autorizationTypeCode(DEFAULT_AUTORIZATION_TYPE_CODE)
            .autorizationTypeId(DEFAULT_AUTORIZATION_TYPE_ID)
            .productTypeCode(DEFAULT_PRODUCT_TYPE_CODE)
            .productTypeId(DEFAULT_PRODUCT_TYPE_ID)
            .alloHomeo(DEFAULT_ALLO_HOMEO);
        return productGroup;
    }

    @Before
    public void initTest() {
        productGroupRepository.deleteAll();
        productGroup = createEntity();
    }

    @Test
    public void createProductGroup() throws Exception {
        int databaseSizeBeforeCreate = productGroupRepository.findAll().size();

        // Create the ProductGroup
        restProductGroupMockMvc.perform(post("/api/product-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productGroup)))
            .andExpect(status().isCreated());

        // Validate the ProductGroup in the database
        List<ProductGroup> productGroupList = productGroupRepository.findAll();
        assertThat(productGroupList).hasSize(databaseSizeBeforeCreate + 1);
        ProductGroup testProductGroup = productGroupList.get(productGroupList.size() - 1);
        assertThat(testProductGroup.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testProductGroup.getNumber()).isEqualTo(DEFAULT_NUMBER);
        assertThat(testProductGroup.isDeprecated()).isEqualTo(DEFAULT_DEPRECATED);
        assertThat(testProductGroup.getUsageCode()).isEqualTo(DEFAULT_USAGE_CODE);
        assertThat(testProductGroup.getUsageId()).isEqualTo(DEFAULT_USAGE_ID);
        assertThat(testProductGroup.getProcedureTypeCode()).isEqualTo(DEFAULT_PROCEDURE_TYPE_CODE);
        assertThat(testProductGroup.getProcedureTypeId()).isEqualTo(DEFAULT_PROCEDURE_TYPE_ID);
        assertThat(testProductGroup.getAutorizationTypeCode()).isEqualTo(DEFAULT_AUTORIZATION_TYPE_CODE);
        assertThat(testProductGroup.getAutorizationTypeId()).isEqualTo(DEFAULT_AUTORIZATION_TYPE_ID);
        assertThat(testProductGroup.getProductTypeCode()).isEqualTo(DEFAULT_PRODUCT_TYPE_CODE);
        assertThat(testProductGroup.getProductTypeId()).isEqualTo(DEFAULT_PRODUCT_TYPE_ID);
        assertThat(testProductGroup.getAlloHomeo()).isEqualTo(DEFAULT_ALLO_HOMEO);
    }

    @Test
    public void createProductGroupWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = productGroupRepository.findAll().size();

        // Create the ProductGroup with an existing ID
        productGroup.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductGroupMockMvc.perform(post("/api/product-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productGroup)))
            .andExpect(status().isBadRequest());

        // Validate the ProductGroup in the database
        List<ProductGroup> productGroupList = productGroupRepository.findAll();
        assertThat(productGroupList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = productGroupRepository.findAll().size();
        // set the field null
        productGroup.setName(null);

        // Create the ProductGroup, which fails.

        restProductGroupMockMvc.perform(post("/api/product-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productGroup)))
            .andExpect(status().isBadRequest());

        List<ProductGroup> productGroupList = productGroupRepository.findAll();
        assertThat(productGroupList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = productGroupRepository.findAll().size();
        // set the field null
        productGroup.setNumber(null);

        // Create the ProductGroup, which fails.

        restProductGroupMockMvc.perform(post("/api/product-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productGroup)))
            .andExpect(status().isBadRequest());

        List<ProductGroup> productGroupList = productGroupRepository.findAll();
        assertThat(productGroupList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkUsageCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = productGroupRepository.findAll().size();
        // set the field null
        productGroup.setUsageCode(null);

        // Create the ProductGroup, which fails.

        restProductGroupMockMvc.perform(post("/api/product-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productGroup)))
            .andExpect(status().isBadRequest());

        List<ProductGroup> productGroupList = productGroupRepository.findAll();
        assertThat(productGroupList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkUsageIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = productGroupRepository.findAll().size();
        // set the field null
        productGroup.setUsageId(null);

        // Create the ProductGroup, which fails.

        restProductGroupMockMvc.perform(post("/api/product-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productGroup)))
            .andExpect(status().isBadRequest());

        List<ProductGroup> productGroupList = productGroupRepository.findAll();
        assertThat(productGroupList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkProcedureTypeCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = productGroupRepository.findAll().size();
        // set the field null
        productGroup.setProcedureTypeCode(null);

        // Create the ProductGroup, which fails.

        restProductGroupMockMvc.perform(post("/api/product-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productGroup)))
            .andExpect(status().isBadRequest());

        List<ProductGroup> productGroupList = productGroupRepository.findAll();
        assertThat(productGroupList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkProcedureTypeIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = productGroupRepository.findAll().size();
        // set the field null
        productGroup.setProcedureTypeId(null);

        // Create the ProductGroup, which fails.

        restProductGroupMockMvc.perform(post("/api/product-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productGroup)))
            .andExpect(status().isBadRequest());

        List<ProductGroup> productGroupList = productGroupRepository.findAll();
        assertThat(productGroupList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkAutorizationTypeCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = productGroupRepository.findAll().size();
        // set the field null
        productGroup.setAutorizationTypeCode(null);

        // Create the ProductGroup, which fails.

        restProductGroupMockMvc.perform(post("/api/product-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productGroup)))
            .andExpect(status().isBadRequest());

        List<ProductGroup> productGroupList = productGroupRepository.findAll();
        assertThat(productGroupList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkAutorizationTypeIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = productGroupRepository.findAll().size();
        // set the field null
        productGroup.setAutorizationTypeId(null);

        // Create the ProductGroup, which fails.

        restProductGroupMockMvc.perform(post("/api/product-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productGroup)))
            .andExpect(status().isBadRequest());

        List<ProductGroup> productGroupList = productGroupRepository.findAll();
        assertThat(productGroupList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkProductTypeCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = productGroupRepository.findAll().size();
        // set the field null
        productGroup.setProductTypeCode(null);

        // Create the ProductGroup, which fails.

        restProductGroupMockMvc.perform(post("/api/product-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productGroup)))
            .andExpect(status().isBadRequest());

        List<ProductGroup> productGroupList = productGroupRepository.findAll();
        assertThat(productGroupList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkProductTypeIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = productGroupRepository.findAll().size();
        // set the field null
        productGroup.setProductTypeId(null);

        // Create the ProductGroup, which fails.

        restProductGroupMockMvc.perform(post("/api/product-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productGroup)))
            .andExpect(status().isBadRequest());

        List<ProductGroup> productGroupList = productGroupRepository.findAll();
        assertThat(productGroupList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllProductGroups() throws Exception {
        // Initialize the database
        productGroupRepository.save(productGroup);

        // Get all the productGroupList
        restProductGroupMockMvc.perform(get("/api/product-groups?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productGroup.getId())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].number").value(hasItem(DEFAULT_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].deprecated").value(hasItem(DEFAULT_DEPRECATED.booleanValue())))
            .andExpect(jsonPath("$.[*].usageCode").value(hasItem(DEFAULT_USAGE_CODE.toString())))
            .andExpect(jsonPath("$.[*].usageId").value(hasItem(DEFAULT_USAGE_ID.toString())))
            .andExpect(jsonPath("$.[*].procedureTypeCode").value(hasItem(DEFAULT_PROCEDURE_TYPE_CODE.toString())))
            .andExpect(jsonPath("$.[*].procedureTypeId").value(hasItem(DEFAULT_PROCEDURE_TYPE_ID.toString())))
            .andExpect(jsonPath("$.[*].autorizationTypeCode").value(hasItem(DEFAULT_AUTORIZATION_TYPE_CODE.toString())))
            .andExpect(jsonPath("$.[*].autorizationTypeId").value(hasItem(DEFAULT_AUTORIZATION_TYPE_ID.toString())))
            .andExpect(jsonPath("$.[*].productTypeCode").value(hasItem(DEFAULT_PRODUCT_TYPE_CODE.toString())))
            .andExpect(jsonPath("$.[*].productTypeId").value(hasItem(DEFAULT_PRODUCT_TYPE_ID.toString())))
            .andExpect(jsonPath("$.[*].alloHomeo").value(hasItem(DEFAULT_ALLO_HOMEO.toString())));
    }

    @Test
    public void getProductGroup() throws Exception {
        // Initialize the database
        productGroupRepository.save(productGroup);

        // Get the productGroup
        restProductGroupMockMvc.perform(get("/api/product-groups/{id}", productGroup.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(productGroup.getId()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.number").value(DEFAULT_NUMBER.toString()))
            .andExpect(jsonPath("$.deprecated").value(DEFAULT_DEPRECATED.booleanValue()))
            .andExpect(jsonPath("$.usageCode").value(DEFAULT_USAGE_CODE.toString()))
            .andExpect(jsonPath("$.usageId").value(DEFAULT_USAGE_ID.toString()))
            .andExpect(jsonPath("$.procedureTypeCode").value(DEFAULT_PROCEDURE_TYPE_CODE.toString()))
            .andExpect(jsonPath("$.procedureTypeId").value(DEFAULT_PROCEDURE_TYPE_ID.toString()))
            .andExpect(jsonPath("$.autorizationTypeCode").value(DEFAULT_AUTORIZATION_TYPE_CODE.toString()))
            .andExpect(jsonPath("$.autorizationTypeId").value(DEFAULT_AUTORIZATION_TYPE_ID.toString()))
            .andExpect(jsonPath("$.productTypeCode").value(DEFAULT_PRODUCT_TYPE_CODE.toString()))
            .andExpect(jsonPath("$.productTypeId").value(DEFAULT_PRODUCT_TYPE_ID.toString()))
            .andExpect(jsonPath("$.alloHomeo").value(DEFAULT_ALLO_HOMEO.toString()));
    }

    @Test
    public void getNonExistingProductGroup() throws Exception {
        // Get the productGroup
        restProductGroupMockMvc.perform(get("/api/product-groups/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateProductGroup() throws Exception {
        // Initialize the database
        productGroupService.save(productGroup);

        int databaseSizeBeforeUpdate = productGroupRepository.findAll().size();

        // Update the productGroup
        ProductGroup updatedProductGroup = productGroupRepository.findOne(productGroup.getId());
        updatedProductGroup
            .name(UPDATED_NAME)
            .number(UPDATED_NUMBER)
            .deprecated(UPDATED_DEPRECATED)
            .usageCode(UPDATED_USAGE_CODE)
            .usageId(UPDATED_USAGE_ID)
            .procedureTypeCode(UPDATED_PROCEDURE_TYPE_CODE)
            .procedureTypeId(UPDATED_PROCEDURE_TYPE_ID)
            .autorizationTypeCode(UPDATED_AUTORIZATION_TYPE_CODE)
            .autorizationTypeId(UPDATED_AUTORIZATION_TYPE_ID)
            .productTypeCode(UPDATED_PRODUCT_TYPE_CODE)
            .productTypeId(UPDATED_PRODUCT_TYPE_ID)
            .alloHomeo(UPDATED_ALLO_HOMEO);

        restProductGroupMockMvc.perform(put("/api/product-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedProductGroup)))
            .andExpect(status().isOk());

        // Validate the ProductGroup in the database
        List<ProductGroup> productGroupList = productGroupRepository.findAll();
        assertThat(productGroupList).hasSize(databaseSizeBeforeUpdate);
        ProductGroup testProductGroup = productGroupList.get(productGroupList.size() - 1);
        assertThat(testProductGroup.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProductGroup.getNumber()).isEqualTo(UPDATED_NUMBER);
        assertThat(testProductGroup.isDeprecated()).isEqualTo(UPDATED_DEPRECATED);
        assertThat(testProductGroup.getUsageCode()).isEqualTo(UPDATED_USAGE_CODE);
        assertThat(testProductGroup.getUsageId()).isEqualTo(UPDATED_USAGE_ID);
        assertThat(testProductGroup.getProcedureTypeCode()).isEqualTo(UPDATED_PROCEDURE_TYPE_CODE);
        assertThat(testProductGroup.getProcedureTypeId()).isEqualTo(UPDATED_PROCEDURE_TYPE_ID);
        assertThat(testProductGroup.getAutorizationTypeCode()).isEqualTo(UPDATED_AUTORIZATION_TYPE_CODE);
        assertThat(testProductGroup.getAutorizationTypeId()).isEqualTo(UPDATED_AUTORIZATION_TYPE_ID);
        assertThat(testProductGroup.getProductTypeCode()).isEqualTo(UPDATED_PRODUCT_TYPE_CODE);
        assertThat(testProductGroup.getProductTypeId()).isEqualTo(UPDATED_PRODUCT_TYPE_ID);
        assertThat(testProductGroup.getAlloHomeo()).isEqualTo(UPDATED_ALLO_HOMEO);
    }

    @Test
    public void updateNonExistingProductGroup() throws Exception {
        int databaseSizeBeforeUpdate = productGroupRepository.findAll().size();

        // Create the ProductGroup

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restProductGroupMockMvc.perform(put("/api/product-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productGroup)))
            .andExpect(status().isCreated());

        // Validate the ProductGroup in the database
        List<ProductGroup> productGroupList = productGroupRepository.findAll();
        assertThat(productGroupList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteProductGroup() throws Exception {
        // Initialize the database
        productGroupService.save(productGroup);

        int databaseSizeBeforeDelete = productGroupRepository.findAll().size();

        // Get the productGroup
        restProductGroupMockMvc.perform(delete("/api/product-groups/{id}", productGroup.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ProductGroup> productGroupList = productGroupRepository.findAll();
        assertThat(productGroupList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductGroup.class);
        ProductGroup productGroup1 = new ProductGroup();
        productGroup1.setId("id1");
        ProductGroup productGroup2 = new ProductGroup();
        productGroup2.setId(productGroup1.getId());
        assertThat(productGroup1).isEqualTo(productGroup2);
        productGroup2.setId("id2");
        assertThat(productGroup1).isNotEqualTo(productGroup2);
        productGroup1.setId(null);
        assertThat(productGroup1).isNotEqualTo(productGroup2);
    }
}
