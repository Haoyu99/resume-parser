package com.example.resumeparser.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

/***
 * @title Response
 * @description
 * @author haoyu99
 * @version 1.0.0
 * @create 2024/7/21 10:12
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Response<T> {

    private String status;
    @Nullable private String message;
    @Nullable private T data;

    private static final String SUCCESS_STATUS = "200";
    private static final String FAIL = "500";

    public static <T> Response<T> ok(T data) {
        return new Response<>(SUCCESS_STATUS, null, data);
    }
    public static <T> Response<T> ok(T data, String message) {
        return new Response<>(SUCCESS_STATUS, message, data);
    }

    public static <T> Response<T> ok() {
        return new Response<>(SUCCESS_STATUS, null, null);
    }

    public static <T> Response<T> error(int status, String message) {
        return new Response<>(String.valueOf(status), message, null);
    }

    public static <T> Response<T> error(String error) {
        return new Response<>(FAIL, error, null);
    }
}