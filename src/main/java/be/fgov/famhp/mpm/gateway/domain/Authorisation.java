package be.fgov.famhp.mpm.gateway.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Authorisation.
 */
@Document(collection = "authorisation")
public class Authorisation implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    private String id;

    @Field("cti")
    private String cti;

    @Field("authorisation_nbr")
    private String authorisationNbr;

    @Field("authorisation_date")
    private LocalDate authorisationDate;

    @Field("radiation_date")
    private LocalDate radiationDate;

    @Field("suspension_date")
    private LocalDate suspensionDate;

    @Field("radiation_note")
    private String radiationNote;

    @Field("suspension_note")
    private String suspensionNote;

    @Field("strength")
    private String strength;

    @Field("pc_name")
    private String pcName;

    @Field("pc_id")
    private String pcId;

    @Field("pc_nbr")
    private String pcNbr;

    @Field("name")
    private String name;

    @Field("status")
    private String status;

    @Field("status_id")
    private String statusId;

    @Field("pg_usage")
    private String pgUsage;

    @Field("pg_procedure_type")
    private String pgProcedureType;

    @Field("pg_authorisation_type")
    private String pgAuthorisationType;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCti() {
        return cti;
    }

    public Authorisation cti(String cti) {
        this.cti = cti;
        return this;
    }

    public void setCti(String cti) {
        this.cti = cti;
    }

    public String getAuthorisationNbr() {
        return authorisationNbr;
    }

    public Authorisation authorisationNbr(String authorisationNbr) {
        this.authorisationNbr = authorisationNbr;
        return this;
    }

    public void setAuthorisationNbr(String authorisationNbr) {
        this.authorisationNbr = authorisationNbr;
    }

    public LocalDate getAuthorisationDate() {
        return authorisationDate;
    }

    public Authorisation authorisationDate(LocalDate authorisationDate) {
        this.authorisationDate = authorisationDate;
        return this;
    }

    public void setAuthorisationDate(LocalDate authorisationDate) {
        this.authorisationDate = authorisationDate;
    }

    public LocalDate getRadiationDate() {
        return radiationDate;
    }

    public Authorisation radiationDate(LocalDate radiationDate) {
        this.radiationDate = radiationDate;
        return this;
    }

    public void setRadiationDate(LocalDate radiationDate) {
        this.radiationDate = radiationDate;
    }

    public LocalDate getSuspensionDate() {
        return suspensionDate;
    }

    public Authorisation suspensionDate(LocalDate suspensionDate) {
        this.suspensionDate = suspensionDate;
        return this;
    }

    public void setSuspensionDate(LocalDate suspensionDate) {
        this.suspensionDate = suspensionDate;
    }

    public String getRadiationNote() {
        return radiationNote;
    }

    public Authorisation radiationNote(String radiationNote) {
        this.radiationNote = radiationNote;
        return this;
    }

    public void setRadiationNote(String radiationNote) {
        this.radiationNote = radiationNote;
    }

    public String getSuspensionNote() {
        return suspensionNote;
    }

    public Authorisation suspensionNote(String suspensionNote) {
        this.suspensionNote = suspensionNote;
        return this;
    }

    public void setSuspensionNote(String suspensionNote) {
        this.suspensionNote = suspensionNote;
    }

    public String getStrength() {
        return strength;
    }

    public Authorisation strength(String strength) {
        this.strength = strength;
        return this;
    }

    public void setStrength(String strength) {
        this.strength = strength;
    }

    public String getPcName() {
        return pcName;
    }

    public Authorisation pcName(String pcName) {
        this.pcName = pcName;
        return this;
    }

    public void setPcName(String pcName) {
        this.pcName = pcName;
    }

    public String getPcId() {
        return pcId;
    }

    public Authorisation pcId(String pcId) {
        this.pcId = pcId;
        return this;
    }

    public void setPcId(String pcId) {
        this.pcId = pcId;
    }

    public String getPcNbr() {
        return pcNbr;
    }

    public Authorisation pcNbr(String pcNbr) {
        this.pcNbr = pcNbr;
        return this;
    }

    public void setPcNbr(String pcNbr) {
        this.pcNbr = pcNbr;
    }

    public String getName() {
        return name;
    }

    public Authorisation name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public Authorisation status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusId() {
        return statusId;
    }

    public Authorisation statusId(String statusId) {
        this.statusId = statusId;
        return this;
    }

    public void setStatusId(String statusId) {
        this.statusId = statusId;
    }

    public String getPgUsage() {
        return pgUsage;
    }

    public Authorisation pgUsage(String pgUsage) {
        this.pgUsage = pgUsage;
        return this;
    }

    public void setPgUsage(String pgUsage) {
        this.pgUsage = pgUsage;
    }

    public String getPgProcedureType() {
        return pgProcedureType;
    }

    public Authorisation pgProcedureType(String pgProcedureType) {
        this.pgProcedureType = pgProcedureType;
        return this;
    }

    public void setPgProcedureType(String pgProcedureType) {
        this.pgProcedureType = pgProcedureType;
    }

    public String getPgAuthorisationType() {
        return pgAuthorisationType;
    }

    public Authorisation pgAuthorisationType(String pgAuthorisationType) {
        this.pgAuthorisationType = pgAuthorisationType;
        return this;
    }

    public void setPgAuthorisationType(String pgAuthorisationType) {
        this.pgAuthorisationType = pgAuthorisationType;
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
        Authorisation authorisation = (Authorisation) o;
        if (authorisation.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), authorisation.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Authorisation{" +
            "id=" + getId() +
            ", cti='" + getCti() + "'" +
            ", authorisationNbr='" + getAuthorisationNbr() + "'" +
            ", authorisationDate='" + getAuthorisationDate() + "'" +
            ", radiationDate='" + getRadiationDate() + "'" +
            ", suspensionDate='" + getSuspensionDate() + "'" +
            ", radiationNote='" + getRadiationNote() + "'" +
            ", suspensionNote='" + getSuspensionNote() + "'" +
            ", strength='" + getStrength() + "'" +
            ", pcName='" + getPcName() + "'" +
            ", pcId='" + getPcId() + "'" +
            ", pcNbr='" + getPcNbr() + "'" +
            ", name='" + getName() + "'" +
            ", status='" + getStatus() + "'" +
            ", statusId='" + getStatusId() + "'" +
            ", pgUsage='" + getPgUsage() + "'" +
            ", pgProcedureType='" + getPgProcedureType() + "'" +
            ", pgAuthorisationType='" + getPgAuthorisationType() + "'" +
            "}";
    }
}
