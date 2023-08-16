package chargily.epay.java;

import com.google.gson.annotations.SerializedName;

public class ChargilyResponse {

    @SerializedName("checkout_url")
    public String checkoutUrl;

    public String getCheckoutUrl() {
        return checkoutUrl;
    }

    public void setCheckoutUrl(String checkoutUrl) {
        this.checkoutUrl = checkoutUrl;
    }
}
