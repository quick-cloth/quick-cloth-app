package org.example.quickclothapp.controller;

import org.example.quickclothapp.exception.DataServiceException;
import org.example.quickclothapp.payload.response.MessageResponse;
import org.example.quickclothapp.service.intf.ICampaignsService;
import org.example.quickclothapp.service.intf.IClotheBankService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/application/campaigns")
public class CampaignsController {

    private final ICampaignsService campaignsService;

    public CampaignsController(ICampaignsService campaignsService) {
        this.campaignsService = campaignsService;
    }


    @GetMapping("/get_active")
    public ResponseEntity<?> getActiveCampaigns() {
        try {
        return ResponseEntity.ok(
                campaignsService.getActiveCampaigns()
        );
        } catch (DataServiceException e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage(), null, null));
        }
    }
}
