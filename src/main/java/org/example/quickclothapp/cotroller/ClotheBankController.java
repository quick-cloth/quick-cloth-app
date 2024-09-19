package org.example.quickclothapp.cotroller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.example.quickclothapp.exception.DataServiceException;
import org.example.quickclothapp.payload.request.CampaignRequest;
import org.example.quickclothapp.payload.request.ClotheBankRequest;
import org.example.quickclothapp.payload.response.MessageResponse;
import org.example.quickclothapp.service.intf.IClotheBankService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/application/clothe_bank")
public class ClotheBankController {
    private final IClotheBankService clotheBankService;

    public ClotheBankController(IClotheBankService clotheBankService) {
        this.clotheBankService = clotheBankService;
    }

    @Operation(summary = "Crear banco de ropa", description = "Crea un nuevo banco de ropa dado el uuid de la fundacion y la ciudad")
    @ApiResponse(responseCode = "200", description = "El valor uuid retorna el uuid del banco de ropa creado", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))})
    @ApiResponse(responseCode = "400", description = "El valor mensaje retorna el mensaje de error", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))})
    @PostMapping("/save")
    public ResponseEntity<?> saveClotheBank(@RequestBody ClotheBankRequest clotheBank) {
        try {
            return ResponseEntity.ok(clotheBankService.saveClotheBank(clotheBank));
        } catch (DataServiceException e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage(), null, null));
        }
    }

    @Operation(summary = "Crear campaña", description = "Crea una campaña para un banco de ropa dado el uuid del banco y el tipo de campaña")
    @ApiResponse(responseCode = "200", description = "El valor uuid retorna el uuid de la campaña creada", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))})
    @ApiResponse(responseCode = "400", description = "El valor mensaje retorna el mensaje de error", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))})
    @PostMapping("/campaign/save")
    public ResponseEntity<?> saveCampaign(@RequestBody CampaignRequest campaign) {
        try {
            return ResponseEntity.ok(clotheBankService.saveCampaign(campaign));
        } catch (DataServiceException e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage(), null, null));
        }
    }
}
