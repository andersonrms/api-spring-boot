package med.voll.api.domain.doctor;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import med.voll.api.domain.enuns.Specialty;
import med.voll.api.domain.address.AddressDTO;

public record DoctorDTO(
        @NotBlank
        String name,
        @NotBlank
        @Email
        String email,
        @NotBlank
        @Pattern(regexp = "\\d{4,6}")
        String crm,
        @NotBlank
        @Pattern(regexp = "\\d{10,11}")
        String cellphone,
        @NotNull
        Specialty specialty,
        @NotNull
        @Valid
        AddressDTO address) {
}
