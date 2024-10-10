package org.example.quickclothapp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.example.quickclothapp.exception.ClotheBankServiceException;
import org.example.quickclothapp.exception.DataServiceException;
import org.example.quickclothapp.model.TypeCampaign;
import org.example.quickclothapp.payload.request.CampaignRequest;
import org.example.quickclothapp.payload.request.ClotheBankRequest;
import org.example.quickclothapp.payload.request.DonationRequest;
import org.example.quickclothapp.payload.request.OrderRequest;
import org.example.quickclothapp.payload.response.CampaignResponse;
import org.example.quickclothapp.payload.response.DonationResponse;
import org.example.quickclothapp.payload.response.MessageResponse;
import org.example.quickclothapp.payload.response.OrderResponse;
import org.example.quickclothapp.service.intf.IClotheBankService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.UUID;

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
        } catch (DataServiceException | ClotheBankServiceException e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage(), null, null));
        }
    }

    @Operation(summary = "#TODO: 22 de septiembre -> Obtener campañas", description = "Obtiene todas las campañas dado el uuid del banco de ropa, los atributos discount y valueDiscount se retornan como nulos")
    @ApiResponse(responseCode = "200", description = "La lista de campañas", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = CampaignResponse.class))})
    @ApiResponse(responseCode = "400", description = "El valor mensaje retorna el mensaje de error", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))})
    @GetMapping("/campaign/get_all")
    public ResponseEntity<?> getAllCampaigns(@RequestParam UUID clotheBankUuid, @RequestParam(required = false) LocalDate startDate, @RequestParam(required = false) LocalDate endDate) {
        try {
            return ResponseEntity.ok(clotheBankService.findAllCampaignsByClotheBankUuid(clotheBankUuid, startDate, endDate));
        } catch (DataServiceException e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage(), null, null));
        }
    }

    @Operation(summary = "#TODO: 22 de septiembre -> Obtener todos los tipos de campañas", description = "Obtiene todos los tipos de campañas")
    @ApiResponse(responseCode = "200", description = "Lista de Tipos de campañas", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = TypeCampaign.class))})
    @ApiResponse(responseCode = "400", description = "El valor mensaje retorna el mensaje de error", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))})
    @GetMapping("/type_campaign/get_all")
    public ResponseEntity<?> getAllTypeCampaign() {
        try {
            return ResponseEntity.ok(clotheBankService.findAllTypeCampaign());
        } catch (DataServiceException e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage(), null, null));
        }
    }

    @Operation(summary = "#TODO: 26 de septiembre -> Crear donación", description = "Crea una donación para un banco de ropa dado el uuid del banco")
    @ApiResponse(responseCode = "200", description = "El valor uuid retorna el uuid de la donación creada", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))})
    @ApiResponse(responseCode = "400", description = "El valor mensaje retorna el mensaje de error", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))})
    @PostMapping("/donation/save")
    public ResponseEntity<?> saveDonation(@RequestBody DonationRequest donationRequest) {
        try {
            return ResponseEntity.ok(clotheBankService.saveDonation(donationRequest));
        } catch (DataServiceException e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage(), e.getStatusCode(), null));
        }
    }

    @Operation(summary = "#TODO: 22 de septiembre -> Obtener donaciones por banco de ropa", description = "Obtiene todas las donaciones dado el uuid del banco de ropa")
    @ApiResponse(responseCode = "200", description = "La lista de donaciones", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = DonationResponse.class))})
    @ApiResponse(responseCode = "400", description = "El valor mensaje retorna el mensaje de error", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))})
    @GetMapping("/donation/get_all/clothe_bank")
    public ResponseEntity<?> getAllDonations(@RequestParam UUID clotheBankUuid) {
        try {
            return ResponseEntity.ok(clotheBankService.findDonationByClotheBankUuid(clotheBankUuid));
        } catch (DataServiceException e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage(), e.getStatusCode(), null));
        }
    }

    @Operation(summary = "#TODO: 26 de septiembre -> Obtener todas las ordenes", description = "Obtiene todas las ordenes dado el uuid del banco de ropa, el uuid del estado de la orden y el uuid del ropero son opcionales ")
    @ApiResponse(responseCode = "200", description = "La lista de ordenes", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = OrderResponse.class))})
    @ApiResponse(responseCode = "400", description = "El valor mensaje retorna el mensaje de error", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))})
    @GetMapping("/orders/get_all")
    public ResponseEntity<?> getAllOrders(@RequestParam UUID clotheBankUuid, @RequestParam(required = false) UUID orderStateUuid, @RequestParam(required = false) UUID wardRobeUuid) {
        try {
            return ResponseEntity.ok(clotheBankService.findOrdersByClotheBankUuid(clotheBankUuid, orderStateUuid, wardRobeUuid));
        } catch (DataServiceException e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage(), e.getStatusCode(), null));
        }
    }

    @Operation(summary = "#TODO: 26 de septiembre -> Responder una orden y ponerla en estado ON_WAY", description = "El campo wardropeUuid no se debe enviar")
    @ApiResponse(responseCode = "200", description = "El valor uuid retorna el uuid de la orden actualizada", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))})
    @ApiResponse(responseCode = "400", description = "El valor mensaje retorna el mensaje de error", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))})
    @PostMapping("/order/response")
    public ResponseEntity<?> responseOrder(@RequestBody OrderRequest orderRequest, @RequestParam UUID orderUuid) {
        try {
            return ResponseEntity.ok(clotheBankService.responseOrder(orderRequest, orderUuid));
        } catch (DataServiceException e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage(), e.getStatusCode(), null));
        }
    }

}
