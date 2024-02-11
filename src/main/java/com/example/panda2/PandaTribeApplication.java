package com.example.panda2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.concurrent.ThreadLocalRandom;

@SpringBootApplication
public class PandaTribeApplication {

    public static void main(String[] args) {
        SpringApplication.run(PandaTribeApplication.class, args);
    }
}
