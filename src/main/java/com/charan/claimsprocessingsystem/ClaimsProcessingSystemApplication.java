package com.charan.claimsprocessingsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;  // Add this import – for WAR.

@SpringBootApplication
public class ClaimsProcessingSystemApplication extends SpringBootServletInitializer {  // Add extends – for deployment.
    public static void main(String[] args) {
        SpringApplication.run(ClaimsProcessingSystemApplication.class, args);
    }
}