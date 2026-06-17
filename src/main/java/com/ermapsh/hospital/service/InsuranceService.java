package com.ermapsh.hospital.service;

import com.ermapsh.hospital.entity.Insurance;
import com.ermapsh.hospital.entity.Patient;
import com.ermapsh.hospital.repository.InsuranceRepository;
import com.ermapsh.hospital.repository.PatientRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InsuranceService {

    private final InsuranceRepository insuranceRepository;
    private final PatientRepository patientRepository;

    @Transactional
    public Insurance assignInsuranceToPatient(Insurance insurance, Long patientId){
        Patient patient = patientRepository.findById(patientId).orElseThrow();

        patient.setInsurance(insurance); // dirty the patient
        insurance.setPatient(patient);// optional but its showing bidirectional
        return insurance;
    }

    @Transactional
    public void deletePatientWithInsurance(Long patientId){
        Patient patient = patientRepository.findById(patientId).orElseThrow();
        patientRepository.deleteById(patientId);
    }


    @Transactional
    public Patient updateInsuranceOfPatient(Long patientId){
        Patient patient = patientRepository.findById(patientId).orElseThrow();
        patient.setInsurance(null);
        return patient;
    }
}
