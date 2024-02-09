package com.hunus.playerprofileservice.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public final class ResourceUtils {
    private ResourceUtils() {
    }

    public static <T> ResponseEntity<T> ok(T entity) {
        return response(entity, HttpStatus.OK);
    }

    public static <T> ResponseEntity<T> created(T entity) {
        return response(entity, HttpStatus.CREATED);
    }

    public static <T> ResponseEntity<T> badRequest() {
        return response(null, HttpStatus.BAD_REQUEST);
    }

    public static <T> ResponseEntity<T> notFound() {
        return response(null, HttpStatus.NOT_FOUND);
    }

    public static <T> ResponseEntity<T> serverError() {
        return response(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private static <T> ResponseEntity<T> response(T entity, HttpStatus status) {
        if (entity != null) {
            return new ResponseEntity<>(entity, status);
        } else {
            return new ResponseEntity<>(status);
        }
    }
}
