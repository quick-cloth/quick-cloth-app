package org.example.quickclothapp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.example.quickclothapp.exception.DataServiceException;
import org.example.quickclothapp.model.Clothe;
import org.example.quickclothapp.model.TypeClothe;
import org.example.quickclothapp.model.TypeGender;
import org.example.quickclothapp.model.TypeStage;
import org.example.quickclothapp.payload.response.MessageResponse;
import org.example.quickclothapp.service.intf.IClotheService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/application/clothe")
public class ClotheController {
    private final IClotheService clotheService;

    public ClotheController(IClotheService clotheService) {
        this.clotheService = clotheService;
    }

    @Operation(summary = "#TODO: 26 de septiembre -> Obtener una prenda por todos los tipos")
    @ApiResponse(responseCode = "200", description = "La prenda", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Clothe.class))})
    @ApiResponse(responseCode = "400", description = "El valor mensaje retorna el mensaje de error", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))})
    @GetMapping("/get")
    public ResponseEntity<?> getClotheByAllTypes(@RequestParam UUID typeClotheUuid, @RequestParam UUID typeGenderUuid, @RequestParam UUID typeStageUuid) {
        try {
            return ResponseEntity.ok(clotheService.findClotheByAllTypes(typeClotheUuid, typeGenderUuid, typeStageUuid));
        } catch (DataServiceException e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage(), e.getStatusCode(), null));
        }

    }

    @Operation(summary = "#TODO: 22 de septiembre -> Obtener todos los tipos de etapas")
    @ApiResponse(responseCode = "200", description = "La lista de tipos de etapas", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = TypeStage.class))})
    @ApiResponse(responseCode = "400", description = "El valor mensaje retorna el mensaje de error", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))})
    @GetMapping("/type_stage/get_all")
    public ResponseEntity<?> getAllTypeStage() {
        try {
            return ResponseEntity.ok(clotheService.findAllTypeStage());
        } catch (DataServiceException e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage(), e.getStatusCode(), null));
        }
    }

    @Operation(summary = "#TODO: 22 de septiembre -> Obtener todos los tipos de genero")
    @ApiResponse(responseCode = "200", description = "La lista de tipos de genero", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = TypeGender.class))})
    @ApiResponse(responseCode = "400", description = "El valor mensaje retorna el mensaje de error", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))})
    @GetMapping("/type_gender/get_all")
    public ResponseEntity<?> getAllTypeGender() {
        try {
            return ResponseEntity.ok(clotheService.findAllTypeGender());
        } catch (DataServiceException e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage(), e.getStatusCode(), null));
        }
    }

    @Operation(summary = "#TODO: 22 de septiembre -> Obtener todos los tipos de ropa")
    @ApiResponse(responseCode = "200", description = "La lista de tipos de ropa", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = TypeClothe.class))})
    @ApiResponse(responseCode = "400", description = "El valor mensaje retorna el mensaje de error", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))})
    @GetMapping("/type_clothe/get_all")
    public ResponseEntity<?> getAllTypeClothe() {
        try {
            return ResponseEntity.ok(clotheService.findAllTypeClothe());
        } catch (DataServiceException e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage(), e.getStatusCode(), null));
        }
    }
}
