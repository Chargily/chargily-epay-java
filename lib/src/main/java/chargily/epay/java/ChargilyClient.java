package chargily.epay.java;

import br.com.fluentvalidator.exception.ValidationException;
import retrofit2.Callback;
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

    /**
     * Create invoice synchronously
     *
     * @param invoice Invoice information to be sent to Chargily API.
     * @return ChargilyResponse that contains checkoutUrl.
     * @throws IOException         if a problem occurred talking to the server.
     * @throws ValidationException if the invoice object is not valid.
     * @see #createInvoiceAsync(Invoice, Callback) createInvoiceAsync
     */
    public Response<ChargilyResponse> createInvoice(Invoice invoice) throws IOException, ValidationException {
        var validations = new InvoiceValidator().validate(invoice);
        if (!validations.isValid())
            throw new InvoiceException(validations);
        return chargilyApi.createInvoice(apiKey, invoice).execute();
    }

    /**
     * Create invoice asynchronously
     *
     * @param invoice  Invoice information to be sent to Chargily API.
     * @param callback Retrofit Callback object with OnResponse and OnFailure.
     * @throws ValidationException if the invoice object is not valid.
     * @see #createInvoice(Invoice) createInvoice
     */
    public void createInvoiceAsync(Invoice invoice, Callback<ChargilyResponse> callback) throws ValidationException {
        var validations = new InvoiceValidator().validate(invoice);
        if (!validations.isValid())
            throw new InvoiceException(validations);
        chargilyApi.createInvoice(apiKey, invoice).enqueue(callback);
    }

}
