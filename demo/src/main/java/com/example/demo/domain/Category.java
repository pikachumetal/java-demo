package com.example.demo.domain;

import manifold.ext.props.rt.api.var;
import org.hibernate.annotations.*;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity(name = "Category")
@Table(name = "categories")
@SQLDelete(sql = "UPDATE categories SET deleted=true WHERE id=? AND deleted=false")
@FilterDef(name = "noDeletedCategory", parameters = @ParamDef(name = "showDeleted", type = "boolean"))
@Filter(name = "noDeletedCategory",condition = ":showDeleted OR deleted = :showDeleted")
public class Category extends AggregateRoot<String> implements Serializable {
    @Column(name = "description", columnDefinition = "NVARCHAR(256)", nullable = false, unique = true)
    @Basic(optional = false)
    @var
    String description;

    @Column(name = "email", columnDefinition = "NVARCHAR(128)", nullable = false)
    @Basic(optional = false)
    @var
    String email;

    @Column(name = "active", columnDefinition = "BIT", nullable = false)
    @Basic(optional = false)
    @var
    boolean active;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    @Where(clause = "deleted = false")
    private final List<Question> questions;

    public Category() {
        this(UUID.randomUUID().toString(), "", "", new ArrayList<>(), null, null);
    }

    public Category(String description, String email) {
        this(UUID.randomUUID().toString(), description, email, new ArrayList<>(), null, null);
    }

    public Category(String description, String email, List<Question> questions) {
        this(UUID.randomUUID().toString(), description, email, questions, null, null);
    }

    public Category(String id, String description, String email, List<Question> questions, Instant created, Instant updated) {
        this.setId(id);

        this.description = description;
        this.email = email;
        this.questions = questions;
        this.deleted = false;
        this.active = true;

        this.created = created;
        this.updated = updated;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        var item = (Category) o;
        return Objects.equals(id, item.id) &&
                Objects.equals(description, item.description) &&
                Objects.equals(active, item.active) &&
                Objects.equals(questions, item.questions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description);
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", email='" + email + '\'' +
                ", active='" + active + '\'' +
                '}';
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestion(Question question) {
        this.questions.removeIf(o -> Objects.equals(o.id, question.id));
        this.questions.add(question);
    }

    public void removeQuestion(Question question) {
        this.questions.removeIf(o -> Objects.equals(o.id, question.id));
    }
}
