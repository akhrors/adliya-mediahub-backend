package uz.adevs.mediahub.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.adevs.mediahub.constants.UserStatus;
import uz.adevs.mediahub.dto.request.CreateUserRequest;
import uz.adevs.mediahub.dto.response.*;
import uz.adevs.mediahub.service.UserService;
import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<ApiResponse<UserResponse>> create(@Valid @RequestBody CreateUserRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.ok(userService.create(req)));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('SUPER_ADMIN','AXBOROT_XIZMATI_ADMIN')")
    public ResponseEntity<ApiResponse<List<UserResponse>>> getAll() {
        return ResponseEntity.ok(ApiResponse.ok(userService.getAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.ok(userService.getById(id)));
    }

    @PatchMapping("/{id}/status")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<ApiResponse<UserResponse>> updateStatus(@PathVariable Long id, @RequestParam UserStatus status) {
        return ResponseEntity.ok(ApiResponse.ok(userService.updateStatus(id, status)));
    }
}
