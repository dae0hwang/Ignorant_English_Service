package hello.api.webconfig;

import hello.api.interceptor.ExceptionResponseInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class InterceptorWebConfig implements WebMvcConfigurer {

    private final ExceptionResponseInterceptor exceptionResponseInterceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(exceptionResponseInterceptor)
            .order(1)
            .addPathPatterns("/**");
    }
}
