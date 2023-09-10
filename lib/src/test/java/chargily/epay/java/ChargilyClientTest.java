package chargily.epay.java;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import retrofit2.Call;

import javax.annotation.Nonnull;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.awaitility.Awaitility.await;

class ChargilyClientTest {

    public final int HTTP_STATUS_UNAUTHORIZED = 401;
    private static ChargilyClient chargilyClient;
    private boolean isResponse = false;
    private final int HTTP_STATUS_CREATED = 201;
    private ChargilyResponse chargilyResponse;
    private final String CHARGILY_CHECKOUT_URL = "https://epay.chargily.com.dz/checkout/";

    @BeforeAll
    static void init() {
        chargilyClient = new ChargilyClient("api_KFWtdBczv0qnAMHNxGXCGVK93yEZahZwr4EgFa4xmfnLTIJkezPvW0LgqholrC7S");
    }

    @Test
    void should_submit_invoice_and_return_chargily_response_is_true() throws IOException {
        // Given
        Invoice invoice = getInvoice();

        // When
        ChargilyResponse chargilyResponse = chargilyClient.submitInvoice(invoice);

        // Then
        assertThat(chargilyResponse).isNotNull();
        assertThat(chargilyResponse.isSuccess()).isTrue();
        assertThat(chargilyResponse.getStatusCode()).isEqualTo(HTTP_STATUS_CREATED);
        assertThat(chargilyResponse.getCheckoutUrl()).contains(CHARGILY_CHECKOUT_URL);
        assertThat(chargilyResponse.getErrorBody()).isNull();
    }

    @Test
    void should_throw_validation_exception_when_client_invoice_is_null() {
        // Given
        Invoice invoice = getInvoice();
        invoice.setClientName(null);

        // When, Then
        assertThatExceptionOfType(InvoiceException.class)
                .isThrownBy(() -> chargilyClient.submitInvoice(invoice))
                .withMessageContaining("client name cannot be null or empty");

    }

    @Test
    void should_submit_invoice_and_return_chargily_response_is_false() throws IOException {
        // Given
        Invoice invoice = getInvoice();
        ChargilyClient fakeChargilyClient = new ChargilyClient("api_KFWtdBczv0qnAMHNxGXCGVK93yEAahZwr4EgFa4xmfnLTIJkezPvW0LgqholrC7S");
        // When
        ChargilyResponse chargilyResponse = fakeChargilyClient.submitInvoice(invoice);

        // Then
        assertThat(chargilyResponse).isNotNull();
        assertThat(chargilyResponse.isSuccess()).isFalse();
        assertThat(chargilyResponse.getStatusCode()).isEqualTo(HTTP_STATUS_UNAUTHORIZED);
        assertThat(chargilyResponse.getCheckoutUrl()).isNull();
        assertThat(chargilyResponse.getErrorBody()).contains(
                """
                        {"errors":[{"message":"Unauthorized"}]}""");
    }

    @Test
    void should_submit_invoice_async_and_get_chargily_response_is_true() {
        // Given
        Invoice invoice = getInvoice();

        ChargilyCallback<ChargilyResponse> responseCallback = new ChargilyCallback<>() {

            @Override
            public void onResponse(@Nonnull Call<ChargilyResponse> call, ChargilyResponse response) {
                chargilyResponse = response;
                isResponse = true;
            }

            @Override
            public void onFailure(@Nonnull Call<ChargilyResponse> call, @Nonnull Throwable t) {
            }
        };

        // When
        chargilyClient.submitInvoiceAsync(invoice, responseCallback);

        // Then
        await().until(() -> isResponse);
        assertThat(chargilyResponse.isSuccess()).isTrue();
        assertThat(chargilyResponse.getStatusCode()).isEqualTo(HTTP_STATUS_CREATED);
        assertThat(chargilyResponse.getCheckoutUrl()).contains(CHARGILY_CHECKOUT_URL);
    }

    @Test
    void should_submit_invoice_async_and_get_chargily_response_is_false() {
        // Given
        Invoice invoice = getInvoice();
        ChargilyClient fakeChargilyClient = new ChargilyClient("api_KFWtdBczv0qnAMHNxGXCGVK93yEAahZwr4EgFa4xmfnLTIJkezPvW0LgqholrC7S");

        ChargilyCallback<ChargilyResponse> responseCallback = new ChargilyCallback<>() {

            @Override
            public void onResponse(@Nonnull Call<ChargilyResponse> call, ChargilyResponse response) {
                chargilyResponse = response;
                isResponse = true;
            }

            @Override
            public void onFailure(@Nonnull Call<ChargilyResponse> call, @Nonnull Throwable t) {
            }
        };

        // When
        fakeChargilyClient.submitInvoiceAsync(invoice, responseCallback);

        // Then
        await().until(() -> isResponse);
        assertThat(chargilyResponse.isSuccess()).isFalse();
        assertThat(chargilyResponse.getStatusCode()).isEqualTo(HTTP_STATUS_UNAUTHORIZED);
        assertThat(chargilyResponse.getCheckoutUrl()).isNull();
        assertThat(chargilyResponse.getErrorBody()).contains(
                """
                        {"errors":[{"message":"Unauthorized"}]}""");
    }

    @Test
    void should_throw_valid_exception_when_amount_invoice_is_null() {
        // Given
        Invoice invoice = getInvoice();
        invoice.setAmount(null);

        // When, Then
        assertThatExceptionOfType(InvoiceException.class)
                .isThrownBy(() -> chargilyClient.submitInvoiceAsync(invoice, null))
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
