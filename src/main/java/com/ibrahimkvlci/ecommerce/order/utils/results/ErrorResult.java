package com.ibrahimkvlci.ecommerce.order.utils.results;

import lombok.Data;

@Data
public class ErrorResult extends Result {

    public ErrorResult() {
        super(false);
    }

    public ErrorResult(String message) {
        super(false, message);
    }

}
