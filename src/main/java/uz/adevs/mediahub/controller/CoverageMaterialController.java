package uz.adevs.mediahub.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import uz.adevs.mediahub.dto.request.CoverageMaterialCreateRequest;
import uz.adevs.mediahub.dto.response.ApiResponse;
import uz.adevs.mediahub.model.CoverageMaterial;
import uz.adevs.mediahub.repository.UserRepository;
import uz.adevs.mediahub.service.CoverageMaterialService;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/coverage-materials")
@RequiredArgsConstructor
public class CoverageMaterialController {

    private final CoverageMaterialService coverageMaterialService;
    private final UserRepository userRepository;

    @GetMapping
    public ResponseEntity<ApiResponse<List<CoverageMaterial>>> getAll() {
        return ResponseEntity.ok(ApiResponse.ok(coverageMaterialService.getAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CoverageMaterial>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.ok(coverageMaterialService.findById(id)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<CoverageMaterial>> create(
            @Valid @RequestBody CoverageMaterialCreateRequest req,
            @AuthenticationPrincipal UserDetails ud) {
        Long userId = userRepository.findByEmail(ud.getUsername()).get().getId();
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.ok(coverageMaterialService.create(req, userId)));
    }

    @PatchMapping("/{id}/approve")
    public ResponseEntity<ApiResponse<CoverageMaterial>> approve(
            @PathVariable Long id, @AuthenticationPrincipal UserDetails ud) {
        Long userId = userRepository.findByEmail(ud.getUsername()).get().getId();
        return ResponseEntity.ok(ApiResponse.ok(coverageMaterialService.approve(id, userId)));
    }

    @PatchMapping("/{id}/reject")
    public ResponseEntity<ApiResponse<CoverageMaterial>> reject(
            @PathVariable Long id, @RequestBody Map<String, String> body) {
        return ResponseEntity.ok(ApiResponse.ok(coverageMaterialService.reject(id, body.get("reason"))));
    }
}
