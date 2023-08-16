package med.voll.api.controller;

import med.voll.api.domain.appointment.AppointmentDTO;
import med.voll.api.domain.appointment.DetailsAppointmentDTO;
import med.voll.api.domain.enuns.Specialty;
import med.voll.api.service.AppointmentScheduleService;
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

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class AppointmentControllerTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private JacksonTester<AppointmentDTO> appointmentDTOJson;
    @Autowired
    private JacksonTester<DetailsAppointmentDTO> detailsAppointmentDTOJacksonTester;

    @MockBean
    private AppointmentScheduleService appointmentScheduleService;

    @Test
    @DisplayName("should return http 500 when send invalid information")
    @WithMockUser
    void appointment_scenario1() throws Exception {
        var response = mvc.perform(post("/agendamentos"))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());
    }

    @Test
    @DisplayName("should return http 200 when send invalid information")
    @WithMockUser
    void appointment_scenario2() throws Exception {
        var date = LocalDateTime.now().plusHours(1);
        var specialty = Specialty.CARDIOLOGIA;
        var detailsDTO = new DetailsAppointmentDTO(null, 2l, 5l, date);

        when(appointmentScheduleService.schedule(any()))
                .thenReturn(detailsDTO);

        var response = mvc.perform(
                post(
                        "/agendamentos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(appointmentDTOJson.write(
                                new AppointmentDTO(2l, 5l, date, specialty)
                        ).getJson())
                )
                .andReturn().getResponse();

        var expectedJson = detailsAppointmentDTOJacksonTester
                .write(detailsDTO).getJson();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(expectedJson);
    }
}