package com.ermapsh.hospital.profiles;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("default")
public class Defualt implements MethodDto {

    @Override
    public String getData() {
        return "default data";
    }
}
