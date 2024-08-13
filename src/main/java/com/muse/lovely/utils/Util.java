package com.muse.lovely.utils;

import java.security.SecureRandom;

public class Util {

     public static String generateOtp(int length) {
        final String characters = "0123456789";
        StringBuilder stringBuilder = new StringBuilder();
        SecureRandom randomString = new SecureRandom();

        for (int i = 0; i <length; i++){
            int randomIndex = randomString.nextInt(characters.length());
            stringBuilder.append(characters.charAt(randomIndex));
        }
        return stringBuilder.toString();
    }
}
