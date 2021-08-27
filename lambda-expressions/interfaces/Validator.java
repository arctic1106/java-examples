package interfaces;

import java.util.function.Predicate;
import java.util.function.Supplier;

import models.Person;

@FunctionalInterface
public interface Validator {

    ValidatorSupplier on(models.Person p);

    static Validator validate(Predicate<models.Person> predicate, String errorMessage) {
        return p -> {
            if (predicate.test(p)) {
                return () -> p;
            } else {
                return () -> {
                    ValidationException exception = new ValidationException("The object is not valid");
                    exception.addSuppressed(new IllegalArgumentException(errorMessage));
                    throw exception;
                };
            }
        };
    }

    default Validator thenValidate(Predicate<models.Person> predicate, String errorMessage) {
        return p -> {
            try {
                on(p).validate();
                if (predicate.test(p)) {
                    return () -> p;
                } else {
                    return () -> {
                        ValidationException exception = new ValidationException("The object is not valid");
                        exception.addSuppressed(new IllegalArgumentException(errorMessage));
                        throw exception;
                    };
                }
            } catch (ValidationException validationException) {
                if (!predicate.test(p)) {
                    validationException.addSuppressed(new IllegalArgumentException(errorMessage));
                }
                return () -> {
                    throw validationException;
                };
            }
        };
    }

    @FunctionalInterface
    interface ValidatorSupplier extends Supplier<models.Person> {

        default Person validate() {
            return get();
        }
    }

    class ValidationException extends RuntimeException {

        public ValidationException(String errorMessage) {
            super(errorMessage);
        }
    }
}