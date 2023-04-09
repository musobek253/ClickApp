package com.musobek.clickapp.entity;

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
public class Category extends AbsLongEntity {

    @Column(nullable = false)
    private String name;

    @ManyToOne
    private Project project;

    @Column
    private boolean archived = false;

    @Transient
    @OneToMany(mappedBy = "category", cascade = CascadeType.PERSIST)
    private List<Status> statuses;

    public Category(String name, Project project) {
        this.name = name;
        this.project = project;
    }
}

