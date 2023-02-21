package hello.api.interceptor;

import hello.api.dto.ErrorResponse;
import hello.api.threadlocalstorage.ErrorInformation;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@RequiredArgsConstructor
@Component
public class ExceptionResponseInterceptor implements HandlerInterceptor {

    private final ThreadLocal<ErrorInformation> errorInformationTls;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
        Object handler, Exception ex) throws Exception {
        threadLocalExceptionControl(request, response);
    }

    private void threadLocalExceptionControl(HttpServletRequest request,
        HttpServletResponse response)
        throws IOException {
        ErrorInformation errorInformation = errorInformationTls.get();
        if (errorInformation.getErrorType() != null) {
            String errorType = errorInformation.getErrorType();
            String errorDetail = errorInformation.getErrorDetail();
            String errorTitle = errorInformation.getErrorTitle();
            errorInformationTls.remove();

            log.warn("TLSExceptionResponseInterceptor, error type={}", errorType);
            ErrorResponse error = new ErrorResponse(errorType, errorTitle,
                response.getStatus(), errorDetail, request.getRequestURI());
            String errorResponseBody = objectMapper.writeValueAsString(error);
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(errorResponseBody);
        }
    }
}
