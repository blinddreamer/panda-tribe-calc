package com.example.pandatribe.utils;

import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class Helper {

    public String getCodes() {
        String codeVerifier = generateRandomCodeVerifier();

        // Generate the code_challenge using SHA-256
        String codeChallenge = generateCodeChallenge(codeVerifier);
        return codeChallenge;

    }

    public String generateIconLink(Integer typeId){
        return String.format("https://images.evetech.net/types/%d/icon?size=32",typeId);
    }

    private String generateRandomCodeVerifier() {
        byte[] randomBytes = new byte[32]; // 256 bits
        ThreadLocalRandom.current().nextBytes(randomBytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes);
    }

    private String generateCodeChallenge(String codeVerifier) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(codeVerifier.getBytes());
            return Base64.getUrlEncoder().withoutPadding().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            // Handle the exception appropriately
            e.printStackTrace();
            return null;
        }
    }
}