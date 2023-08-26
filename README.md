<img src="https://raw.githubusercontent.com/rainxh11/chargily-epay-java/master/assets/chargily_java.svg" heigh="300">

### Chargily Epay Gateway JAVA Package
![Chargily ePay Gateway](https://raw.githubusercontent.com/Chargily/epay-gateway-php/main/assets/banner-1544x500.png "Chargily ePay Gateway")

This Plugin is to integrate ePayment gateway with Chargily easily.
- Currently support payment by **CIB / EDAHABIA** cards and soon by **Visa / Mastercard** 
- This repo is recently created for **Java Library**, If you are a developer and want to collaborate to the development of this library, you are welcomed!

# Instaltation:
[![](https://jitpack.io/v/chargily/chargily-epay-java.svg)](https://jitpack.io/#chargily/chargily-epay-java)
To install add Jitpack Repository & chargily package dependency to `build.gradle` gradle build file:
```groovy
repositories {
    maven { url 'https://jitpack.io' }
}
dependencies {
    implementation 'com.github.chargily:chargily-epay-java:1.1'
}
```
# Example Usage

## Sync
```java
import chargily.epay.java.*;

class ChargilyApp {
    public static void main() {
        ChargilyClient client = new ChargilyClient("[API_KEY]");
        Invoice invoice = new Invoice(
                "Chakhoum Ahmed",
                "rainxh11@gmail.com",
                5.0,
                "https://backend.com/webhook_endpoint",
                "https://frontend.com",
                PaymentMethod.EDAHABIA,
                "5001",
                10000.0);
        try {
            ChargilyResponse response = client.submitInvoice(invoice);
            if (response.isSuccess()) {
                response.getStatusCode();
                response.getCheckoutUrl();
            } else {
                response.getStatusCode();
                response.getErrorBody();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
```

## Async
```java
import chargily.epay.java.*;

class ChargilyApp {
    public static void main() {

        ChargilyClient client = new ChargilyClient("[API_KEY]");
        Invoice invoice = new Invoice(
                "Chakhoum Ahmed",
                "rainxh11@gmail.com",
                5.0,
                "https://backend.com/webhook_endpoint",
                "https://frontend.com",
                PaymentMethod.EDAHABIA,
                "5001",
                10000.0);

        ChargilyCallback<ChargilyResponse> responseCallback = new ChargilyCallback<>() {

            @Override
            public void onResponse(@Nonnull Call<ChargilyResponse> call, ChargilyResponse response) {
                // do something on response
                if (response.isSuccess()) {
                    response.getStatusCode();
                    response.getCheckoutUrl();
                } else {
                    response.getStatusCode();
                    response.getErrorBody();
                }
            }

            @Override
            public void onFailure(@Nonnull Call<ChargilyResponse> call, @Nonnull Throwable t) {
                // do something on failure
            }
        };

        client.submitInvoiceAsync(invoice, responseCallback);
    }
}
```