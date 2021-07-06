package com.example.demo.domain;

import manifold.ext.props.rt.api.var;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;
import org.hibernate.annotations.SQLDelete;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

@Entity(name = "Message")
@Table(name = "messages")
@SQLDelete(sql = "UPDATE messages SET deleted=true WHERE id=? AND deleted=false")
@FilterDef(name = "noDeletedMessage", parameters = @ParamDef(name = "isDeleted", type = "boolean"))
@Filter(name = "noDeletedMessage", condition = "deleted = :isDeleted")
public class Message extends AggregateRoot<String> implements Serializable {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "authors_id", nullable = false)
    @var
    Author author;

    @Column(name = "title", columnDefinition = "NVARCHAR(128)", nullable = false)
    @Basic(optional = false)
    @var
    String title;

    @Column(name = "message", columnDefinition = "NVARCHAR(512)", nullable = false)
    @Basic(optional = false)
    @var
    String message;

    public Message() {
        this(UUID.randomUUID().toString(), "", "", Instant.now(), Instant.now());
    }

    public Message(String title, String message) {
        this(UUID.randomUUID().toString(), title, message, Instant.now(), Instant.now());
    }

    public Message(String id, String title, String message, Instant createdAt, Instant updatedAt) {
        this.id = id;

        this.title = title;
        this.message = message;
        this.deleted = false;

        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        var item = (Message) o;
        return Objects.equals(id, item.id) &&
                Objects.equals(title, item.title) &&
                Objects.equals(message, item.message) &&
                Objects.equals(author, item.author);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, message);
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", message='" + message + '\'' +
                ", author='" + author + '\'' +
                '}';
    }
}
