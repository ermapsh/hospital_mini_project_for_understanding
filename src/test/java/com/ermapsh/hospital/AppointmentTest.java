package com.ermapsh.hospital;

import com.ermapsh.hospital.entity.Appointment;
import com.ermapsh.hospital.service.AppointmentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest

public class AppointmentTest {

    @Autowired
    private AppointmentService appointmentService;

    @Test
    public void createAppointment(){
        Appointment appointment = Appointment.builder()
                .appointmentTime(LocalDateTime.of(2026, 6, 22, 11, 30))
                .reason("Fever + Cough + Pain")
                .status(Boolean.TRUE)
                .build(); // in transient state

        Appointment newAppointment =  appointmentService.createAppointment(appointment, 1L, 2L);
        System.out.println(newAppointment);
    }
}
