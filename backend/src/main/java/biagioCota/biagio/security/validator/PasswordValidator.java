package biagioCota.biagio.security.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<ValidPassword, String> {

    @Override
    public void initialize(ValidPassword annotation) {}

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        if (password == null) return false;

        boolean isValid = true;
        StringBuilder message = new StringBuilder();

        if (password.length() < 8) {
            isValid = false;
            message.append("- Minimo 8 caratteri\n");
        }

        if (password.length() > 50) {
            isValid = false;
            message.append("- Massimo 50 caratteri\n");
        }

        if (!password.matches(".*[A-Z].*")) {
            isValid = false;
            message.append("- Almeno una lettera maiuscola\n");
        }

        if (!password.matches(".*[a-z].*")) {
            isValid = false;
            message.append("- Almeno una lettera minuscola\n");
        }

        if (!password.matches(".*[0-9].*")) {
            isValid = false;
            message.append("- Almeno un numero\n");
        }

        if (!password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};:'\",.<>?/\\\\|`~].*")) {
            isValid = false;
            message.append("- Almeno un carattere speciale (!@#$%^&*)\n");
        }

        if (!isValid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(message.toString()).addConstraintViolation();
        }

        return isValid;
    }
}
