package org.example.quickclothapp.cotroller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.example.quickclothapp.exception.DataServiceException;
import org.example.quickclothapp.payload.request.OrderRequest;
import org.example.quickclothapp.payload.request.SaleRequest;
import org.example.quickclothapp.payload.request.WardRopeRequest;
import org.example.quickclothapp.payload.response.InventoryResponse;
import org.example.quickclothapp.payload.response.MessageResponse;
import org.example.quickclothapp.payload.response.SaleResponse;
import org.example.quickclothapp.service.intf.IWardRopeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

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

    @Operation(summary = "Obtener Inventario de un ropero", description = "Obtiene el inventario de un ropero dado el uuid del ropero")
    @ApiResponse(responseCode = "200", description = "La lista del invetario del ropero", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = InventoryResponse.class))})
    @ApiResponse(responseCode = "400", description = "El valor mensaje retorna el mensaje de error", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))})
    @GetMapping("/inventory/get_all")
    public ResponseEntity<?> getAllInventories(@RequestParam UUID wardRopeUuid) {
        try {
            return ResponseEntity.ok(wardRopeService.findInventoriesByWardRopeUuid(wardRopeUuid));
        } catch (DataServiceException e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage(), null, null));
        }
    }

    @Operation(summary = "Obtener una prenda de un roper", description = "Se obtiene una prenda de un ropero dado el uuid del ropero y el uuid de la prenda")
    @ApiResponse(responseCode = "200", description = "Entidad del ropero", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = InventoryResponse.class))})
    @ApiResponse(responseCode = "400", description = "El valor mensaje retorna el mensaje de error", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))})
    @GetMapping("/inventory/clothe")
    public ResponseEntity<?> getInventory(@RequestParam UUID clotheUuid, @RequestParam UUID wardRopeUuid) {
        try {
            return ResponseEntity.ok(wardRopeService.findInventoryByClotheUuidAndWardRopeUuid(clotheUuid, wardRopeUuid));
        } catch (DataServiceException e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage(), null, null));
        }
    }

    @Operation(summary = "Crear Venta", description = "Crea una venta para un ropero dado el uuid del roper y la lista de prendas")
    @ApiResponse(responseCode = "200", description = "El valor uuid retorna el uuid de la venta creada", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))})
    @ApiResponse(responseCode = "400", description = "El valor mensaje retorna el mensaje de error", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))})
    @PostMapping("/sale/save")
    public ResponseEntity<?> saveSale(@RequestBody SaleRequest sale) {
        try {
            return ResponseEntity.ok(wardRopeService.saveSale(sale));
        } catch (DataServiceException e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage(), null, null));
        }
    }

    @Operation(summary = "Validar venta", description = "Valida la venta antes de emitirse, retornando el valor de la venta y los descuentos por campaña")
    @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = SaleResponse.class))})
    @ApiResponse(responseCode = "400", description = "El valor mensaje retorna el mensaje de error", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))})
    @PostMapping("/sale/check_sale/value")
    public ResponseEntity<?> checkValueSale(@RequestBody SaleRequest sale) {
        try {
            return ResponseEntity.ok(wardRopeService.checkValueSale(sale));
        } catch (DataServiceException e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage(), null, null));
        }
    }

    @PostMapping("/order/create")
    public ResponseEntity<?> createOrder(@RequestBody OrderRequest orderRequest) {
        try {
            return ResponseEntity.ok(wardRopeService.createOrder(orderRequest));
        } catch (DataServiceException e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage(), null, null));
        }
    }
}
