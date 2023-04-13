package com.musobek.clickapp.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class CheckListItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @Column
    private boolean resolved = false;

    @ManyToOne
    private CheckList checkListId;

    public CheckListItem(String name, CheckList checkList, boolean resolved) {
        this.checkListId = checkList;
        this.name = name;
        this.resolved = resolved;
    }
}

