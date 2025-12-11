package com.clon.etoro.application.usecase;

import lombok.Data;

@Data
public class DeleteUserRequest {

    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
