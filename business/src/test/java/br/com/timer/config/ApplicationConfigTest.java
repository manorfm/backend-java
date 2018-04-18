package br.com.timer.config;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = { "br.com.timer.domain" })
@ComponentScan(basePackages = { 
		"br.com.timer.rest",
		"br.com.timer.domain",
		"br.com.timer.service"
})
@EntityScan("br.com.timer.domain")
@EnableAspectJAutoProxy
@SpringBootConfiguration
@EnableAutoConfiguration
public class ApplicationConfigTest {

}
