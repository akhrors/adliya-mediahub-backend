package uz.adevs.mediahub.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import uz.adevs.mediahub.constants.EventStatus;
import uz.adevs.mediahub.dto.request.EventCreateRequest;
import uz.adevs.mediahub.dto.response.ApiResponse;
import uz.adevs.mediahub.model.Event;
import uz.adevs.mediahub.model.EventChecklist;
import uz.adevs.mediahub.repository.UserRepository;
import uz.adevs.mediahub.service.EventService;
import java.util.List;

@RestController
@RequestMapping("/api/v1/events")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;
    private final UserRepository userRepository;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Event>>> getAll() {
        return ResponseEntity.ok(ApiResponse.ok(eventService.getAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Event>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.ok(eventService.findById(id)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Event>> create(
            @Valid @RequestBody EventCreateRequest req,
            @AuthenticationPrincipal UserDetails ud) {
        Long userId = userRepository.findByEmail(ud.getUsername()).get().getId();
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.ok(eventService.create(req, userId)));
    }

    @PostMapping("/{id}/submit")
    public ResponseEntity<ApiResponse<Event>> submit(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.ok(eventService.submit(id)));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<ApiResponse<Event>> updateStatus(
            @PathVariable Long id, @RequestParam EventStatus status) {
        return ResponseEntity.ok(ApiResponse.ok(eventService.updateStatus(id, status)));
    }

    @PatchMapping("/{id}/checklists/{itemId}")
    public ResponseEntity<ApiResponse<EventChecklist>> updateChecklist(
            @PathVariable Long id, @PathVariable Long itemId,
            @RequestParam boolean isDone, @AuthenticationPrincipal UserDetails ud) {
        Long userId = userRepository.findByEmail(ud.getUsername()).get().getId();
        return ResponseEntity.ok(ApiResponse.ok(eventService.updateChecklist(id, itemId, isDone, userId)));
    }
}
