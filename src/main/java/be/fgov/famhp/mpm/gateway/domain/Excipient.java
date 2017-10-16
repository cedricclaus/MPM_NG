package be.fgov.famhp.mpm.gateway.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Excipient.
 */
@Document(collection = "active_substance")
public class Excipient implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    private String id;

    @Field("name")
    private String name;

    @Field("notes")
    private String notes;

    @Field("additional_info")
    private String additionalInfo;

    @Field("amount")
    private Double amount;

    @Field("non_numerical_amount")
    private String nonNumericalAmount;

    @Field("unit_id")
    private String unitId;

    @Field("unit")
    private String unit;

    @Field("pc_id")
    private String pcId;

    @Field("pcc_id")
    private String pccId;

    @Field("pcc_en")
    private String pccEn;

    @NotNull
    @Field("ingredient_id")
    private String ingredientId;

    @Field("belongs_to")
    private String belongsTo;

    @Field("known_effect")
    private Boolean knownEffect;

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

    public Excipient name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNotes() {
        return notes;
    }

    public Excipient notes(String notes) {
        this.notes = notes;
        return this;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public Excipient additionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
        return this;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    public Double getAmount() {
        return amount;
    }

    public Excipient amount(Double amount) {
        this.amount = amount;
        return this;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getNonNumericalAmount() {
        return nonNumericalAmount;
    }

    public Excipient nonNumericalAmount(String nonNumericalAmount) {
        this.nonNumericalAmount = nonNumericalAmount;
        return this;
    }

    public void setNonNumericalAmount(String nonNumericalAmount) {
        this.nonNumericalAmount = nonNumericalAmount;
    }

    public String getUnitId() {
        return unitId;
    }

    public Excipient unitId(String unitId) {
        this.unitId = unitId;
        return this;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public String getUnit() {
        return unit;
    }

    public Excipient unit(String unit) {
        this.unit = unit;
        return this;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getPcId() {
        return pcId;
    }

    public Excipient pcId(String pcId) {
        this.pcId = pcId;
        return this;
    }

    public void setPcId(String pcId) {
        this.pcId = pcId;
    }

    public String getPccId() {
        return pccId;
    }

    public Excipient pccId(String pccId) {
        this.pccId = pccId;
        return this;
    }

    public void setPccId(String pccId) {
        this.pccId = pccId;
    }

    public String getPccEn() {
        return pccEn;
    }

    public Excipient pccEn(String pccEn) {
        this.pccEn = pccEn;
        return this;
    }

    public void setPccEn(String pccEn) {
        this.pccEn = pccEn;
    }

    public String getIngredientId() {
        return ingredientId;
    }

    public Excipient ingredientId(String ingredientId) {
        this.ingredientId = ingredientId;
        return this;
    }

    public void setIngredientId(String ingredientId) {
        this.ingredientId = ingredientId;
    }

    public String getBelongsTo() {
        return belongsTo;
    }

    public Excipient belongsTo(String belongsTo) {
        this.belongsTo = belongsTo;
        return this;
    }

    public void setBelongsTo(String belongsTo) {
        this.belongsTo = belongsTo;
    }

    public Boolean isKnownEffect() {
        return knownEffect;
    }

    public Excipient knownEffect(Boolean knownEffect) {
        this.knownEffect = knownEffect;
        return this;
    }

    public void setKnownEffect(Boolean knownEffect) {
        this.knownEffect = knownEffect;
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
        Excipient excipient = (Excipient) o;
        if (excipient.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), excipient.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Excipient{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", notes='" + getNotes() + "'" +
            ", additionalInfo='" + getAdditionalInfo() + "'" +
            ", amount='" + getAmount() + "'" +
            ", nonNumericalAmount='" + getNonNumericalAmount() + "'" +
            ", unitId='" + getUnitId() + "'" +
            ", unit='" + getUnit() + "'" +
            ", pcId='" + getPcId() + "'" +
            ", pccId='" + getPccId() + "'" +
            ", pccEn='" + getPccEn() + "'" +
            ", ingredientId='" + getIngredientId() + "'" +
            ", belongsTo='" + getBelongsTo() + "'" +
            ", knownEffect='" + isKnownEffect() + "'" +
            "}";
    }
}
