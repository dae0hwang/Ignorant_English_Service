package hello.api.interceptor;

import hello.api.dto.ErrorResponse;
import hello.api.threadlocalstorage.ErrorInformationTls;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
public class ExceptionResponseInterceptor implements HandlerInterceptor {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final ErrorInformationTls threadLocalStorage = new ErrorInformationTls();

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
        Object handler, Exception ex) throws Exception {
        threadLocalExceptionControl(request, response);
    }

    private void threadLocalExceptionControl(HttpServletRequest request,
        HttpServletResponse response)
        throws IOException {
        if (threadLocalStorage.getErrorType() != null) {
            String errorType = threadLocalStorage.getErrorType();
            String errorDetail = threadLocalStorage.getErrorDetail();
            String errorTitle = threadLocalStorage.getErrorTitle();
            threadLocalStorage.removeErrorType();
            threadLocalStorage.removeErrorDetail();
            threadLocalStorage.removeErrorTitle();
            log.warn("TLSExceptionResponseInterceptor, error type={}", errorType);
            ErrorResponse error = new ErrorResponse(errorType, errorTitle,
                response.getStatus(), errorDetail, request.getRequestURI());
            String errorResponseBody = objectMapper.writeValueAsString(error);
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(errorResponseBody);
        }
    }
}
