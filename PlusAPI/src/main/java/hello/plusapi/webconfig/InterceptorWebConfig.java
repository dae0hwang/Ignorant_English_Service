package hello.plusapi.webconfig;

import hello.plusapi.interceptor.ExceptionResponseInterceptor;
import hello.plusapi.threadlocalstorage.ErrorInformationTlsContainer;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class InterceptorWebConfig implements WebMvcConfigurer {

    private final ErrorInformationTlsContainer errorInformationTlsContainer;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new ExceptionResponseInterceptor(errorInformationTlsContainer))
            .order(1)
            .addPathPatterns("/**");
    }
}
