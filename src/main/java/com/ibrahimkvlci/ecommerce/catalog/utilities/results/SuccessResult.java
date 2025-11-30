package com.ibrahimkvlci.ecommerce.catalog.utilities.results;

import lombok.Data;

@Data
public class SuccessResult extends Result {

    public SuccessResult() {
        super(true);
    }

    public SuccessResult(String message) {
        super(true, message);
    }

}
