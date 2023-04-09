package com.musobek.clickapp.dto.res;

import com.musobek.clickapp.entity.enam.AccessType;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SpaceRespDto {
    private Long id;

    private String name;

    private String color;

    private Long workspace;

    private String initialLetter;

    private Long icon;

    private Long avatar;
    private Long owner;
    private AccessType accessType;
}
