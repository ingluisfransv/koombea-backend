package com.koombea.scrapping.utils;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("errorDetails")
public class ErrorDetails {
    @JsonProperty("code")
    private int code;
    @JsonProperty("error")
    private String error;

    public ErrorDetails(int code, String error) {
        this.code = code;
        this.error = error;
    }

    public enum ErrorCode {
        INTERNAL_ERROR(1001),
        NOT_FOUND(1002),
        UNAUTHORIZED(1003);

        private final int code;

        ErrorCode(int code) {
            this.code = code;
        }

        public int getCode() {
            return code;
        }
    }
}


