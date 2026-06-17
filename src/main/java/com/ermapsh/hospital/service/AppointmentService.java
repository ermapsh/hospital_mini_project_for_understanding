package com.ermapsh.hospital.service;

import com.ermapsh.hospital.entity.Appointment;
import com.ermapsh.hospital.entity.Doctor;
import com.ermapsh.hospital.entity.Patient;
import com.ermapsh.hospital.repository.AppointmentRepository;
import com.ermapsh.hospital.repository.DoctorRepository;
import com.ermapsh.hospital.repository.PatientRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;

    @Transactional
    public Appointment createAppointment(Appointment appointment, Long doctorId, Long patientId){
        Doctor doctor = doctorRepository.findById(doctorId).orElseThrow();
        Patient patient = patientRepository.findById(patientId).orElseThrow();


        appointment.setPatient(patient);
        appointment.setDoctor(doctor); // now we are in transient state and we have to go to persistence state
        appointmentRepository.save(appointment);
        return appointment;
    }
}
