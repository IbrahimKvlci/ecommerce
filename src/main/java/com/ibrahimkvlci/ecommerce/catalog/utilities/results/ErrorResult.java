package com.ibrahimkvlci.ecommerce.catalog.utilities.results;

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
