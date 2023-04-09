package com.musobek.clickapp.entity;

import com.musobek.clickapp.entity.enam.AccessType;
import com.musobek.clickapp.entity.template.AbsLongEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"name", "space_id"}))
    public class Project extends AbsLongEntity {
        @Column(nullable = false)
        private String name;

        @Column(nullable = false)
        private String color;

        @ManyToOne
        private Space space;

        @Column
        private boolean archived = false;

        @Enumerated(EnumType.STRING)
        private AccessType accessType;

        @OneToMany(mappedBy = "project", cascade = CascadeType.PERSIST)
        private List<Category> categories;
}