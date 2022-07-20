package chargily.epay.java;

import br.com.fluentvalidator.context.ValidationResult;
import br.com.fluentvalidator.exception.ValidationException;

public class InvoiceException extends ValidationException {

    protected InvoiceException(ValidationResult validationResult)
    {
        super(validationResult);
    }
}
