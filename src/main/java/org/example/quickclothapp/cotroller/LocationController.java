package org.example.quickclothapp.cotroller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.example.quickclothapp.exception.DataServiceException;
import org.example.quickclothapp.model.Department;
import org.example.quickclothapp.model.TypeMeetUs;
import org.example.quickclothapp.payload.response.CityResponse;
import org.example.quickclothapp.payload.response.MessageResponse;
import org.example.quickclothapp.service.intf.ILocationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/application/location")
public class LocationController {
    private final ILocationService locationService;

    public LocationController(ILocationService locationService) {
        this.locationService = locationService;
    }


    @Operation(summary = " #TODO: 22 de septiembre Obtener todos los departamentos")
    @ApiResponse(responseCode = "200", description = "Lista de departamentos", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Department.class))})
    @ApiResponse(responseCode = "400", description = "El valor mensaje retorna el mensaje de error", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))})
    @GetMapping("/department/get_all")
    public ResponseEntity<?> getAllDepartments() {
        try {
            return ResponseEntity.ok(locationService.findAllDepartments());
        } catch (DataServiceException e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage(), e.getStatusCode(), null));
        }
    }

    @Operation(summary = " #TODO: 22 de septiembre  Obtener todas las ciudades dado un departamento")
    @ApiResponse(responseCode = "200", description = "Lista de ciudades", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = CityResponse.class))})
    @ApiResponse(responseCode = "400", description = "El valor mensaje retorna el mensaje de error", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))})
    @GetMapping("city/get_all/by_department")
    public ResponseEntity<?> getAllCitiesByDepartment(UUID departmentUuid) {
        try {
            return ResponseEntity.ok(locationService.findAllCitiesByDepartment(departmentUuid));
        } catch (DataServiceException e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage(), e.getStatusCode(), null));
        }
    }
}
