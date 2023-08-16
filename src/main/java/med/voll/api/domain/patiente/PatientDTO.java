package med.voll.api.domain.patiente;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import med.voll.api.domain.address.AddressDTO;

public record PatientDTO(
        Long id,
        @NotBlank
        String name,
        @NotBlank
        @Email
        String email,
        @NotBlank
        String cellphone,
        @NotBlank
        @Pattern(regexp = "\\d{11}")
        String cpf,
        Boolean active,
        @NotNull
        @Valid
        AddressDTO address) {
}
