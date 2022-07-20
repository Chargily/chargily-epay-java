package chargily.epay.java;

import br.com.fluentvalidator.*;

import static br.com.fluentvalidator.predicate.ComparablePredicate.*;
import static br.com.fluentvalidator.predicate.ObjectPredicate.nullValue;
import static br.com.fluentvalidator.predicate.StringPredicate.*;
import static java.util.function.Predicate.not;

public class InvoiceValidator extends AbstractValidator<Invoice> {

    @Override
    public void rules() {
        ruleFor(Invoice::getClientName)
                .must(not(stringEmptyOrNull()))
                .withMessage("client name cannot be null or empty")
                .critical();
        ruleFor(Invoice::getClientEmail)
                .must(stringMatches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$"))
                .withMessage("client email must be a valid email")
                .critical();
        ruleFor(Invoice::getAmount)
                .must(greaterThanOrEqual(75.0))
                .withMessage("invoice amount cannot be less than 75.0")
                .critical();
        ruleFor(Invoice::getDiscountPercentage)
                .must(between(0.0,100.0))
                .withMessage("discount percentage must be a valid percentage")
                .critical();
        ruleFor(Invoice::getInvoiceNumber)
                .must(not(stringEmptyOrNull()))
                .withMessage("invoice number cannot be null or empty")
                .critical();
        ruleFor(Invoice::getWebhookUrl)
                .must(stringMatches("[(http(s)?):\\/\\/(www\\.)?a-zA-Z0-9@:%._\\+~#=]{2,256}\\.[a-z]{2,6}\\b([-a-zA-Z0-9@:%_\\+.~#?&//=]*)"))
                .withMessage("must be a valid URL");
        ruleFor(Invoice::getBackUrl)
                .must(stringMatches("[(http(s)?):\\/\\/(www\\.)?a-zA-Z0-9@:%._\\+~#=]{2,256}\\.[a-z]{2,6}\\b([-a-zA-Z0-9@:%_\\+.~#?&//=]*)"))
                .withMessage("must be a valid URL");
        ruleFor(Invoice::getPaymentMethod)
                .must(not(nullValue()))
                .withMessage("payment method must be valid");
    }
}
