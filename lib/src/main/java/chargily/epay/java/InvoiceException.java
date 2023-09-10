package chargily.epay.java;

import br.com.fluentvalidator.context.ValidationResult;

public class InvoiceException extends RuntimeException {

    public InvoiceException(ValidationResult validationResult)
    {
        super(validationResult.toString());
    }
}
