package uz.adevs.mediahub.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import uz.adevs.mediahub.dto.response.ApiResponse;
import uz.adevs.mediahub.model.Notification;
import uz.adevs.mediahub.repository.UserRepository;
import uz.adevs.mediahub.service.NotificationService;
import java.util.List;

@RestController
@RequestMapping("/api/v1/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;
    private final UserRepository userRepository;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Notification>>> getAll(@AuthenticationPrincipal UserDetails ud) {
        Long userId = userRepository.findByEmail(ud.getUsername()).get().getId();
        return ResponseEntity.ok(ApiResponse.ok(notificationService.getByUser(userId)));
    }

    @PatchMapping("/{id}/read")
    public ResponseEntity<ApiResponse<Void>> markRead(@PathVariable Long id) {
        notificationService.markRead(id);
        return ResponseEntity.ok(ApiResponse.ok("O'qildi", null));
    }

    @PatchMapping("/read-all")
    public ResponseEntity<ApiResponse<Void>> markAllRead(@AuthenticationPrincipal UserDetails ud) {
        Long userId = userRepository.findByEmail(ud.getUsername()).get().getId();
        notificationService.markAllRead(userId);
        return ResponseEntity.ok(ApiResponse.ok("Hammasi o'qildi", null));
    }
}
