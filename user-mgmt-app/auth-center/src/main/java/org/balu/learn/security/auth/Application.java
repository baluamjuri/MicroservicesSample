package org.balu.learn.security.auth;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;



/**
 * Spring boot app.
 *
 */

@SpringBootApplication
@EnableAuthorizationServer
@EnableDiscoveryClient
public class Application {
	private static final Logger logger = LogManager.getLogger(Application.class);

    public static void main(String[] args) {

        SpringApplication.run(Application.class, args);
        logger.info("Authentication center started");
    }
}
