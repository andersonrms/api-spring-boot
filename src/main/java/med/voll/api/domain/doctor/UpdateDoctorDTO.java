package med.voll.api.domain.doctor;

import jakarta.validation.constraints.NotNull;
import med.voll.api.domain.address.AddressDTO;

public record UpdateDoctorDTO(
        @NotNull
        Long id,
        String name,
        String cellphone,
        AddressDTO address) {
}
