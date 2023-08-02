package com.innovationserver.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class DefaultRes<T> {
    //프론트와 소통시 편리함을 위해 Res의 형태를 고정
    private boolean success;
    private int statusCode;
    private String responseMessage;
    private T data;

    public DefaultRes(final boolean success, final int statusCode, final String responseMessage) {
        this.success = success;
        this.statusCode = statusCode;
        this.responseMessage = responseMessage;
        this.data = null;
    }

    public static<T> DefaultRes<T> res(final boolean success, final int statusCode, final String responseMessage) {
        return res(success, statusCode, responseMessage, null);
    }

    public static<T> DefaultRes<T> res(final boolean success, final int statusCode, final String responseMessage, final T t) {
        return DefaultRes.<T>builder()
                .success(success)
                .data(t)
                .statusCode(statusCode)
                .responseMessage(responseMessage)
                .build();
    }
}