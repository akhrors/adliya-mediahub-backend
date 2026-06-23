package uz.adevs.mediahub.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.adevs.mediahub.dto.response.ApiResponse;
import uz.adevs.mediahub.model.Organization;
import uz.adevs.mediahub.service.OrganizationService;
import java.util.List;

@RestController
@RequestMapping("/api/v1/organizations")
@RequiredArgsConstructor
public class OrganizationController {

    private final OrganizationService organizationService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Organization>>> getAll() {
        return ResponseEntity.ok(ApiResponse.ok(organizationService.getAll()));
    }

    @PostMapping
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<ApiResponse<Organization>> create(@RequestBody Organization org) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.ok(organizationService.create(org)));
    }
}
