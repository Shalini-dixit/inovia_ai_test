package com.inovia.assignment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Entry Point for Application.
 *
 * @author Shalini Dixit
 */
@SpringBootApplication
public class EmployeePortalApiApplication {

    public static final Logger LOGGER = LoggerFactory.getLogger(EmployeePortalApiApplication.class);

    public static void main(String[] args) {
        final String profiles = System.getProperty("spring.profiles", null);
        if (profiles == null) {
            System.setProperty("spring.profiles.active", "local");
        }
        LOGGER.info("System property spring.profiles.active: {}", System.getProperty("spring.profiles", null));

        SpringApplication.run(EmployeePortalApiApplication.class, args);
    }

}
