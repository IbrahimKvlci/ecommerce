package com.ibrahimkvlci.ecommerce.payment.utils;

import java.security.SecureRandom;

public class RandomNumberUtil {

    public static String getRandomNumberBase16(int length){
        SecureRandom random = new SecureRandom();

        byte[] data = new byte[length];

        random.nextBytes(data);

        StringBuilder result = new StringBuilder(length);

        for (byte b : data) {
            int hexValue = (b & 0xFF) % 16;
            result.append(Integer.toHexString(hexValue).toUpperCase());
        }

        return result.toString();
    }
}
