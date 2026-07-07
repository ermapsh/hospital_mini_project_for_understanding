package com.ermapsh.hospital.service.impl;

import com.ermapsh.hospital.service.DataService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Profile("dev")
public class DataServiceDevImpl implements DataService {
    @Override
    public String getData() {
        return "Dev data";
    }
}
