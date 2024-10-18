package org.example.quickclothapp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.example.quickclothapp.exception.DataServiceException;
import org.example.quickclothapp.model.Campaign;
import org.example.quickclothapp.payload.response.MessageResponse;
import org.example.quickclothapp.service.intf.ICampaignsService;
import org.example.quickclothapp.service.intf.IClotheBankService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/application/campaigns")
public class CampaignsController {

    private final ICampaignsService campaignsService;

    public CampaignsController(ICampaignsService campaignsService) {
        this.campaignsService = campaignsService;
    }


    @Operation(summary = "Obtiene toda las campañas activas en el momento", description = "Obtiene todas las campañas activas en el momento")
    @ApiResponse(responseCode = "200", description = "Lista de todas las campañas activas en el momento", content = {@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Campaign.class)))})
    @ApiResponse(responseCode = "400", description = "El valor mensaje retorna el mensaje de error", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))})
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

    @Operation(summary = "Obtiene toda las campañas activas de posible interes para el usuario", description = "Obtiene todas las campañas activas de posible interes para el usuario")
    @ApiResponse(responseCode = "200", description = "Lista de todas las campañas activas en el momento", content = {@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Campaign.class)))})
    @ApiResponse(responseCode = "400", description = "El valor mensaje retorna el mensaje de error", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))})
    @GetMapping("/get_for_user")
    public ResponseEntity<?> getCampaignsForUser(@RequestParam UUID userUuid) {
        try {
            return ResponseEntity.ok(
                    campaignsService.getCampaignsForUser(userUuid)
            );
        } catch (DataServiceException e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage(), null, null));
        }
    }
}
