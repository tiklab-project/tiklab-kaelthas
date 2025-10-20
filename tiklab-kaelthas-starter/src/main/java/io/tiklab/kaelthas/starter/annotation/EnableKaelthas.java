package io.tiklab.kaelthas.starter.annotation;

import io.tiklab.kaelthas.starter.config.KaelthasConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({KaelthasConfiguration.class })
public @interface EnableKaelthas {
}
