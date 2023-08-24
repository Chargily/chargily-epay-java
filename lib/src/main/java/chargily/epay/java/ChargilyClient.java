package chargily.epay.java;

import br.com.fluentvalidator.context.ValidationResult;
import br.com.fluentvalidator.exception.ValidationException;
import chargily.epay.java.exception.ChargilyApiException;
import chargily.epay.java.exception.InvoiceException;
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
     * @deprecated Use {@link #submitInvoice(Invoice)} instead. This method will be removed in future versions.
     */
    @Deprecated(since = "1.1", forRemoval = true)
    public Response<ChargilyResponse> createInvoice(Invoice invoice) throws IOException, ValidationException {
        var validations = new InvoiceValidator().validate(invoice);
        if (!validations.isValid())
            throw new InvoiceException(validations);
        return chargilyApi.createInvoice(apiKey, invoice).execute();
    }

    /**
     * Submit invoice synchronously
     *
     * @param invoice Invoice information to be sent to Chargily API.
     * @return ChargilyResponse that contains checkoutUrl.
     * @throws IOException         if a problem occurred talking to the server.
     * @throws ValidationException if the invoice object is not valid.
     * @throws ChargilyApiException if the response is not successful with the ChargilyApi.
     * @see #createInvoiceAsync(Invoice, Callback) createInvoiceAsync
     */
    public ChargilyResponse submitInvoice(Invoice invoice) throws ValidationException, IOException {
        ValidationResult validations = new InvoiceValidator().validate(invoice);
        if (!validations.isValid()) {
            throw new InvoiceException(validations);
        }
        Response<ChargilyResponse> response = chargilyApi.createInvoice(apiKey, invoice).execute();
        if (!response.isSuccessful()) {
            throw new ChargilyApiException(response.code(), response.errorBody().string());
        }
        return response.body();
    }

    /**
     * Create invoice asynchronously
     *
     * @param invoice  Invoice information to be sent to Chargily API.
     * @param callback Retrofit Callback object with OnResponse and OnFailure.
     * @throws ValidationException if the invoice object is not valid.
     * @see #submitInvoice(Invoice) createInvoice
     */
    public void createInvoiceAsync(Invoice invoice, Callback<ChargilyResponse> callback) throws ValidationException {
        var validations = new InvoiceValidator().validate(invoice);
        if (!validations.isValid())
            throw new InvoiceException(validations);
        chargilyApi.createInvoice(apiKey, invoice).enqueue(callback);
    }

}
