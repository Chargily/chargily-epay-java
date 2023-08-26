package chargily.epay.java;

import com.google.gson.annotations.SerializedName;

public class ChargilyResponse {

    @SerializedName("checkout_url")
    private String checkoutUrl;
    private int statusCode;
    private boolean success;
    private String errorBody;

    public ChargilyResponse(boolean success, int statusCode, String checkoutUrl, String errorBody) {
        this.checkoutUrl = checkoutUrl;
        this.statusCode = statusCode;
        this.errorBody = errorBody;
        this.success = success;

    }

    public ChargilyResponse() {
    }


    public String getCheckoutUrl() {
        return checkoutUrl;
    }

    public boolean isSuccess() {
        return success;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getErrorBody() {
        return errorBody;
    }

}
