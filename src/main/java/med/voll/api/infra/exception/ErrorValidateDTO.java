package med.voll.api.infra.exception;

import org.springframework.validation.FieldError;

public record ErrorValidateDTO(String field, String message) {
    public ErrorValidateDTO(FieldError error){
        this(error.getField(), error.getDefaultMessage());
    }
}
