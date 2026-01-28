package com.ibrahimkvlci.ecommerce.auth.services;

public interface EmailService {
    void sendVerificationEmail(String email, String code);
}
