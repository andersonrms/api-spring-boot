package med.voll.api.domain.doctor;

import med.voll.api.domain.address.Address;
import med.voll.api.domain.enuns.Specialty;

public record DetailsDoctorDTO(
        Long id,
        String name,
        String email,
        String crm,
        String cellphone,
        Specialty specialty,
        Address address) {

    public DetailsDoctorDTO(Doctor doctor){
        this(doctor.getId(), doctor.getName(), doctor.getEmail(), doctor.getCrm(), doctor.getCellphone(), doctor.getSpecialty(), doctor.getAddress());
    }
}
