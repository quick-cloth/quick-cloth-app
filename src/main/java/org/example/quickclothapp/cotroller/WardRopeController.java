package org.example.quickclothapp.cotroller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.example.quickclothapp.exception.DataServiceException;
import org.example.quickclothapp.payload.request.SaleRequest;
import org.example.quickclothapp.payload.request.WardRopeRequest;
import org.example.quickclothapp.payload.response.MessageResponse;
import org.example.quickclothapp.service.intf.IWardRopeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/application/ward_rope")
public class WardRopeController {
    private final IWardRopeService wardRopeService;

    public WardRopeController(IWardRopeService wardRopeService) {
        this.wardRopeService = wardRopeService;
    }

    @Operation(summary = "Crear Ropero", description = "Crea un nuevo ropero dado el uuid del banco y la ciudad")
    @ApiResponse(responseCode = "200", description = "El valor uuid retorna el uuid del banco de ropa creado", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))})
    @ApiResponse(responseCode = "400", description = "El valor mensaje retorna el mensaje de error", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))})
    @PostMapping("/save")
    public ResponseEntity<?> saveWardRope(@RequestBody WardRopeRequest wardrope) {
        try {
            return ResponseEntity.ok(wardRopeService.saveWardRope(wardrope));
        } catch (DataServiceException e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage(), null, null));
        }
    }

    @PostMapping("/sale/save")
    public ResponseEntity<?> saveSale(@RequestBody SaleRequest sale) {
        try {
            return ResponseEntity.ok(wardRopeService.saveSale(sale));
        } catch (DataServiceException e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage(), null, null));
        }
    }
}
