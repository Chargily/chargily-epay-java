<img src="https://raw.githubusercontent.com/rainxh11/chargily-epay-java/master/assets/chargily_java.svg" heigh="300">

### Chargily Epay Gateway JAVA Package
![Chargily ePay Gateway](https://raw.githubusercontent.com/Chargily/epay-gateway-php/main/assets/banner-1544x500.png "Chargily ePay Gateway")

This Plugin is to integrate ePayment gateway with Chargily easily.
- Currently support payment by **CIB / EDAHABIA** cards and soon by **Visa / Mastercard** 
- This repo is recently created for **Java Library**, If you are a developer and want to collaborate to the development of this library, you are welcomed!

# Example Usage
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
            var response = client.createInvoice(invoice);
            System.out.println(response.body().checkoutUrl);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
```