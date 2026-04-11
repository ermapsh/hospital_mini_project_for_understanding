package com.ermapsh.hospital.dto;

import com.ermapsh.hospital.entity.type.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GenderCountEntity  {
    private Gender gender;
    private Long count;
}
