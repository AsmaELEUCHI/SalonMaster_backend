package com.salonMaster_springBoot.salonMaster.config;

import java.security.SecureRandom;
import java.util.Base64;

public class KeyGenerator {
    public static void main(String[] args) {
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[64]; // 64 octets pour HS512, ou ajuster selon besoin
        random.nextBytes(key);
        String base64Key = Base64.getEncoder().encodeToString(key);
        System.out.println("Generated Key: " + base64Key);
    }
}
