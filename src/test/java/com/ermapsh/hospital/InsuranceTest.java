package com.ermapsh.hospital;

import com.ermapsh.hospital.entity.Insurance;
import com.ermapsh.hospital.entity.Patient;
import com.ermapsh.hospital.service.InsuranceService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

@SpringBootTest
public class InsuranceTest {

    @Autowired
    private InsuranceService insuranceService;

    @Test
    public void testAssignInsuranceToPatient(){
        Insurance insurance = Insurance.builder().
                provider("HDFC agro").
                policyNumber("HDFC 1234").
                validUntil(LocalDate.of(2030, 1,1)).
                build();
        var updatedInsurance =  insuranceService.assignInsuranceToPatient(insurance, 2L);
        System.out.println(updatedInsurance);
    }


    @Test
    public void deletePatientWithInsurance(){
        insuranceService.deletePatientWithInsurance(2L);
    }


    @Test
    public void updateTheInsurance(){
       Patient patient =  insuranceService.updateInsuranceOfPatient(3L);
       System.out.println(patient);
    }
}
