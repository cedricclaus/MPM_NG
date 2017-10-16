package be.fgov.famhp.mpm.gateway.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A ProductCounter.
 */
@Document(collection = "product_counter")
public class ProductCounter implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    private String id;

    @NotNull
    @Field("name")
    private String name;

    @NotNull
    @Field("number")
    private String number;

    @Field("strength")
    private String strength;

    @Field("pharmaceutical_form")
    private String pharmaceuticalForm;

    @NotNull
    @Field("status")
    private String status;

    @NotNull
    @Field("status_id")
    private String statusId;

    @Field("pc_id")
    private String pcId;

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

    public ProductCounter name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public ProductCounter number(String number) {
        this.number = number;
        return this;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getStrength() {
        return strength;
    }

    public ProductCounter strength(String strength) {
        this.strength = strength;
        return this;
    }

    public void setStrength(String strength) {
        this.strength = strength;
    }

    public String getPharmaceuticalForm() {
        return pharmaceuticalForm;
    }

    public ProductCounter pharmaceuticalForm(String pharmaceuticalForm) {
        this.pharmaceuticalForm = pharmaceuticalForm;
        return this;
    }

    public void setPharmaceuticalForm(String pharmaceuticalForm) {
        this.pharmaceuticalForm = pharmaceuticalForm;
    }

    public String getStatus() {
        return status;
    }

    public ProductCounter status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusId() {
        return statusId;
    }

    public ProductCounter statusId(String statusId) {
        this.statusId = statusId;
        return this;
    }

    public void setStatusId(String statusId) {
        this.statusId = statusId;
    }

    public String getPcId() {
        return pcId;
    }

    public ProductCounter pcId(String pcId) {
        this.pcId = pcId;
        return this;
    }

    public void setPcId(String pcId) {
        this.pcId = pcId;
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
        ProductCounter productCounter = (ProductCounter) o;
        if (productCounter.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), productCounter.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProductCounter{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", number='" + getNumber() + "'" +
            ", strength='" + getStrength() + "'" +
            ", pharmaceuticalForm='" + getPharmaceuticalForm() + "'" +
            ", status='" + getStatus() + "'" +
            ", statusId='" + getStatusId() + "'" +
            ", pcId='" + getPcId() + "'" +
            "}";
    }
}
