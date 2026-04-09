package com.ermapsh.hospital.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

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

    @ToString.Exclude
    private LocalDateTime dob;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(unique = true)
    private String contactNumber;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
