package hello.plusapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.aws.autoconfigure.context.ContextStackAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication(exclude = {ContextStackAutoConfiguration.class})
public class PlusApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(PlusApiApplication.class, args);
    }

}
