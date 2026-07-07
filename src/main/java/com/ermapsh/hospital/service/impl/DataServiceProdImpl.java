package com.ermapsh.hospital.service.impl;

import com.ermapsh.hospital.service.DataService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Profile("prod")
public class DataServiceProdImpl implements DataService {
    @Override
    public String getData() {
        return "Prod Data";
    }
}
