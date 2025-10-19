package com.charan.claimsprocessingsystem;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.charan.claimsprocessingsystem.controller.ClaimController;
import com.charan.claimsprocessingsystem.entity.Claim;
import com.charan.claimsprocessingsystem.service.ClaimService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(ClaimController.class) // Why? Test controller layer – mocks service.
public class ClaimControllerTest {
    @Autowired
    private MockMvc mockMvc; // Why? Simulates HTTP requests – test APIs.

    @MockBean
    private ClaimService service; // Why? Mock service – control output.

    @Autowired
    private ObjectMapper objectMapper; // Why? JSON handling – for body.

    @Test
    public void testSubmitClaim() throws Exception { // Why? Test POST /submit.
        Claim claim = new Claim();
        claim.setClaimType("health");
        claim.setAmount(5000.0);
        when(service.submitClaim(any(Claim.class))).thenReturn(claim); // Mock service.

        mockMvc.perform(post("/api/claims/submit")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(claim))) // Why? Send JSON body.
                .andExpect(status().isOk()) // Why? Expect 200.
                .andExpect(jsonPath("$.claimType").value("health")); // Why? Check response.
    }

    @Test
    public void testValidateClaim() throws Exception { // Why? Test PUT /validate/1.
        Claim claim = new Claim();
        claim.setStatus("VALIDATED");
        when(service.validateClaim(1L)).thenReturn(claim);

        mockMvc.perform(put("/api/claims/validate/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("VALIDATED"));
    }
}