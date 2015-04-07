package me.entropyx.errorhandler;

/**
 * Created by shaosong on 15/1/8.
 */
public class QuestionQueryException extends Exception {
    public QuestionQueryException() {
        super();
    }
    public QuestionQueryException(Throwable cause) {
        super(cause);
    }
    public QuestionQueryException(String msg) {
        super(msg);
    }
    public QuestionQueryException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
