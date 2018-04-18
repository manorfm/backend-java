package br.com.timer.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.rest.RepositoryRestMvcAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@ComponentScan(basePackages = {
		"br.com.timer.converter",
		"br.com.timer.domain",
		"br.com.timer.rest",
		"br.com.timer.service",
		"br.com.timer.main.security",
		"br.com.timer.main.database",
		"br.com.timer.type.converters"})

@EnableJpaRepositories(basePackages = {"br.com.timer.domain"})
@EntityScan("br.com.timer.domain")
@ConfigurationProperties
@EnableAspectJAutoProxy
@SpringBootApplication
@EnableAutoConfiguration(exclude = RepositoryRestMvcAutoConfiguration.class)
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}