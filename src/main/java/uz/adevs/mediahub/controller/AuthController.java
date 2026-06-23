package uz.adevs.mediahub.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.adevs.mediahub.dto.request.LoginRequest;
import uz.adevs.mediahub.dto.response.ApiResponse;
import uz.adevs.mediahub.dto.response.AuthResponse;
import uz.adevs.mediahub.service.AuthService;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(ApiResponse.ok(authService.login(request)));
    }

    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse<AuthResponse>> refresh(@RequestParam String refreshToken) {
        return ResponseEntity.ok(ApiResponse.ok(authService.refreshToken(refreshToken)));
    }
}
// Note: append health check - add this method inside the class manually
