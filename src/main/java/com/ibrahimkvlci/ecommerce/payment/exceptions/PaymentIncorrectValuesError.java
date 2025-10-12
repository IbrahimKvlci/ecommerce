package com.ibrahimkvlci.ecommerce.payment.exceptions;
public class PaymentIncorrectValuesError extends RuntimeException {
    public PaymentIncorrectValuesError() {
        super();
    }

    public PaymentIncorrectValuesError(String message) {
        super(message);
    }

    public PaymentIncorrectValuesError(String message, Throwable cause) {
        super(message, cause);
    }

    public PaymentIncorrectValuesError(Throwable cause) {
        super(cause);
    }
}
