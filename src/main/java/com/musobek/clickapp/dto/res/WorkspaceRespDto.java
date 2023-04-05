package com.musobek.clickapp.dto.res;

import lombok.*;

import java.sql.Timestamp;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class WorkspaceRespDto {

    private Long id;

    private String name;

    private String color;

    private Long owner;

    private String initialLetter;

    private Timestamp createdAt;
}
