package com.musobek.clickapp.dto;

import com.musobek.clickapp.entity.CheckList;
import com.musobek.clickapp.entity.User;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ChecklistItemDto {

    private String name;


    private Integer checkListId;


}
