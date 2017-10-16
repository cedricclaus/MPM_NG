package be.fgov.famhp.mpm.gateway.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import be.fgov.famhp.mpm.gateway.domain.enumeration.IngredientType;

/**
 * A Ingredient.
 */
@Document(collection = "ingredient")
public class Ingredient implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    private String id;

    @Field("en")
    private String en;

    @Field("fr")
    private String fr;

    @Field("de")
    private String de;

    @Field("nl")
    private String nl;

    @Field("category")
    private String category;

    @Field("notes")
    private String notes;

    @Field("snomed_ct_id")
    private String snomedCTId;

    @Field("synonyms")
    private String synonyms;

    @Field("cas_code")
    private String casCode;

    @Field("deprecated")
    private Boolean deprecated;

    @Field("innovation")
    private Boolean innovation;

    @Field("creation_date_usage_hum")
    private LocalDate creationDateUsageHum;

    @Field("creation_date_usage_vet")
    private LocalDate creationDateUsageVet;

    @Field("residual_product")
    private Boolean residualProduct;

    @NotNull
    @Field("type")
    private IngredientType type;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEn() {
        return en;
    }

    public Ingredient en(String en) {
        this.en = en;
        return this;
    }

    public void setEn(String en) {
        this.en = en;
    }

    public String getFr() {
        return fr;
    }

    public Ingredient fr(String fr) {
        this.fr = fr;
        return this;
    }

    public void setFr(String fr) {
        this.fr = fr;
    }

    public String getDe() {
        return de;
    }

    public Ingredient de(String de) {
        this.de = de;
        return this;
    }

    public void setDe(String de) {
        this.de = de;
    }

    public String getNl() {
        return nl;
    }

    public Ingredient nl(String nl) {
        this.nl = nl;
        return this;
    }

    public void setNl(String nl) {
        this.nl = nl;
    }

    public String getCategory() {
        return category;
    }

    public Ingredient category(String category) {
        this.category = category;
        return this;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getNotes() {
        return notes;
    }

    public Ingredient notes(String notes) {
        this.notes = notes;
        return this;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getSnomedCTId() {
        return snomedCTId;
    }

    public Ingredient snomedCTId(String snomedCTId) {
        this.snomedCTId = snomedCTId;
        return this;
    }

    public void setSnomedCTId(String snomedCTId) {
        this.snomedCTId = snomedCTId;
    }

    public String getSynonyms() {
        return synonyms;
    }

    public Ingredient synonyms(String synonyms) {
        this.synonyms = synonyms;
        return this;
    }

    public void setSynonyms(String synonyms) {
        this.synonyms = synonyms;
    }

    public String getCasCode() {
        return casCode;
    }

    public Ingredient casCode(String casCode) {
        this.casCode = casCode;
        return this;
    }

    public void setCasCode(String casCode) {
        this.casCode = casCode;
    }

    public Boolean isDeprecated() {
        return deprecated;
    }

    public Ingredient deprecated(Boolean deprecated) {
        this.deprecated = deprecated;
        return this;
    }

    public void setDeprecated(Boolean deprecated) {
        this.deprecated = deprecated;
    }

    public Boolean isInnovation() {
        return innovation;
    }

    public Ingredient innovation(Boolean innovation) {
        this.innovation = innovation;
        return this;
    }

    public void setInnovation(Boolean innovation) {
        this.innovation = innovation;
    }

    public LocalDate getCreationDateUsageHum() {
        return creationDateUsageHum;
    }

    public Ingredient creationDateUsageHum(LocalDate creationDateUsageHum) {
        this.creationDateUsageHum = creationDateUsageHum;
        return this;
    }

    public void setCreationDateUsageHum(LocalDate creationDateUsageHum) {
        this.creationDateUsageHum = creationDateUsageHum;
    }

    public LocalDate getCreationDateUsageVet() {
        return creationDateUsageVet;
    }

    public Ingredient creationDateUsageVet(LocalDate creationDateUsageVet) {
        this.creationDateUsageVet = creationDateUsageVet;
        return this;
    }

    public void setCreationDateUsageVet(LocalDate creationDateUsageVet) {
        this.creationDateUsageVet = creationDateUsageVet;
    }

    public Boolean isResidualProduct() {
        return residualProduct;
    }

    public Ingredient residualProduct(Boolean residualProduct) {
        this.residualProduct = residualProduct;
        return this;
    }

    public void setResidualProduct(Boolean residualProduct) {
        this.residualProduct = residualProduct;
    }

    public IngredientType getType() {
        return type;
    }

    public Ingredient type(IngredientType type) {
        this.type = type;
        return this;
    }

    public void setType(IngredientType type) {
        this.type = type;
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
        Ingredient ingredient = (Ingredient) o;
        if (ingredient.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), ingredient.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Ingredient{" +
            "id=" + getId() +
            ", en='" + getEn() + "'" +
            ", fr='" + getFr() + "'" +
            ", de='" + getDe() + "'" +
            ", nl='" + getNl() + "'" +
            ", category='" + getCategory() + "'" +
            ", notes='" + getNotes() + "'" +
            ", snomedCTId='" + getSnomedCTId() + "'" +
            ", synonyms='" + getSynonyms() + "'" +
            ", casCode='" + getCasCode() + "'" +
            ", deprecated='" + isDeprecated() + "'" +
            ", innovation='" + isInnovation() + "'" +
            ", creationDateUsageHum='" + getCreationDateUsageHum() + "'" +
            ", creationDateUsageVet='" + getCreationDateUsageVet() + "'" +
            ", residualProduct='" + isResidualProduct() + "'" +
            ", type='" + getType() + "'" +
            "}";
    }
}
