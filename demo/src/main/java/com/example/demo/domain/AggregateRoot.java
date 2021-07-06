package com.example.demo.domain;

import manifold.ext.props.rt.api.var;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.domain.Persistable;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;

@MappedSuperclass
// @Where(clause = "deleted = false")
// @SQLDelete(sql = "UPDATE authors SET deleted=true WHERE id=? AND deleted=false")
// @FilterDef(name = "deletedCarFilter", parameters = @ParamDef(name = "isDeleted", type = "boolean"))
// @Filter(name = "deletedCarFilter", condition = "deleted = :isDeleted")
public abstract class AggregateRoot<ID> implements Serializable, Persistable<ID> {
    @Transient
    private boolean isNew = true;

    @Override
    public boolean isNew() {
        return isNew;
    }

    @PrePersist
    @PostLoad
    void markNotNew() {
        this.isNew = false;
    }

    @Id
    @Column(name = "id", columnDefinition = "CHAR(36)")
    // @GeneratedValue(generator = "uuid2")
    // @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    private ID id;

    @Override
    public ID getId() {
        return id;
    }

    public void setId(ID id) {
        this.id = id;
    }

    @Column(name = "deleted", columnDefinition = "BIT", nullable = false)
    @Basic(optional = false)
    @var
    boolean deleted;

    @Column(name = "created_at", columnDefinition = "TIMESTAMP", nullable = false, updatable = false)
    @CreationTimestamp
    @var
    Instant createdAt;

    @Column(name = "updated_at", columnDefinition = "TIMESTAMP", nullable = false, updatable = true)
    @UpdateTimestamp
    @var
    Instant updatedAt;
}
