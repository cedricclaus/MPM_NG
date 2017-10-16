package be.fgov.famhp.mpm.gateway.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

import be.fgov.famhp.mpm.gateway.domain.enumeration.AlloHomeoType;

/**
 * A ProductGroup.
 */
@Document(collection = "product_group")
public class ProductGroup implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    private String id;

    @NotNull
    @Field("name")
    private String name;

    @NotNull
    @Field("number")
    private String number;

    @Field("deprecated")
    private Boolean deprecated;

    @NotNull
    @Field("usage_code")
    private String usageCode;

    @NotNull
    @Field("usage_id")
    private String usageId;

    @NotNull
    @Field("procedure_type_code")
    private String procedureTypeCode;

    @NotNull
    @Field("procedure_type_id")
    private String procedureTypeId;

    @NotNull
    @Field("autorization_type_code")
    private String autorizationTypeCode;

    @NotNull
    @Field("autorization_type_id")
    private String autorizationTypeId;

    @NotNull
    @Field("product_type_code")
    private String productTypeCode;

    @NotNull
    @Field("product_type_id")
    private String productTypeId;

    @Field("allo_homeo")
    private AlloHomeoType alloHomeo;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public ProductGroup name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public ProductGroup number(String number) {
        this.number = number;
        return this;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Boolean isDeprecated() {
        return deprecated;
    }

    public ProductGroup deprecated(Boolean deprecated) {
        this.deprecated = deprecated;
        return this;
    }

    public void setDeprecated(Boolean deprecated) {
        this.deprecated = deprecated;
    }

    public String getUsageCode() {
        return usageCode;
    }

    public ProductGroup usageCode(String usageCode) {
        this.usageCode = usageCode;
        return this;
    }

    public void setUsageCode(String usageCode) {
        this.usageCode = usageCode;
    }

    public String getUsageId() {
        return usageId;
    }

    public ProductGroup usageId(String usageId) {
        this.usageId = usageId;
        return this;
    }

    public void setUsageId(String usageId) {
        this.usageId = usageId;
    }

    public String getProcedureTypeCode() {
        return procedureTypeCode;
    }

    public ProductGroup procedureTypeCode(String procedureTypeCode) {
        this.procedureTypeCode = procedureTypeCode;
        return this;
    }

    public void setProcedureTypeCode(String procedureTypeCode) {
        this.procedureTypeCode = procedureTypeCode;
    }

    public String getProcedureTypeId() {
        return procedureTypeId;
    }

    public ProductGroup procedureTypeId(String procedureTypeId) {
        this.procedureTypeId = procedureTypeId;
        return this;
    }

    public void setProcedureTypeId(String procedureTypeId) {
        this.procedureTypeId = procedureTypeId;
    }

    public String getAutorizationTypeCode() {
        return autorizationTypeCode;
    }

    public ProductGroup autorizationTypeCode(String autorizationTypeCode) {
        this.autorizationTypeCode = autorizationTypeCode;
        return this;
    }

    public void setAutorizationTypeCode(String autorizationTypeCode) {
        this.autorizationTypeCode = autorizationTypeCode;
    }

    public String getAutorizationTypeId() {
        return autorizationTypeId;
    }

    public ProductGroup autorizationTypeId(String autorizationTypeId) {
        this.autorizationTypeId = autorizationTypeId;
        return this;
    }

    public void setAutorizationTypeId(String autorizationTypeId) {
        this.autorizationTypeId = autorizationTypeId;
    }

    public String getProductTypeCode() {
        return productTypeCode;
    }

    public ProductGroup productTypeCode(String productTypeCode) {
        this.productTypeCode = productTypeCode;
        return this;
    }

    public void setProductTypeCode(String productTypeCode) {
        this.productTypeCode = productTypeCode;
    }

    public String getProductTypeId() {
        return productTypeId;
    }

    public ProductGroup productTypeId(String productTypeId) {
        this.productTypeId = productTypeId;
        return this;
    }

    public void setProductTypeId(String productTypeId) {
        this.productTypeId = productTypeId;
    }

    public AlloHomeoType getAlloHomeo() {
        return alloHomeo;
    }

    public ProductGroup alloHomeo(AlloHomeoType alloHomeo) {
        this.alloHomeo = alloHomeo;
        return this;
    }

    public void setAlloHomeo(AlloHomeoType alloHomeo) {
        this.alloHomeo = alloHomeo;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ProductGroup productGroup = (ProductGroup) o;
        if (productGroup.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), productGroup.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProductGroup{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", number='" + getNumber() + "'" +
            ", deprecated='" + isDeprecated() + "'" +
            ", usageCode='" + getUsageCode() + "'" +
            ", usageId='" + getUsageId() + "'" +
            ", procedureTypeCode='" + getProcedureTypeCode() + "'" +
            ", procedureTypeId='" + getProcedureTypeId() + "'" +
            ", autorizationTypeCode='" + getAutorizationTypeCode() + "'" +
            ", autorizationTypeId='" + getAutorizationTypeId() + "'" +
            ", productTypeCode='" + getProductTypeCode() + "'" +
            ", productTypeId='" + getProductTypeId() + "'" +
            ", alloHomeo='" + getAlloHomeo() + "'" +
            "}";
    }
}
