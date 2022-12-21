package com.musobek.clickapp.entity;

import com.musobek.clickapp.entity.enam.DependencyTypes;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class TaskDependency {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private Task taskId;
    @Enumerated(value = EnumType.STRING)
    private DependencyTypes dependencyTypes;
}

