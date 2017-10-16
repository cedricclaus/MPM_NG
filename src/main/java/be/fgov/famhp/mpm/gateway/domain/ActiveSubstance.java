package be.fgov.famhp.mpm.gateway.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A ActiveSubstance.
 */
@Document(collection = "active_substance")
public class ActiveSubstance implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    private String id;

    @Field("name")
    private String name;

    @Field("ingredient_id")
    private String ingredientId;

    @Field("notes")
    private String notes;

    @Field("as_equivalent")
    private String asEquivalent;

    @Field("additional_info")
    private String additionalInfo;

    @Field("amount")
    private Double amount;

    @Field("amount_eq")
    private Double amountEq;

    @Field("non_numerical_amount")
    private String nonNumericalAmount;

    @Field("non_numerical_amount_eq")
    private String nonNumericalAmountEq;

    @Field("unit_id")
    private String unitId;

    @Field("unit")
    private String unit;

    @Field("unit_eq")
    private String unitEq;

    @Field("unit_eq_id")
    private String unitEqId;

    @Field("pc_id")
    private String pcId;

    @Field("pcc_id")
    private String pccId;

    @Field("pcc_en")
    private String pccEn;

    @NotNull
    @Field("ingredient_eq_id")
    private String ingredientEqId;

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

    public ActiveSubstance name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIngredientId() {
        return ingredientId;
    }

    public ActiveSubstance ingredientId(String ingredientId) {
        this.ingredientId = ingredientId;
        return this;
    }

    public void setIngredientId(String ingredientId) {
        this.ingredientId = ingredientId;
    }

    public String getNotes() {
        return notes;
    }

    public ActiveSubstance notes(String notes) {
        this.notes = notes;
        return this;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getAsEquivalent() {
        return asEquivalent;
    }

    public ActiveSubstance asEquivalent(String asEquivalent) {
        this.asEquivalent = asEquivalent;
        return this;
    }

    public void setAsEquivalent(String asEquivalent) {
        this.asEquivalent = asEquivalent;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public ActiveSubstance additionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
        return this;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    public Double getAmount() {
        return amount;
    }

    public ActiveSubstance amount(Double amount) {
        this.amount = amount;
        return this;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getAmountEq() {
        return amountEq;
    }

    public ActiveSubstance amountEq(Double amountEq) {
        this.amountEq = amountEq;
        return this;
    }

    public void setAmountEq(Double amountEq) {
        this.amountEq = amountEq;
    }

    public String getNonNumericalAmount() {
        return nonNumericalAmount;
    }

    public ActiveSubstance nonNumericalAmount(String nonNumericalAmount) {
        this.nonNumericalAmount = nonNumericalAmount;
        return this;
    }

    public void setNonNumericalAmount(String nonNumericalAmount) {
        this.nonNumericalAmount = nonNumericalAmount;
    }

    public String getNonNumericalAmountEq() {
        return nonNumericalAmountEq;
    }

    public ActiveSubstance nonNumericalAmountEq(String nonNumericalAmountEq) {
        this.nonNumericalAmountEq = nonNumericalAmountEq;
        return this;
    }

    public void setNonNumericalAmountEq(String nonNumericalAmountEq) {
        this.nonNumericalAmountEq = nonNumericalAmountEq;
    }

    public String getUnitId() {
        return unitId;
    }

    public ActiveSubstance unitId(String unitId) {
        this.unitId = unitId;
        return this;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public String getUnit() {
        return unit;
    }

    public ActiveSubstance unit(String unit) {
        this.unit = unit;
        return this;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getUnitEq() {
        return unitEq;
    }

    public ActiveSubstance unitEq(String unitEq) {
        this.unitEq = unitEq;
        return this;
    }

    public void setUnitEq(String unitEq) {
        this.unitEq = unitEq;
    }

    public String getUnitEqId() {
        return unitEqId;
    }

    public ActiveSubstance unitEqId(String unitEqId) {
        this.unitEqId = unitEqId;
        return this;
    }

    public void setUnitEqId(String unitEqId) {
        this.unitEqId = unitEqId;
    }

    public String getPcId() {
        return pcId;
    }

    public ActiveSubstance pcId(String pcId) {
        this.pcId = pcId;
        return this;
    }

    public void setPcId(String pcId) {
        this.pcId = pcId;
    }

    public String getPccId() {
        return pccId;
    }

    public ActiveSubstance pccId(String pccId) {
        this.pccId = pccId;
        return this;
    }

    public void setPccId(String pccId) {
        this.pccId = pccId;
    }

    public String getPccEn() {
        return pccEn;
    }

    public ActiveSubstance pccEn(String pccEn) {
        this.pccEn = pccEn;
        return this;
    }

    public void setPccEn(String pccEn) {
        this.pccEn = pccEn;
    }

    public String getIngredientEqId() {
        return ingredientEqId;
    }

    public ActiveSubstance ingredientEqId(String ingredientEqId) {
        this.ingredientEqId = ingredientEqId;
        return this;
    }

    public void setIngredientEqId(String ingredientEqId) {
        this.ingredientEqId = ingredientEqId;
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
        ActiveSubstance activeSubstance = (ActiveSubstance) o;
        if (activeSubstance.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), activeSubstance.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ActiveSubstance{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", ingredientId='" + getIngredientId() + "'" +
            ", notes='" + getNotes() + "'" +
            ", asEquivalent='" + getAsEquivalent() + "'" +
            ", additionalInfo='" + getAdditionalInfo() + "'" +
            ", amount='" + getAmount() + "'" +
            ", amountEq='" + getAmountEq() + "'" +
            ", nonNumericalAmount='" + getNonNumericalAmount() + "'" +
            ", nonNumericalAmountEq='" + getNonNumericalAmountEq() + "'" +
            ", unitId='" + getUnitId() + "'" +
            ", unit='" + getUnit() + "'" +
            ", unitEq='" + getUnitEq() + "'" +
            ", unitEqId='" + getUnitEqId() + "'" +
            ", pcId='" + getPcId() + "'" +
            ", pccId='" + getPccId() + "'" +
            ", pccEn='" + getPccEn() + "'" +
            ", ingredientEqId='" + getIngredientEqId() + "'" +
            "}";
    }
}
