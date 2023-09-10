package chargily.epay.java;

import br.com.fluentvalidator.context.ValidationResult;
import retrofit2.Call;
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
     * @throws IOException      if a problem occurred talking to the server.
     * @throws InvoiceException if the invoice object is not valid.
     * @see #submitInvoiceAsync(Invoice, ChargilyCallback) submitInvoiceAsync
     * @deprecated Use {@link #submitInvoice(Invoice)} instead. This method will be removed in future versions.
     */
    @Deprecated(since = "1.1", forRemoval = true)
    public Response<ChargilyResponse> createInvoice(Invoice invoice) throws IOException, InvoiceException {
        var validations = new InvoiceValidator().validate(invoice);
        if (!validations.isValid())
            throw new InvoiceException(validations);
        return chargilyApi.createInvoice(apiKey, invoice).execute();
    }

    /**
     * Submits an invoice synchronously to the Chargily API and retrieves a ChargilyResponse containing the checkout URL.
     *
     * @param invoice Invoice information to be sent to the Chargily API.
     * @return ChargilyResponse containing the checkout URL.
     * @throws InvoiceException if the invoice object is not valid.
     * @throws IOException      if a problem occurred talking to the server.
     * @see #submitInvoiceAsync(Invoice, ChargilyCallback) submitInvoiceAsync
     */
    public ChargilyResponse submitInvoice(Invoice invoice) throws InvoiceException, IOException {
        ValidationResult validations = new InvoiceValidator().validate(invoice);
        if (!validations.isValid()) {
            throw new InvoiceException(validations);
        }
        Response<ChargilyResponse> response = chargilyApi.createInvoice(apiKey, invoice).execute();
        if (!response.isSuccessful()) {
            return new ChargilyResponse(false, response.code(), null, response.errorBody().string());
        }
        return new ChargilyResponse(true, response.code(), response.body().getCheckoutUrl(), null);
    }

    /**
     * Submits an invoice asynchronously to the Chargily API and provides a custom callback for handling responses and errors.
     *
     * @param invoice  Invoice information to be sent to the Chargily API.
     * @param callback Chargily Callback object with OnResponse and OnFailure methods. The callback
     *                 will receive a {@link ChargilyResponse} object.
     * @throws InvoiceException if the invoice object is not valid.
     * @see #submitInvoice(Invoice) submitInvoice
     */
    public void submitInvoiceAsync(Invoice invoice, ChargilyCallback<ChargilyResponse> callback) throws InvoiceException {
        ValidationResult validations = new InvoiceValidator().validate(invoice);
        if (!validations.isValid())
            throw new InvoiceException(validations);
        chargilyApi.createInvoice(apiKey, invoice).enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<ChargilyResponse> call, Response<ChargilyResponse> response) {
                if (response.isSuccessful()) {
                    callback.onResponse(call, new ChargilyResponse(true, response.code(), response.body().getCheckoutUrl(), null));
                } else {
                    try {
                        callback.onResponse(call, new ChargilyResponse(false, response.code(), null, response.errorBody().string()));
                    } catch (IOException e) {
                        callback.onFailure(call, e);
                    }
                }
            }

            @Override
            public void onFailure(Call<ChargilyResponse> call, Throwable t) {
                callback.onFailure(call, t);
            }
        });
    }

}
