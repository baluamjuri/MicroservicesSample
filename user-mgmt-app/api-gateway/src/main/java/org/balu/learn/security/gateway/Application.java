package org.balu.learn.security.gateway;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;


/**
 * Spring boot app.
 *
 */
@EnableDiscoveryClient
@SpringBootApplication
@EnableZuulProxy
public class Application {

	private static final Logger logger = LogManager.getLogger(Application.class);
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        logger.info("Zuul API gateway called....");
    }
}
