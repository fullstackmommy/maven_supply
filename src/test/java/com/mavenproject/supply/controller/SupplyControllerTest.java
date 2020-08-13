package com.mavenproject.supply.controller;

import com.mavenproject.supply.model.SupplyRecord;
import com.mavenproject.supply.service.SupplyService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class SupplyControllerTest {

    @MockBean
    private SupplyService supplyService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("GET - /supply/{id} - Successful")
    public void testGetSupplyByIdSuccessfully() throws Exception {
        SupplyRecord supplyRecord = new SupplyRecord(1, "New Product", "Utilities", 100);

        doReturn(Optional.of(supplyRecord)).when(supplyService).getSupplyRecord(1);

        mockMvc.perform(get("/supply/{id}", 1))

                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))

                .andExpect(header().string(HttpHeaders.LOCATION, "/supply/1"))

                .andExpect(jsonPath("$.productId", is(1)))
                .andExpect(jsonPath("$.productName", is("New Product")))
                .andExpect(jsonPath("$.productCategory", is("Utilities")))
                .andExpect(jsonPath("$.quantity", is(100)));
    }

    @Test
    @DisplayName("GET - /supply/{id} - Failure")
    public void testGetSupplyByIdFails() throws Exception {
        doReturn(Optional.empty()).when(supplyService).getSupplyRecord(1);

        mockMvc.perform(get("/supply/{id}", 1))

                .andExpect(status().isNotFound());
    }
}