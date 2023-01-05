package com.musobek.clickapp.entity;

import com.musobek.clickapp.entity.template.AbsLongEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Category extends AbsLongEntity {

    private String name;
    @ManyToOne
    private Project projectId;
    private String accessType;
    private String archived;
    private String color;
}

