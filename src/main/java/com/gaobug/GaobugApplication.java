package com.gaobug;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author yhht
 */
@SpringBootApplication
public class GaobugApplication {
    public static void main(String[] args) {
        SpringApplication.run(GaobugApplication.class, args);
    }
}
