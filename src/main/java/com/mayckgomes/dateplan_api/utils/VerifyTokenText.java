package com.mayckgomes.dateplan_api.utils;

import com.mayckgomes.dateplan_api.exception.custom.token.TokenInvalidException;

public class VerifyTokenText {

    public static String verifyTokenText(String token) {

        if (token.isEmpty() || token == null || (!token.startsWith("Bearer "))) {

            throw new TokenInvalidException();

        }

        return token.replace("Bearer ", "");

    }

}
