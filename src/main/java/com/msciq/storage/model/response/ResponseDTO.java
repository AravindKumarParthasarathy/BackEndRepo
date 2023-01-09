package com.msciq.storage.model.response;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import javax.enterprise.inject.Model;

@Model
@Builder
@ToString
@Getter
public class ResponseDTO {
    private String message;
    private boolean isError;
}
