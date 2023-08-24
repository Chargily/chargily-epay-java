package chargily.epay.java;

import br.com.fluentvalidator.exception.ValidationException;
import chargily.epay.java.exception.ChargilyApiException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import javax.annotation.Nonnull;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.awaitility.Awaitility.await;

class ChargilyClientTest {

    private static ChargilyClient chargilyClient;
    private boolean isResponse = false;
    private int responseStatusCode = 0;
    private final int HTTP_STATUS_CREATED = 201;
    private ChargilyResponse chargilyResponse;
    private final String CHARGILY_CHECKOUT_URL = "https://epay.chargily.com.dz/checkout/";

    @BeforeAll
    static void init() {
        chargilyClient = new ChargilyClient("api_KFWtdBczv0qnAMHNxGXCGVK93yEZahZwr4EgFa4xmfnLTIJkezPvW0LgqholrC7S");
    }

    @Test
    void should_create_invoice_and_return_chargily_response() throws IOException {
        // Given
        Invoice invoice = getInvoice();

        // When
        ChargilyResponse chargilyResponse = chargilyClient.submitInvoice(invoice);

        // Then
        assertThat(chargilyResponse).isNotNull();
        assertThat(chargilyResponse.getCheckoutUrl()).contains(CHARGILY_CHECKOUT_URL);
    }

    @Test
    void should_throw_validation_exception_when_client_invoice_is_null() {
        // Given
        Invoice invoice = getInvoice();
        invoice.setClientName(null);

        // When, Then
        assertThatExceptionOfType(ValidationException.class)
                .isThrownBy(() -> chargilyClient.submitInvoice(invoice))
                .withMessageContaining("client name cannot be null or empty");

    }

    @Test
    void should_throw_chargily_api_exception_when_secret_not_valid() {
        // Given
        Invoice invoice = getInvoice();
        ChargilyClient fakeChargilyClient = new ChargilyClient("api_KFWtdBczv0qnAMHNxGXCGVK93yEAahZwr4EgFa4xmfnLTIJkezPvW0LgqholrC7S");
        // When, Then
        assertThatExceptionOfType(ChargilyApiException.class)
                .isThrownBy(() -> fakeChargilyClient.submitInvoice(invoice))
                .withMessageContaining("Chargily API call failed with error code");
    }

    @Test
    void should_create_invoice_async_and_get_chargily_response() {
        // Given
        Invoice invoice = getInvoice();

        Callback<ChargilyResponse> responseCallback = new Callback<>() {

            @Override
            public void onResponse(@Nonnull Call call, Response response) {
                isResponse = true;
                responseStatusCode = response.code();
                chargilyResponse = (ChargilyResponse) response.body();
            }

            @Override
            public void onFailure(@Nonnull Call call, @Nonnull Throwable t) {
            }
        };

        // When
        chargilyClient.createInvoiceAsync(invoice, responseCallback);

        // Then
        await().until(() -> isResponse);
        assertThat(responseStatusCode).isEqualTo(HTTP_STATUS_CREATED);
        assertThat(chargilyResponse.getCheckoutUrl()).contains(CHARGILY_CHECKOUT_URL);
    }

    @Test
    void should_throw_valid_exception_when_amount_invoice_is_null() {
        // Given
        Invoice invoice = getInvoice();
        invoice.setAmount(null);

        // When, Then
        assertThatExceptionOfType(ValidationException.class)
                .isThrownBy(() -> chargilyClient.createInvoiceAsync(invoice, null))
                .withMessageContaining("invoice amount cannot be less than 75.0");
    }

    private Invoice getInvoice() {
        return new Invoice(
                "Chakhoum Ahmed",
                "rainxh11@gmail.com",
                5.0,
                "https://backend.com/webhook_endpoint",
                "https://frontend.com",
                PaymentMethod.EDAHABIA,
                "5001",
                10000.0
        );
    }
}
