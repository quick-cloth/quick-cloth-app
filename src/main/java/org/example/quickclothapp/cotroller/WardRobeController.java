package org.example.quickclothapp.cotroller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.example.quickclothapp.exception.DataServiceException;
import org.example.quickclothapp.exception.WardRopeServiceExpetion;
import org.example.quickclothapp.model.OrderState;
import org.example.quickclothapp.payload.request.OrderRequest;
import org.example.quickclothapp.payload.request.SaleRequest;
import org.example.quickclothapp.payload.request.WardRobeRequest;
import org.example.quickclothapp.payload.response.*;
import org.example.quickclothapp.service.intf.IWardRobeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/application/ward_robe")
public class WardRobeController {
    private final IWardRobeService wardRopeService;

    public WardRobeController(IWardRobeService wardRopeService) {
        this.wardRopeService = wardRopeService;
    }

    @Operation(summary = "Crear Ropero", description = "Crea un nuevo ropero dado el uuid del banco y la ciudad")
    @ApiResponse(responseCode = "200", description = "El valor uuid retorna el uuid del banco de ropa creado", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))})
    @ApiResponse(responseCode = "400", description = "El valor mensaje retorna el mensaje de error", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))})
    @PostMapping("/save")
    public ResponseEntity<?> saveWardRope(@RequestBody WardRobeRequest wardrope) {
        try {
            return ResponseEntity.ok(wardRopeService.saveWardRope(wardrope));
        } catch (DataServiceException e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage(), null, null));
        }
    }

    @Operation(summary = "#TODO: 22 de septiembre -> Obtener Roperos", description = "Obtiene todos los roperos dado el uuid del banco de ropa")
    @ApiResponse(responseCode = "200", description = "La lista de roperos", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = WardRobeResponse.class))})
    @ApiResponse(responseCode = "400", description = "El valor mensaje retorna el mensaje de error", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))})
    @GetMapping("/get_all/clothe_bank")
    public ResponseEntity<?> getAllWardRobes(@RequestParam UUID clotheBankUuid) {
        try {
            return ResponseEntity.ok(wardRopeService.finAllWardRobeByClotheBankUuid(clotheBankUuid));
        } catch (DataServiceException e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage(), null, null));
        }
    }

    @Operation(summary = "#TODO: 22 de septiembre -> Actualizar Ropero", description = "Actualiza un ropero dada la entidad del ropero")
    @ApiResponse(responseCode = "200", description = "El valor uuid retorna el uuid del ropero actualizado", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))})
    @ApiResponse(responseCode = "400", description = "El valor mensaje retorna el mensaje de error", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))})
    @PutMapping("/update")
    public ResponseEntity<?> updateWardRope(@RequestBody WardRobeRequest wardrobe) {
        try {
            return ResponseEntity.ok(wardRopeService.updateWardRope(wardrobe));
        } catch (DataServiceException e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage(), null, null));
        }
    }

    @Operation(summary = "#TODO: 22 de septiembre -> Obtener Ropero", description = "Obtiene un ropero dado el uuid del ropero")
    @ApiResponse(responseCode = "200", description = "Entidad del ropero", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = WardRobeResponse.class))})
    @ApiResponse(responseCode = "400", description = "El valor mensaje retorna el mensaje de error", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))})
    @GetMapping("/get")
    public ResponseEntity<?> getWardRope(@RequestParam UUID uuid) {
        try {
            return ResponseEntity.ok(wardRopeService.findWardRopeByUuid(uuid));
        } catch (DataServiceException e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage(), null, null));
        }
    }

    @Operation(summary = "#TODO 23 de septiembre -> Obtener Inventario de un ropero", description = "Obtiene el inventario de un ropero dado el uuid del ropero")
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

    @Operation(summary = "#TODO 22 de septiembre ->  Crear Venta", description = """
            Crea una venta para un ropero dado el uuid del ropero y la lista de prendas\s
            Para tener en cuenta: 1. si el valor de payPoints es true, se pagara con puntos""")
    @ApiResponse(responseCode = "200", description = "El valor uuid retorna el uuid de la venta creada", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))})
    @ApiResponse(responseCode = "400", description = "El valor mensaje retorna el mensaje de error", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))})
    @PostMapping("/sale/save")
    public ResponseEntity<?> saveSale(@RequestBody SaleRequest sale, @RequestParam(required = false) boolean payPoints) {
        try {
            return ResponseEntity.ok(wardRopeService.saveSale(sale, payPoints));
        } catch (DataServiceException | WardRopeServiceExpetion e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage(), null, null));
        }
    }

    @Operation(summary = "#TODO 22 de septiembre -> Validar venta antes de crear venta", description = "Valida la venta antes de emitirse, retornando el valor de la venta y los descuentos por campaña " +
            "Para tener en cuenta: 1. si el valor de payPoints es true, se pagara con puntos ")
    @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = SaleResponse.class))})
    @ApiResponse(responseCode = "400", description = "El valor mensaje retorna el mensaje de error", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))})
    @PostMapping("/sale/check_sale/value")
    public ResponseEntity<?> checkValueSale(@RequestBody SaleRequest sale, @RequestParam(required = false) boolean payPoints) {
        try {
            return ResponseEntity.ok(wardRopeService.checkValueSale(sale, payPoints));
        } catch (DataServiceException | WardRopeServiceExpetion e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage(), null, null));
        }
    }

    @Operation(summary = "#TODO: 23 de septiembre -> Obtener Ventas", description = "Obtiene todas las ventas dado el uuid del ropero")
    @ApiResponse(responseCode = "200", description = "La lista de ventas, el atributo saleList siembre sera nulo", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = SaleWardRobeResponse.class))})
    @ApiResponse(responseCode = "400", description = "El valor mensaje retorna el mensaje de error", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))})
    @GetMapping("/sale/get_all")
    public ResponseEntity<?> getAllSales(@RequestParam UUID wardRopeUuid) {
        try {
            return ResponseEntity.ok(wardRopeService.findSalesByWardRopeUuid(wardRopeUuid));
        } catch (DataServiceException e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage(), null, null));
        }
    }

    @Operation(summary = "#TODO: 23 de septiembre -> Obtener Venta", description = "Obtiene una venta dado el uuid de la venta")
    @ApiResponse(responseCode = "200", description = "Entidad de la venta, el atributo", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = SaleWardRobeResponse.class))})
    @ApiResponse(responseCode = "400", description = "El valor mensaje retorna el mensaje de error", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))})
    @GetMapping("/sale/get")
    public ResponseEntity<?> getSale(@RequestParam UUID saleuuid) {
        try {
            return ResponseEntity.ok(wardRopeService.findSaleByUuid(saleuuid));
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

    @Operation(summary = "#TODO: 22 de septiembre -> Obtener tipos de estado de orden")
    @ApiResponse(responseCode = "200", description = "La lista de tipos de estado de orden", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = OrderState.class))})
    @ApiResponse(responseCode = "400", description = "El valor mensaje retorna el mensaje de error", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))})
    @GetMapping("/order_state/get_all")
    public ResponseEntity<?> getAllOrderStates() {
        try {
            return ResponseEntity.ok(wardRopeService.getAllOrderStates());
        } catch (DataServiceException e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage(), null, null));
        }
    }

}
