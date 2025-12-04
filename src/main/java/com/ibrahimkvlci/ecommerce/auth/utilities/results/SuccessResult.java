package com.ibrahimkvlci.ecommerce.auth.utilities.results;

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
