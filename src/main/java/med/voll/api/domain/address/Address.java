package med.voll.api.domain.address;


import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    private String street;
    private String neighborhood;
    private String cep;
    private String uf;
    private String number;
    private String complement;
    private String city;

    public Address(AddressDTO addressDTO) {
        this.street = addressDTO.street();
        this.neighborhood = addressDTO.neighborhood();
        this.cep = addressDTO.cep();
        this.uf = addressDTO.uf();
        this.number = addressDTO.number();
        this.complement = addressDTO.complement();
        this.city = addressDTO.city();
    }

    public void updateAddress(AddressDTO address) {
        this.street = address.street() != null ? address.street() : this.street;
        this.neighborhood = address.neighborhood() != null ? address.neighborhood() : this.neighborhood;
        this.cep = address.cep() != null ? address.cep() : this.cep;
        this.uf = address.uf() != null ? address.uf() : this.uf;
        this.number = address.number() != null ? address.number() : this.number;
        this.city = address.city() != null ? address.city() : this.city;
        this.complement = address.complement() != null ? address.complement() : this.complement;
    }
}
