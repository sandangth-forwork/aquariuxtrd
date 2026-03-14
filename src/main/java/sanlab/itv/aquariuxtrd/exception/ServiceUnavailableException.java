package sanlab.itv.aquariuxtrd.exception;

public class ServiceUnavailableException extends AbstractException {

    private final String msg;

    public ServiceUnavailableException(String msg) {
        super(msg);
        this.msg = msg;
    }

    @Override
    public String message() {
        return this.msg;
    }
}
