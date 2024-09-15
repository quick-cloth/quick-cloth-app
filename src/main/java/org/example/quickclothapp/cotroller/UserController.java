package org.example.quickclothapp.cotroller;

import org.example.quickclothapp.exception.DataServiceException;
import org.example.quickclothapp.payload.request.UserRequest;
import org.example.quickclothapp.payload.response.MessageResponse;
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
}
