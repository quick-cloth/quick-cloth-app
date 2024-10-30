package org.example.quickclothapp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.example.quickclothapp.exception.DataServiceException;
import org.example.quickclothapp.payload.request.LoginRequest;
import org.example.quickclothapp.payload.response.LoginResponse;
import org.example.quickclothapp.payload.response.MessageResponse;
import org.example.quickclothapp.security.TokenProvider;
import org.example.quickclothapp.service.intf.IUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/application/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final TokenProvider tokenProvider;
    private final IUserService userService;

    public AuthController(AuthenticationManager authenticationManager, TokenProvider tokenProvider, IUserService userService) {
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
        this.userService = userService;
    }

    @Operation(summary = "Iniciar Sesión")
    @ApiResponse(responseCode = "200", description = "Token de usuario autenticado", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = LoginResponse.class))})
    @ApiResponse(responseCode = "400", description = "El valor mensaje retorna el mensaje de error", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))})
    @PostMapping("/login")
    public ResponseEntity<?> loginUser (@RequestBody LoginRequest loginRequest) throws DataServiceException {

        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        
        final UUID userUuid = userService.findUserByUsername(loginRequest.getUsername()).getUuid();
        final String token = tokenProvider.generateToken(authentication, userUuid);

        return ResponseEntity.ok(new LoginResponse(token));
    }
}
