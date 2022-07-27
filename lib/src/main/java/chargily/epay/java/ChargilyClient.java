package chargily.epay.java;

import br.com.fluentvalidator.exception.ValidationException;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;

public class ChargilyClient {
    public ChargilyClient(String apiKey) {
        chargilyApi = new Retrofit.Builder()
                .baseUrl("https://epay.chargily.com.dz")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ChargilyApi.class);
        this.apiKey = apiKey;
    }

    private final String apiKey;
    private final ChargilyApi chargilyApi;

    public Response<ChargilyResponse> createInvoice(Invoice invoice) throws IOException, ValidationException {
        var validations = new InvoiceValidator().validate(invoice);
        if(!validations.isValid())
            throw new InvoiceException(validations);
        return chargilyApi.createInvoice(apiKey, invoice).execute();
    }


}
