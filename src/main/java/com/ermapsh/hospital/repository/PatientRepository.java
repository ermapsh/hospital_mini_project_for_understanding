package com.ermapsh.hospital.repository;

import com.ermapsh.hospital.dto.GenderCountEntity;
import com.ermapsh.hospital.entity.type.Gender;
import com.ermapsh.hospital.entity.Patient;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
    Patient findByName(String name);
    Patient findByNameAndDob(String name, LocalDateTime dob);
    List<Patient> findByDob(LocalDateTime dob);

    @Query("select p from Patient p where p.gender = ?1")
    List<Patient> findByGender(@Param("gender") Gender gender); // we are inserting as param here

    @Query("select p from Patient p where p.contactNumber = :contactNumber") // like this we can also use params
    Patient findbyPhoneNumber(@Param("contactNumber") String contactNumber);

    @Query("select new com.ermapsh.hospital.dto.GenderCountEntity(p.gender, count(p)) from Patient p group by p.gender")
//  List<Object[]> countEachGender();
    List<GenderCountEntity> countEachGender();

    @Query(value = "select * from patient", nativeQuery = true)
//    List<Patient> findAllPatient();
    Page<Patient> findAllPatient(Pageable pageable);

    @Transactional
    @Modifying
    @Query(value = "update Patient p set p.name = :name where p.id = :id")
    int updateNameWithId(@Param("id") Long id, @Param("name") String name);

}
