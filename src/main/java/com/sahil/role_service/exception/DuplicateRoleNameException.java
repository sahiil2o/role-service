package com.sahil.role_service.exception;

public class DuplicateRoleNameException extends RuntimeException {
    public DuplicateRoleNameException(String message) {
        super(message);
    }
}
