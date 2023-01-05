package com.musobek.clickapp.entity;

import com.musobek.clickapp.entity.template.AbsLongEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.Objects;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"name", "owner_id"})})
public class Workspace extends AbsLongEntity {

    @Column(nullable = false)
    private  String name;

    @Column(nullable = false)
    private String color;

    @ManyToOne(optional = false)
    private Attachment attachmentId;
    
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private User owner;
    
    @Column(nullable = false)
    private String initialLetter;

    @PrePersist
    @PreUpdate
    public void setInitialLetterMyMethod() {
        this.initialLetter = name.substring(0, 1);
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Workspace workspace = (Workspace) o;
        return getId() != null && Objects.equals(getId(), workspace.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
    public Workspace(String name, String color, User owner, Attachment avatar) {
        this.name = name;
        this.color = color;
        this.owner = owner;
        this.attachmentId = avatar;
    }
}
