package chargily.epay.java;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ChargilyApi {
    @Headers("Accept: application/json")
    @POST("/api/invoice")
    Call<ChargilyResponse> createInvoice(@Header("X-Authorization") String apiKey,
                                         @Body Invoice invoice);
}

