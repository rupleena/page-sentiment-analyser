package analyser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@org.springframework.boot.autoconfigure.SpringBootApplication
@EnableAutoConfiguration
@ComponentScan({"analyser.controller", "analyser.util", "analyser.service", "analyser.sentiment", "analyser.sentiment.configuration"})
@EnableJpaRepositories
public class SpringBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootApplication.class, args);
    }
}