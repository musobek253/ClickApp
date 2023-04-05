package com.musobek.clickapp.dto.res;

import com.musobek.clickapp.entity.enam.RoleName;
import lombok.*;

import java.sql.Timestamp;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserRespDto {
    private Long id;

    private String fullName;

    private String email;

    private String initialLetter;

    private String color;

    private RoleName RoleName;

    private Timestamp createdAt;
}
