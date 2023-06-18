package hello.plusapi.service;

public interface SqsEmailService<T> {

     void sendEmailAuth(T t);
}
