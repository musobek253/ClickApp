package com.musobek.clickapp.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String color;
    private String name;
    @ManyToOne
    private Workspace workspaceId;

    public Tag(String name, String color, Workspace workspace) {
        this.name= name;
        this.color = color;
        this.workspaceId = workspace;
    }
}
