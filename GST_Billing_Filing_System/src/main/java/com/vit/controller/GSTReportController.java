package com.vit.controller;

import java.time.LocalDate;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vit.service.GSTReportService;

@RestController
@RequestMapping("/api/reports")
public class GSTReportController {

    private final GSTReportService service;

    public GSTReportController(GSTReportService service) {
        this.service = service;
    }

    @GetMapping("/gst")
    public ResponseEntity<String> generateGST(@RequestParam Long userId,
                                              @RequestParam String start,
                                              @RequestParam String end) {
        LocalDate startDate = LocalDate.parse(start);
        LocalDate endDate = LocalDate.parse(end);
        return ResponseEntity.ok(service.generateReport(userId, startDate, endDate));
    }
}