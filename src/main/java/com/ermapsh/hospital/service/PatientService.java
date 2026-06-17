package com.ermapsh.hospital.service;

import com.ermapsh.hospital.entity.Patient;
import com.ermapsh.hospital.repository.PatientRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PatientService {
    private final PatientRepository patientRepository;

    @Transactional
    public Patient getPatient(){
        Patient p1 = patientRepository.findById(1L).orElseThrow();
        Patient p2 = patientRepository.findById(1L).orElseThrow();
        System.out.println("referring to same object=>" + (p1 == p2));

        p1.setName("ErMapsh");
//        patientRepository.save(p1); // If I forgot to save, it will do dirty checking & save in database

        return p1;
    }

    @Transactional
    public void deletePatient(Long patientId){
        Patient p1 = patientRepository.findById(patientId).orElseThrow();

        patientRepository.deleteById(patientId);
    }

}
