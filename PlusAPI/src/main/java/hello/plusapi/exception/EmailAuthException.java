package hello.plusapi.exception;

public class EmailAuthException extends RuntimeException{

    public EmailAuthException(String message) {
        super(message);
    }
}
