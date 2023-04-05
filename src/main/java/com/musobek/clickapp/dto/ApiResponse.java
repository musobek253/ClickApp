package com.musobek.clickapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class ApiResponse {
    private String msg;
    private boolean success;
    private  Object object;

    public ApiResponse(String msg, boolean success) {
        this.msg = msg;
        this.success = success;
    }

    public ApiResponse(String msg, boolean success, Object object) {
        this.msg = msg;
        this.success = success;
        this.object = object;
    }
}
