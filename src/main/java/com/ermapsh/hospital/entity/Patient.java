package com.ermapsh.hospital.entity;

import com.ermapsh.hospital.entity.type.BloodType;
import com.ermapsh.hospital.entity.type.Gender;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@ToString
@Getter
@Setter
@Table(
//        name = "PatientTable", // its going to in snake case like patient_table and it will create new table,
        uniqueConstraints = {
//                @UniqueConstraint(name = "unique_contact_number", columnNames = {"contactNumber"}),
                @UniqueConstraint(name="unique_patient_name_dob", columnNames = {"name", "dob"}),
        },
        indexes = {
                @Index(
                        name="idx_patient_birth_date", columnList = "dob"
                )
        }// to make query we implement such type column as indexes, its just like book introduction index page where we can sort faster
)
public class Patient { // if we Patient change this to PatientTable, then its going to in snake case like patient_table and it will create new table

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 40)
    private String name;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @ToString.Exclude
    private LocalDateTime dob;

    @Email
    private String email;
    @Column(unique = true)

    private String contactNumber;
    @Enumerated(EnumType.STRING)

    private BloodType bloodGroup;

    @OneToOne
    @JoinColumn(name="patient_insurance_id")
    private Insurance insurance; // owning side

    @OneToMany(mappedBy = "patient")
    private List<Appointment> appointments;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
