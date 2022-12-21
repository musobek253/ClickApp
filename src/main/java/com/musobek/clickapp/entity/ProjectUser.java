package com.musobek.clickapp.entity;

import com.musobek.clickapp.entity.enam.Permission;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ProjectUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    @ManyToOne
    private Project projectId;
    @ManyToOne
    private User userId;
    @Enumerated(value = EnumType.STRING)
    private Permission permission;
}
