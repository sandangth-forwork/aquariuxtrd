package sanlab.itv.aquariuxtrd.exception;

public abstract class AbstractException extends RuntimeException {

    public AbstractException(String msg) {
        super(msg);
    }

    public abstract String message();

}
