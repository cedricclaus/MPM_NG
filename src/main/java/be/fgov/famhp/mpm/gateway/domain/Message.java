package be.fgov.famhp.mpm.gateway.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Message.
 */
@Document(collection = "message")
public class Message implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    private String id;

    @NotNull
    @Field("message_id")
    private String messageId;

    @NotNull
    @Field("timestamp")
    private ZonedDateTime timestamp;

    @NotNull
    @Field("user")
    private String user;

    @NotNull
    @Field("type")
    private String type;

    @NotNull
    @Field("routing_key")
    private String routingKey;

    @NotNull
    @Field("queue_name")
    private String queueName;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessageId() {
        return messageId;
    }

    public Message messageId(String messageId) {
        this.messageId = messageId;
        return this;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public ZonedDateTime getTimestamp() {
        return timestamp;
    }

    public Message timestamp(ZonedDateTime timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public void setTimestamp(ZonedDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getUser() {
        return user;
    }

    public Message user(String user) {
        this.user = user;
        return this;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getType() {
        return type;
    }

    public Message type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRoutingKey() {
        return routingKey;
    }

    public Message routingKey(String routingKey) {
        this.routingKey = routingKey;
        return this;
    }

    public void setRoutingKey(String routingKey) {
        this.routingKey = routingKey;
    }

    public String getQueueName() {
        return queueName;
    }

    public Message queueName(String queueName) {
        this.queueName = queueName;
        return this;
    }

    public void setQueueName(String queueName) {
        this.queueName = queueName;
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
        Message message = (Message) o;
        if (message.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), message.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Message{" +
            "id=" + getId() +
            ", messageId='" + getMessageId() + "'" +
            ", timestamp='" + getTimestamp() + "'" +
            ", user='" + getUser() + "'" +
            ", type='" + getType() + "'" +
            ", routingKey='" + getRoutingKey() + "'" +
            ", queueName='" + getQueueName() + "'" +
            "}";
    }
}
