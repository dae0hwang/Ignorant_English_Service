package hello.api.exception;

public class DuplicatedSubscribeException extends RuntimeException{

    public DuplicatedSubscribeException(String message) {
        super(message);
    }
}
