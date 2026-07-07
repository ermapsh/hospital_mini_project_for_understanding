package com.ermapsh.hospital.profiles;

import com.ermapsh.hospital.service.DataService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("dev")
public class Dev implements MethodDto {


    @Override
    public String getData() {
        return "dev data";
    }
}
