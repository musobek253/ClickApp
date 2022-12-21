package com.musobek.clickapp.entity;

import com.musobek.clickapp.entity.enam.TaskPermission;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class CategoryUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @ManyToOne
    private Category categoryId;

    @ManyToOne
    private User userId;

    @Enumerated(value = EnumType.STRING)
    private TaskPermission taskPermission;
}