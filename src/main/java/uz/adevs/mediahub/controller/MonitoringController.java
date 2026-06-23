package uz.adevs.mediahub.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import uz.adevs.mediahub.constants.MonitoringStatus;
import uz.adevs.mediahub.dto.request.MonitoringCreateRequest;
import uz.adevs.mediahub.dto.response.ApiResponse;
import uz.adevs.mediahub.model.MonitoringItem;
import uz.adevs.mediahub.repository.UserRepository;
import uz.adevs.mediahub.service.MonitoringService;
import java.util.List;

@RestController
@RequestMapping("/api/v1/monitoring")
@RequiredArgsConstructor
public class MonitoringController {

    private final MonitoringService monitoringService;
    private final UserRepository userRepository;

    @GetMapping
    public ResponseEntity<ApiResponse<List<MonitoringItem>>> getAll() {
        return ResponseEntity.ok(ApiResponse.ok(monitoringService.getAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<MonitoringItem>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.ok(monitoringService.findById(id)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<MonitoringItem>> create(
            @Valid @RequestBody MonitoringCreateRequest req,
            @AuthenticationPrincipal UserDetails ud) {
        Long userId = userRepository.findByEmail(ud.getUsername()).get().getId();
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.ok(monitoringService.create(req, userId)));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<ApiResponse<MonitoringItem>> updateStatus(
            @PathVariable Long id, @RequestParam MonitoringStatus status) {
        return ResponseEntity.ok(ApiResponse.ok(monitoringService.updateStatus(id, status)));
    }
}
