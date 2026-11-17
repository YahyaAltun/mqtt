package com.example.demo.controller;

import com.example.demo.repository.VisitorRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/visitors")
public class StoreController {

    private final VisitorRepository visitorRepository;

    public StoreController(VisitorRepository visitorRepository) {
        this.visitorRepository = visitorRepository;
    }

    @GetMapping("/total/{storeId}")
    public ResponseEntity<Long> getTotalVisitors(@PathVariable Long storeId) {
        Long total = visitorRepository.getTotalVisitorsByStore(storeId);
        return ResponseEntity.ok(total != null ? total : 0L);
    }
}
