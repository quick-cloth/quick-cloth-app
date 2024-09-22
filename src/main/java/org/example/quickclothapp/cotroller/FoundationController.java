package org.example.quickclothapp.cotroller;

import org.example.quickclothapp.exception.DataServiceException;
import org.example.quickclothapp.payload.request.FoundationRequest;
import org.example.quickclothapp.payload.response.MessageResponse;
import org.example.quickclothapp.service.intf.IFoundationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/application/foundation")
public class FoundationController {

    private final IFoundationService foundationService;

    public FoundationController(IFoundationService foundationService) {
        this.foundationService = foundationService;
    }

    @PostMapping("/save")
    public ResponseEntity<?> saveFoundation(@RequestBody FoundationRequest foundation) {
        try {
            return ResponseEntity.ok(foundationService.saveFoundation(foundation));
        } catch (DataServiceException e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage(), e.getStatusCode(), null));
        }
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateFoundation(@RequestBody FoundationRequest foundation) {
        try {
            return ResponseEntity.ok(foundationService.updateFoundation(foundation));
        } catch (DataServiceException e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage(), e.getStatusCode(), null));
        }
    }

    //@DeleteMapping("/delete")

    @GetMapping("/get_all/clothe_bank")
    public ResponseEntity<?> getAllFoundationByClotheBank(@RequestParam UUID clotheBankUuid) {
        try {
            return ResponseEntity.ok(foundationService.findAllFoundationByClotheBank(clotheBankUuid));
        } catch (DataServiceException e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage(), e.getStatusCode(), null));
        }
    }

    @GetMapping("/get")
    public ResponseEntity<?> getFoundation(@RequestParam UUID foundationUuid) {
        try {
            return ResponseEntity.ok(foundationService.findFoundationResponseByUuid(foundationUuid));
        } catch (DataServiceException e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage(), e.getStatusCode(), null));
        }
    }

    @GetMapping("/type_meet_us/get_all")
    public ResponseEntity<?> getAllTypeMeetUs() {
        try {
            return ResponseEntity.ok(foundationService.getAllTypeMeetUs());
        } catch (DataServiceException e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage(), e.getStatusCode(), null));
        }
    }
}
