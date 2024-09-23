package org.example.quickclothapp.cotroller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.example.quickclothapp.exception.DataServiceException;
import org.example.quickclothapp.payload.request.UserRequest;
import org.example.quickclothapp.payload.response.MessageResponse;
import org.example.quickclothapp.payload.response.UserResponse;
import org.example.quickclothapp.service.intf.IUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/application/user")
public class UserController {
    private final IUserService userService;

    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @PostMapping("/save/client")
    public ResponseEntity<?> getUser(@RequestBody UserRequest user) {
        try {
            return ResponseEntity.ok(userService.saveUserClient(user));
        } catch (DataServiceException e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage(), e.getStatusCode(), null));
        }
    }

    @PostMapping("/save/foundation")
    public ResponseEntity<?> saveUserFoundation(@RequestBody UserRequest user, @RequestParam UUID foundationUuid) {
        try {
            return ResponseEntity.ok(userService.saveUserFoundation(user, foundationUuid));
        } catch (DataServiceException e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage(), e.getStatusCode(), null));
        }
    }

    @PostMapping("/save/bank")
    public ResponseEntity<?> saveUserBank(@RequestBody UserRequest user, @RequestParam UUID clotheBankUuid) {
        try {
            return ResponseEntity.ok(userService.saveUserBank(user, clotheBankUuid));
        } catch (DataServiceException e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage(), e.getStatusCode(), null));
        }
    }

    @PostMapping("/save/wardrope")
    public ResponseEntity<?> saveUserWardrope(@RequestBody UserRequest user, @RequestParam UUID wardRopeUuid) {
        try {
            return ResponseEntity.ok(userService.saveUserWardrope(user, wardRopeUuid));
        } catch (DataServiceException e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage(), e.getStatusCode(), null));
        }
    }

    @GetMapping("/get/identification")
    public ResponseEntity<?> getUser(@RequestParam String identification) {
        try {
            return ResponseEntity.ok(userService.findUserByDocumentNumber(identification));
        } catch (DataServiceException e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage(), null, null));
        }
    }

    @Operation(summary = "#TODO: 22 de septiembre -> Obtener empleado de ropero", description = "Obtiene un empleado de ropero dado el nombre de usuario")
    @ApiResponse(responseCode = "200", description = "Entidad del usuario con el uuid del ropero al que pertenece", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = UserResponse.class))})
    @ApiResponse(responseCode = "400", description = "El valor mensaje retorna el mensaje de error", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))})
    @GetMapping("/get/wardrobe_employee")
    public ResponseEntity<?> getUserWardRobeByUsername(@RequestParam String username) {
        try {
            return ResponseEntity.ok(userService.UserWardRobeByUsername(username));
        } catch (DataServiceException e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage(), null, null));
        }
    }

    @Operation(summary = "#TODO: 22 de septiembre -> Obtener Empleado de Banco", description = "Obtiene un empleado de banco dado el nombre de usuario")
    @ApiResponse(responseCode = "200", description = "Entidad del usuario con el uuid del banco al que pertenece", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = UserResponse.class))})
    @ApiResponse(responseCode = "400", description = "El valor mensaje retorna el mensaje de error", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))})
    @GetMapping("/get/bank_employee")
    public ResponseEntity<?> getBankEmployeeByUsername(@RequestParam String username) {
        try {
            return ResponseEntity.ok(userService.findBankEmployeeByUsername(username));
        } catch (DataServiceException e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage(), null, null));
        }
    }
}
