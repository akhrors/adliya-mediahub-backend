package uz.adevs.mediahub.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import uz.adevs.mediahub.dto.request.ContentCalendarCreateRequest;
import uz.adevs.mediahub.dto.response.ApiResponse;
import uz.adevs.mediahub.model.ContentCalendar;
import uz.adevs.mediahub.repository.UserRepository;
import uz.adevs.mediahub.service.ContentCalendarService;
import java.util.List;

@RestController
@RequestMapping("/api/v1/content-calendar")
@RequiredArgsConstructor
public class ContentCalendarController {

    private final ContentCalendarService contentCalendarService;
    private final UserRepository userRepository;

    @GetMapping
    public ResponseEntity<ApiResponse<List<ContentCalendar>>> getAll() {
        return ResponseEntity.ok(ApiResponse.ok(contentCalendarService.getAll()));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ContentCalendar>> create(
            @Valid @RequestBody ContentCalendarCreateRequest req,
            @AuthenticationPrincipal UserDetails ud) {
        Long userId = userRepository.findByEmail(ud.getUsername()).get().getId();
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.ok(contentCalendarService.create(req, userId)));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<ApiResponse<ContentCalendar>> updateStatus(
            @PathVariable Long id, @RequestParam String status) {
        return ResponseEntity.ok(ApiResponse.ok(contentCalendarService.updateStatus(id, status)));
    }
}
