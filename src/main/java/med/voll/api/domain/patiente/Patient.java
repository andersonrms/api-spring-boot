package med.voll.api.domain.patiente;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.voll.api.domain.address.Address;

@Table(name = "patients")
@Entity(name = "Patient")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Patient {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String cellphone;
    private String cpf;
    private Boolean active;
    @Embedded // Embedded Atribuites: dispensa necessidade de uma tabela separada
    private Address address;

    public Patient(PatientDTO patientDTO){
        this.active = true;
        this.name = patientDTO.name();
        this.email = patientDTO.email();
        this.cellphone = patientDTO.cellphone();
        this.cpf = patientDTO.cpf();
        this.address = new Address(patientDTO.address());
    }

    public void update(PatientDTO patientDTO) {
        this.name = patientDTO.name();
        this.email = patientDTO.email();
        this.cellphone = patientDTO.cellphone();
        this.cpf = patientDTO.cpf();
        this.address = new Address(patientDTO.address());
    }

    public void delete() {
        this.active = false;
    }
}
