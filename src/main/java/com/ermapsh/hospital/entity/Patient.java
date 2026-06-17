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
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    @OneToOne(
//            cascade = {CascadeType.PERSIST, CascadeType.MERGE}
            cascade =  CascadeType.ALL
    ) // we need to take care of child as well along with parent, in this patient is parent and insurance is child
    @JoinColumn(name="patient_insurance_id", nullable = true)
    private Insurance insurance; // owning side

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    private List<Appointment> appointments = new ArrayList<>(); // inverse side

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
