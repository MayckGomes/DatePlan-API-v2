package com.mayckgomes.dateplan_api.utils;

import org.apache.commons.codec.digest.DigestUtils;

public class CreatePublicId {

    public static String create(String email, String name){

        var publicId = DigestUtils.sha256Hex(name + ":" + email);

        return publicId.substring(0,8).toUpperCase();

    }

}
