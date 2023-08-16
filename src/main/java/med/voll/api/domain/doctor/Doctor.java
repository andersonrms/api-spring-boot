package med.voll.api.domain.doctor;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.voll.api.domain.address.Address;
import med.voll.api.domain.enuns.Specialty;

@Table(name = "doctors")
@Entity(name = "Doctor")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Doctor {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String crm;
    private String cellphone;
    @Enumerated(EnumType.STRING)
    private Specialty specialty;

    @Embedded // Embedded Atribuites: dispensa necessidade de uma tabela separada
    private Address address;

    private Boolean active;

    public Doctor(DoctorDTO doctorDto) {
        this.active = true;
        this.name = doctorDto.name();
        this.email = doctorDto.email();
        this.crm = doctorDto.crm();
        this.cellphone = doctorDto.cellphone();
        this.specialty = doctorDto.specialty();
        this.address = new Address(doctorDto.address());
    }

    public void updateData(UpdateDoctorDTO updateDoctorDTO) {
        if(updateDoctorDTO.name() != null){
            this.name = updateDoctorDTO.name();
        }

        if(updateDoctorDTO.cellphone() != null){
            this.cellphone = updateDoctorDTO.cellphone();
        }

        if(updateDoctorDTO.address() != null){
            this.address.updateAddress(updateDoctorDTO.address());
        }
    }

    public void delete() {
        this.active = false;
    }
}
