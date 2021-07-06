package com.example.demo.domain;

import manifold.ext.props.rt.api.var;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;
import org.hibernate.annotations.SQLDelete;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity(name = "Author")
@Table(name = "authors")
@SQLDelete(sql = "UPDATE authors SET deleted=true WHERE id=?")
// @Where(clause = "deleted = false")
@FilterDef(name = "noDeletedAuthor", parameters = @ParamDef(name = "isDeleted", type = "boolean"))
@Filter(name = "noDeletedAuthor", condition = "deleted = :isDeleted")
public class Author extends AggregateRoot<String> implements Serializable {
    @Column(name = "first_name", columnDefinition = "NVARCHAR(128)", nullable = false)
    @Basic(optional = false)
    @var
    String firstName;

    @Column(name = "last_name", columnDefinition = "NVARCHAR(128)", nullable = false)
    @Basic(optional = false)
    @var
    String lastName;

    @Column(name = "email", columnDefinition = "NVARCHAR(256)", nullable = false, unique = true)
    @Basic(optional = false)
    @var
    String email;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<Message> messages;

    public Author() {
        this(UUID.randomUUID().toString(), "", "", "", new ArrayList<Message>(), null, null);
    }

    public Author(String firstName, String lastName, String email) {
        this(UUID.randomUUID().toString(), firstName, lastName, email, new ArrayList<Message>(), null, null);
    }

    public Author(String name, String surname, String email, List<Message> messages) {
        this(UUID.randomUUID().toString(), name, surname, email, messages, null, null);
    }

    public Author(String id, String firstName, String lastName, String email, List<Message> messages, Instant createdAt, Instant updatedAt) {
        this.setId(id);

        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.messages = messages;
        this.deleted = false;

        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        var item = (Author) o;
        return Objects.equals(id, item.id) &&
                Objects.equals(firstName, item.firstName) &&
                Objects.equals(lastName, item.lastName) &&
                Objects.equals(email, item.email) &&
                Objects.equals(messages, item.messages);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName);
    }

    @Override
    public String toString() {
        return "Author{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessage(Message message) {
        this.messages.removeIf(o -> Objects.equals(o.id, message.id));
        this.messages.add(message);
    }

    public void removeMessage(Message message) {
        this.messages.removeIf(o -> Objects.equals(o.id, message.id));
    }
}
