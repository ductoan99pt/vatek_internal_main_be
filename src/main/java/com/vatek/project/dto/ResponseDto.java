package com.vatek.project.dto;

import com.vatek.project.jwt.exception.ErrorResponse;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseDto<T> extends ErrorResponse {
    private T content;
    private Long remainTime;

    public ResponseDto(String errorCode, String errorType, String message, T content) {
        super(errorCode, errorType, message);
        this.content = content;
    }

    public ResponseDto() {
    }
}
