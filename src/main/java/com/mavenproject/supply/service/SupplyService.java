package com.mavenproject.supply.service;

import com.mavenproject.supply.model.SupplyRecord;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SupplyService {
    public Optional<SupplyRecord> getSupplyRecord(Integer id) {
        return Optional.empty();
    }

    public Optional<SupplyRecord> purchaseProduct(Integer productId, Integer quantity) {
        return Optional.empty();
    }
}
