package hello.api.threadlocalstorage;

import org.springframework.stereotype.Component;

@Component
public class ErrorInformationTlsContainer {

    private ThreadLocal<ErrorInformation> threadLocal = ThreadLocal.withInitial(
        () -> new ErrorInformation("none", "none", "none")
    );

    public ThreadLocal<ErrorInformation> getThreadLocal() {
        return threadLocal;
    }

    public void removeThreadLocal() {
        threadLocal.remove();
    }
}