package hello.plusapi.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import hello.plusapi.dto.ErrorResponse;
import hello.plusapi.threadlocalstorage.ErrorInformation;
import hello.plusapi.threadlocalstorage.ErrorInformationTlsContainer;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@RequiredArgsConstructor
public class ExceptionResponseInterceptor implements HandlerInterceptor {

    private final ErrorInformationTlsContainer errorInformationTlsContainer;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
        Object handler, Exception ex) throws Exception {
        threadLocalExceptionControl(request, response);
    }

    private void threadLocalExceptionControl(HttpServletRequest request,
        HttpServletResponse response)
        throws IOException {
        if (!errorInformationTlsContainer.getThreadLocal().get().getErrorTitle().equals("none")) {
            ErrorInformation errorInformation = errorInformationTlsContainer.getThreadLocal().get();
            String errorType = errorInformation.getErrorType();
            String errorDetail = errorInformation.getErrorDetail();
            String errorTitle = errorInformation.getErrorTitle();
            errorInformationTlsContainer.removeThreadLocal();
            log.warn("TLSExceptionResponseInterceptor, error type={}", errorType);
            ErrorResponse error = new ErrorResponse(errorType, errorTitle,
                response.getStatus(), errorDetail, request.getRequestURI());
            String errorResponseBody = objectMapper.writeValueAsString(error);
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(errorResponseBody);
        }
    }
}
