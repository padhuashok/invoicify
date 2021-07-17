package com.galvanize.invoicify.utils;

import com.galvanize.invoicify.response.GeneralResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class RestUtils {
    public static  <T> ResponseEntity<GeneralResponse<T>> buildResponse(HttpStatus httpStatus, String message, T data){
        GeneralResponse<T> response =  new GeneralResponse<>();
        response.setStatus(httpStatus.value());
        response.setMessage(message);
        response.setData(data);
        return new ResponseEntity<GeneralResponse<T>>(response,httpStatus);
    }

    public static  <T> ResponseEntity<GeneralResponse<T>> buildResponse( T data){
        GeneralResponse<T> response =  new GeneralResponse<>();
        response.setStatus(HttpStatus.OK.value());
        response.setMessage(HttpStatus.OK.name());
        response.setData(data);
        return ResponseEntity.status(HttpStatus.OK)
                .body(response);
    }
}
