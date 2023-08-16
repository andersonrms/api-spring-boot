package med.voll.api.controller;

import med.voll.api.domain.address.Address;
import med.voll.api.domain.address.AddressDTO;
import med.voll.api.domain.doctor.DetailsDoctorDTO;
import med.voll.api.domain.doctor.DoctorDTO;
import med.voll.api.domain.doctor.DoctorRepository;
import med.voll.api.domain.enuns.Specialty;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class DoctorControllerTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private JacksonTester<DoctorDTO> doctorDTOJson;
    @MockBean
    private DoctorRepository repository;

    @Test
    @DisplayName("Should return http 201 when send invalid information")
    @WithMockUser
    void create_doctor_scenario1() throws Exception {
        var specialty = Specialty.CARDIOLOGIA;
        var doctor = new DoctorDTO(
                "Alexsandra Krauss",
                "krauss@gmail.com",
                "555442",
                "21995277630",
                specialty,
                addressDTO());

        when(repository.save(any())).thenReturn(null);

        var response = mvc.perform(
                post("/medicos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(doctorDTOJson.write(
                                doctor
                        ).getJson())
        ).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
    }

    @Test
    @DisplayName("should return http 500 when send invalid information")
    @WithMockUser
    void create_doctor_scenario2() throws Exception {
        var response = mvc.perform(post("/medicos"))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());
    }

    private AddressDTO addressDTO(){
        return new AddressDTO(
                "Rua Alquindar",
                "Brás de Pina",
                "21011110",
                "RJ",
                "Rio de Janeiro",
                "769",
                ""
        );
    }
}