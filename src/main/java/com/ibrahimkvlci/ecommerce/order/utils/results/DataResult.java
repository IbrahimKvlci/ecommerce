package com.ibrahimkvlci.ecommerce.order.utils.results;

import lombok.Data;

@Data
public class DataResult<T> {
    private boolean success;
    private String message;
    private T data;

    public DataResult(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public DataResult(boolean success, T data) {
        this.success = success;
        this.data = data;
    }

    public DataResult(boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }
}
