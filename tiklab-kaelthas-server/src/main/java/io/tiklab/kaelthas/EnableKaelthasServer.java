package io.tiklab.kaelthas;

import org.springframework.context.annotation.Import;
import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({KaelthasServerAutoConfiguration.class})
public @interface EnableKaelthasServer {
}
