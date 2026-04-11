package com.ermapsh.hospital;

import com.ermapsh.hospital.dto.GenderCountEntity;
import com.ermapsh.hospital.entity.type.Gender;
import com.ermapsh.hospital.entity.Patient;
import com.ermapsh.hospital.repository.PatientRepository;
import com.ermapsh.hospital.service.PatientService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
public class PatientTest {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private PatientService patientService;

    @Test
    public void testPatientRepo() {
        List<Patient> patientList = patientRepository.findAll();
        System.out.println("==========PatientList==========" + patientList);
    }

    @Test
    public void getPatient() {
        Patient p = patientService.getPatient();
        System.out.println("==========Patient==========" + p);
    }

    @Test
    public void getPatientByName() {
            /*
            Patient p = patientRepository.findByNameAndDob(
                    "Mahesh Mestri",
                    LocalDateTime.of(1998, 5, 10, 0, 0)
            );
            */
        //        Patient p = patientRepository.findByName("Mahesh Mestri");
        List<Patient> p = patientRepository.findByDob(LocalDateTime.of(1998, 5, 10, 0, 0));
        System.out.println("Patients = " + p);
    }

    @Test
    public void getBySex() {
        List<Patient> p = patientRepository.findByGender(Gender.MALE);
        System.out.println(p);
    }

    @Test
    public void getByContact() {
        Patient p = patientRepository.findbyPhoneNumber("9876543210");
        System.out.println("Patinet=>" + p);
    }

    @Test
    public void getHowManyGenderBy() {
            /*
    //        instead of this we will use projection
            List<Object[]> list = patientRepository.countEachGender();
            for(Object[] obj: list){
                System.out.println(obj[0] + " - " + obj[1]);
            }
            */
        List<GenderCountEntity> list = patientRepository.countEachGender();
        System.out.println(list);
    }

    @Test
    public void getAllPatient() {
        Page<Patient> list = patientRepository.findAllPatient(PageRequest.of(0, 3, Sort.by("name")));
//        System.out.println("petientdata= " + list);
        for (Patient patient: list){
            System.out.println(patient);

        }
    }



    @Test
    public void updateNameWithId() {
        int p = patientRepository.updateNameWithId(1L, "Mahesh Mestri");
        System.out.println(p);
    }
}
