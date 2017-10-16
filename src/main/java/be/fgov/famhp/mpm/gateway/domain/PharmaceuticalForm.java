package be.fgov.famhp.mpm.gateway.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A PharmaceuticalForm.
 */
@Document(collection = "pharmaceuticalForm")
public class PharmaceuticalForm implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    private String id;

    @NotNull
    @Field("code")
    private String code;

    @Field("en")
    private String en;

    @Field("fr")
    private String fr;

    @Field("de")
    private String de;

    @Field("nl")
    private String nl;

    @Field("edqm_code")
    private String edqmCode;

    @Field("edqm_definition")
    private String edqmDefinition;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public PharmaceuticalForm code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getEn() {
        return en;
    }

    public PharmaceuticalForm en(String en) {
        this.en = en;
        return this;
    }

    public void setEn(String en) {
        this.en = en;
    }

    public String getFr() {
        return fr;
    }

    public PharmaceuticalForm fr(String fr) {
        this.fr = fr;
        return this;
    }

    public void setFr(String fr) {
        this.fr = fr;
    }

    public String getDe() {
        return de;
    }

    public PharmaceuticalForm de(String de) {
        this.de = de;
        return this;
    }

    public void setDe(String de) {
        this.de = de;
    }

    public String getNl() {
        return nl;
    }

    public PharmaceuticalForm nl(String nl) {
        this.nl = nl;
        return this;
    }

    public void setNl(String nl) {
        this.nl = nl;
    }

    public String getEdqmCode() {
        return edqmCode;
    }

    public PharmaceuticalForm edqmCode(String edqmCode) {
        this.edqmCode = edqmCode;
        return this;
    }

    public void setEdqmCode(String edqmCode) {
        this.edqmCode = edqmCode;
    }

    public String getEdqmDefinition() {
        return edqmDefinition;
    }

    public PharmaceuticalForm edqmDefinition(String edqmDefinition) {
        this.edqmDefinition = edqmDefinition;
        return this;
    }

    public void setEdqmDefinition(String edqmDefinition) {
        this.edqmDefinition = edqmDefinition;
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
        PharmaceuticalForm pharmaceuticalForm = (PharmaceuticalForm) o;
        if (pharmaceuticalForm.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), pharmaceuticalForm.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PharmaceuticalForm{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", en='" + getEn() + "'" +
            ", fr='" + getFr() + "'" +
            ", de='" + getDe() + "'" +
            ", nl='" + getNl() + "'" +
            ", edqmCode='" + getEdqmCode() + "'" +
            ", edqmDefinition='" + getEdqmDefinition() + "'" +
            "}";
    }
}
