package com.musobek.clickapp.dto;

import lombok.*;

@Setter
@Getter
public class CommentDto {
    private String text;

    private Long taskId;
}
