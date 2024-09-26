package io.thoughtware.kaelthas;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({"io.thoughtware.kaelthas"})
public class KaelthasServerAutoConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(KaelthasServerAutoConfiguration.class);
}


