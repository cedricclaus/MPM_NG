package be.fgov.famhp.mpm.gateway.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Usage.
 */
@Document(collection = "usage")
public class Usage implements Serializable {

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

    public Usage code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getEn() {
        return en;
    }

    public Usage en(String en) {
        this.en = en;
        return this;
    }

    public void setEn(String en) {
        this.en = en;
    }

    public String getFr() {
        return fr;
    }

    public Usage fr(String fr) {
        this.fr = fr;
        return this;
    }

    public void setFr(String fr) {
        this.fr = fr;
    }

    public String getDe() {
        return de;
    }

    public Usage de(String de) {
        this.de = de;
        return this;
    }

    public void setDe(String de) {
        this.de = de;
    }

    public String getNl() {
        return nl;
    }

    public Usage nl(String nl) {
        this.nl = nl;
        return this;
    }

    public void setNl(String nl) {
        this.nl = nl;
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
        Usage usage = (Usage) o;
        if (usage.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), usage.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Usage{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", en='" + getEn() + "'" +
            ", fr='" + getFr() + "'" +
            ", de='" + getDe() + "'" +
            ", nl='" + getNl() + "'" +
            "}";
    }
}
