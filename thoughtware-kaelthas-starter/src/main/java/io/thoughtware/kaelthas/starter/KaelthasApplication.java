package io.thoughtware.kaelthas.starter;

import io.thoughtware.core.property.PropertyAndYamlSourceFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
@PropertySource(value = {"classpath:application.yaml"}, factory = PropertyAndYamlSourceFactory.class)
public class KaelthasApplication {
    public static void main(String[] args) {
        SpringApplication.run(KaelthasApplication.class,args);
    }
}
