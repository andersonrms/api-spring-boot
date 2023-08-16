package med.voll.api.domain.patiente;

import med.voll.api.domain.address.Address;

public record DetailsPatientDTO(
        Long id,
        String name,
        String email,
        String cellphone,
        String cpf,
        Address address
) {
    public DetailsPatientDTO(Patient patient){
        this(patient.getId(), patient.getName(), patient.getEmail(),patient.getCellphone(),patient.getCpf(), patient.getAddress());
    }
}
