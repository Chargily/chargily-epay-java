package chargily.epay.java.exception;

public class ChargilyApiException extends RuntimeException {
    private final int errorCode;
    private final String errorBody;

    public ChargilyApiException(int errorCode, String errorBody) {
        super("Chargily API call failed with error code: " + errorCode);
        this.errorCode = errorCode;
        this.errorBody = errorBody;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getErrorBody() {
        return errorBody;
    }
}
