package com.ermapsh.hospital.dto;

import lombok.Data;
import lombok.Setter;

@Data
public class RestDataDTO {
    private Number id;
    private  String title;
    private  String body;
    private  Number userId;
}
