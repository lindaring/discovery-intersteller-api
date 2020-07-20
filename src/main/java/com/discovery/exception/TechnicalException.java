package com.discovery.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Data
public class TechnicalException {
    private String message;
    private HttpStatus status;
    private int code;
    private String type;
}