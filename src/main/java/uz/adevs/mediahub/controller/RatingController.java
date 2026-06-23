package uz.adevs.mediahub.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.adevs.mediahub.dto.response.ApiResponse;
import uz.adevs.mediahub.model.RatingRule;
import uz.adevs.mediahub.service.RatingService;
import java.util.List;

@RestController
@RequestMapping("/api/v1/ratings")
@RequiredArgsConstructor
public class RatingController {

    private final RatingService ratingService;

    @GetMapping("/rules")
    public ResponseEntity<ApiResponse<List<RatingRule>>> getRules() {
        return ResponseEntity.ok(ApiResponse.ok(ratingService.getRules()));
    }

    @PatchMapping("/rules/{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<ApiResponse<RatingRule>> updateRule(
            @PathVariable Long id, @RequestParam Integer ball) {
        return ResponseEntity.ok(ApiResponse.ok(ratingService.updateRule(id, ball)));
    }
}
