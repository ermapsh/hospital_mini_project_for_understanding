package com.ermapsh.hospital.profiles;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("prod")
public class Prod implements MethodDto{

    @Override
    public String getData() {
        return "prod data";
    }
}
