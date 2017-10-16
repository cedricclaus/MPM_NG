package be.fgov.famhp.mpm.gateway.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Country.
 */
@Document(collection = "country")
public class Country implements Serializable {

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

    @Field("from")
    private Integer from;

    @Field("till")
    private Integer till;

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

    public Country code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getEn() {
        return en;
    }

    public Country en(String en) {
        this.en = en;
        return this;
    }

    public void setEn(String en) {
        this.en = en;
    }

    public String getFr() {
        return fr;
    }

    public Country fr(String fr) {
        this.fr = fr;
        return this;
    }

    public void setFr(String fr) {
        this.fr = fr;
    }

    public String getDe() {
        return de;
    }

    public Country de(String de) {
        this.de = de;
        return this;
    }

    public void setDe(String de) {
        this.de = de;
    }

    public String getNl() {
        return nl;
    }

    public Country nl(String nl) {
        this.nl = nl;
        return this;
    }

    public void setNl(String nl) {
        this.nl = nl;
    }

    public Integer getFrom() {
        return from;
    }

    public Country from(Integer from) {
        this.from = from;
        return this;
    }

    public void setFrom(Integer from) {
        this.from = from;
    }

    public Integer getTill() {
        return till;
    }

    public Country till(Integer till) {
        this.till = till;
        return this;
    }

    public void setTill(Integer till) {
        this.till = till;
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
        Country country = (Country) o;
        if (country.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), country.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Country{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", en='" + getEn() + "'" +
            ", fr='" + getFr() + "'" +
            ", de='" + getDe() + "'" +
            ", nl='" + getNl() + "'" +
            ", from='" + getFrom() + "'" +
            ", till='" + getTill() + "'" +
            "}";
    }
}
