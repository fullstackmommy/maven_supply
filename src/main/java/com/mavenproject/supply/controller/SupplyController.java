package com.mavenproject.supply.controller;

import com.mavenproject.supply.service.SupplyService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
public class SupplyController {

    private static final Logger LOGGER = LogManager.getLogger(SupplyController.class);

    @Autowired
    private SupplyService supplyService;

    @GetMapping("/supply/{id}")
    public ResponseEntity<?> getSupplyRecord(@PathVariable Integer id) {
        LOGGER.info("Finding supply record for product with id:" + id);
        return supplyService.getSupplyRecord(id)
                .map(supplyRecord -> {
                    try {
                        return ResponseEntity
                                .ok()
                                .location(new URI("/supply/" + supplyRecord.getProductId()))
                                .body(supplyRecord);
                    } catch (URISyntaxException e) {
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
                    }
                })
                .orElse(ResponseEntity.notFound().build());
    }

}
