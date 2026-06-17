package com.ermapsh.hospital.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.WeakHashMap;

@RestController
public class PatientController {

    @GetMapping("/")
    public ResponseEntity<?> get() {
        return ResponseEntity.ok("Hello World");
    }
}
