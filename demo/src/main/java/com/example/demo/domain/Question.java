package com.example.demo.domain;

import manifold.ext.props.rt.api.var;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;
import org.hibernate.annotations.SQLDelete;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity(name = "Question")
@Table(name = "questions")
@SQLDelete(sql = "UPDATE questions SET deleted=true WHERE id=? AND deleted=false")
@FilterDef(name = "noDeletedQuestion", parameters = @ParamDef(name = "showDeleted", type = "boolean"))
@Filter(name = "noDeletedQuestion", condition = ":showDeleted OR deleted = :showDeleted")
public class Question extends AggregateRoot<String> implements Serializable {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "topic_id", nullable = false)
    @var
    Topic topic;

    @Column(name = "query", columnDefinition = "NVARCHAR(512)", nullable = false)
    @Basic(optional = false)
    @var
    String query;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "answers",  joinColumns = @JoinColumn(name = "question_id"))
    @var
    List<String> answers;

    @Column(name = "email", columnDefinition = "NVARCHAR(128)", nullable = false)
    @Basic(optional = false)
    @var
    String email;

    @Column(name = "active", columnDefinition = "BIT", nullable = false)
    @Basic(optional = false)
    @var
    boolean active;

    public Question() {
        this(UUID.randomUUID().toString(), "", "", new LinkedList<>(), Instant.now(), Instant.now());
    }

    public Question(String query, String email, List<String> answers) {
        this(UUID.randomUUID().toString(), query, email, answers, Instant.now(), Instant.now());
    }

    public Question(String id, String query, String email, List<String> answers, Instant created, Instant updated) {
        this.id = id;

        this.query = query;
        this.email = email;
        this.answers = answers;

        this.deleted = false;
        this.active = true;

        this.created = created;
        this.updated = updated;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        var item = (Question) o;
        return Objects.equals(id, item.id) &&
                Objects.equals(query, item.query) &&
                Objects.equals(email, item.email) &&
                Objects.equals(active, item.active) &&
                Objects.equals(topic, item.topic);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, query, email);
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", question='" + query + '\'' +
                ", email='" + email + '\'' +
                ", active='" + active + '\'' +
                ", topic='" + topic + '\'' +
                '}';
    }
}
