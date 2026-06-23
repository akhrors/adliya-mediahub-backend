package uz.adevs.mediahub.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import uz.adevs.mediahub.constants.MediaRequestStatus;
import uz.adevs.mediahub.dto.request.MediaRequestCreateRequest;
import uz.adevs.mediahub.dto.response.ApiResponse;
import uz.adevs.mediahub.model.MediaRequest;
import uz.adevs.mediahub.repository.UserRepository;
import uz.adevs.mediahub.service.MediaRequestService;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/media-requests")
@RequiredArgsConstructor
public class MediaRequestController {

    private final MediaRequestService mediaRequestService;
    private final UserRepository userRepository;

    @GetMapping
    public ResponseEntity<ApiResponse<List<MediaRequest>>> getAll(
            @RequestParam(required = false) MediaRequestStatus status) {
        List<MediaRequest> list = status != null
                ? mediaRequestService.getByStatus(status)
                : mediaRequestService.getAll();
        return ResponseEntity.ok(ApiResponse.ok(list));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<MediaRequest>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.ok(mediaRequestService.findById(id)));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('AXBOROT_XIZMATI_ADMIN','SUPER_ADMIN')")
    public ResponseEntity<ApiResponse<MediaRequest>> create(
            @Valid @RequestBody MediaRequestCreateRequest req,
            @AuthenticationPrincipal UserDetails ud) {
        Long userId = userRepository.findByEmail(ud.getUsername()).get().getId();
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.ok(mediaRequestService.create(req, userId)));
    }

    @PostMapping("/{id}/assign-expert")
    @PreAuthorize("hasAnyRole('TUZILMA_RAHBARI','SUPER_ADMIN')")
    public ResponseEntity<ApiResponse<MediaRequest>> assignExpert(
            @PathVariable Long id,
            @RequestBody Map<String, Long> body,
            @AuthenticationPrincipal UserDetails ud) {
        Long userId = userRepository.findByEmail(ud.getUsername()).get().getId();
        return ResponseEntity.ok(ApiResponse.ok(
                mediaRequestService.assignExpert(id, body.get("expertId"), userId)));
    }

    @PatchMapping("/{id}/approve-expert/{assignmentId}")
    public ResponseEntity<ApiResponse<MediaRequest>> approveExpert(
            @PathVariable Long id, @PathVariable Long assignmentId) {
        return ResponseEntity.ok(ApiResponse.ok(mediaRequestService.approveExpert(id, assignmentId)));
    }

    @PatchMapping("/{id}/complete")
    public ResponseEntity<ApiResponse<MediaRequest>> complete(
            @PathVariable Long id, @RequestBody Map<String, String> body) {
        return ResponseEntity.ok(ApiResponse.ok(mediaRequestService.complete(id, body.get("havola"))));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<ApiResponse<MediaRequest>> updateStatus(
            @PathVariable Long id, @RequestParam MediaRequestStatus status) {
        return ResponseEntity.ok(ApiResponse.ok(mediaRequestService.updateStatus(id, status)));
    }
}
