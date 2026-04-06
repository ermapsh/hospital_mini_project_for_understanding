package com.ermapsh.hospital;

import com.ermapsh.hospital.entity.Patient;
import com.ermapsh.hospital.repository.PatientRepository;
import com.ermapsh.hospital.service.PatientService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class PatientTest {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private PatientService patientService;

    @Test
    public void testPatientRepo(){
       List<Patient> patientList =  patientRepository.findAll();
       System.out.println("==========PatientList==========" + patientList);
    }

    @Test
    public void getPatient(){
        Patient p = patientService.getPatient();
        System.out.println("==========Patient==========" +  p);
    }

}
