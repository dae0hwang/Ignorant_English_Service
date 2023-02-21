package hello.api.webconfig;

import hello.api.threadlocalstorage.ErrorInformation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ThreadLocalConfig {

    @Bean
    public ThreadLocal<ErrorInformation> errorInformationThreadLocal() {
        return new ThreadLocal<>();
    }
}
