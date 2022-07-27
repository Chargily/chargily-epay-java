package chargily.epay.java;

import com.google.gson.annotations.SerializedName;

public class Invoice {

    public Invoice(String clientName,
                   String clientEmail,
                   Double discountPercentage,
                   String webhookUrl,
                   String backUrl,
                   PaymentMethod paymentMethod,
                   String invoiceNumber,
                   Double amount) {
        this.clientName = clientName;
        this.clientEmail = clientEmail;
        this.discountPercentage = discountPercentage;
        this.webhookUrl = webhookUrl;
        this.backUrl = backUrl;
        this.paymentMethod = paymentMethod;
        this.invoiceNumber = invoiceNumber;
        this.amount = amount;
    }

    public String getClientName() {
        return clientName;
    }

    public void setDiscountPercentage(Double discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    @SerializedName("client")
    public String clientName;

    public String getClientEmail() {
        return clientEmail;
    }

    public void setClientEmail(String clientEmail) {
        this.clientEmail = clientEmail;
    }

    @SerializedName("client_email")
    public String clientEmail;

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public Double getDiscountPercentage() {
        return discountPercentage;
    }

    @SerializedName("discount")
    public Double discountPercentage;

    public String getWebhookUrl() {
        return webhookUrl;
    }

    public void setWebhookUrl(String webhookUrl) {
        this.webhookUrl = webhookUrl;
    }

    @SerializedName("webhook_url")
    public String webhookUrl;

    public String getBackUrl() {
        return backUrl;
    }

    public void setBackUrl(String backUrl) {
        this.backUrl = backUrl;
    }

    @SerializedName("back_url")
    public String backUrl;

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    @SerializedName("mode")
    public PaymentMethod paymentMethod;

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    @SerializedName("invoice_number")
    public String invoiceNumber;

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double amount;
}


