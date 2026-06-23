package uz.adevs.mediahub.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.adevs.mediahub.constants.UserStatus;
import uz.adevs.mediahub.dto.request.CreateUserRequest;
import uz.adevs.mediahub.dto.response.UserResponse;
import uz.adevs.mediahub.exception.ResourceNotFoundException;
import uz.adevs.mediahub.model.Organization;
import uz.adevs.mediahub.model.Role;
import uz.adevs.mediahub.model.User;
import uz.adevs.mediahub.repository.OrganizationRepository;
import uz.adevs.mediahub.repository.RoleRepository;
import uz.adevs.mediahub.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final OrganizationRepository organizationRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserResponse create(CreateUserRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already in use: " + request.getEmail());
        }
        Organization org = organizationRepository.findById(request.getTashkilotId())
                .orElseThrow(() -> new ResourceNotFoundException("Organization not found"));
        Role role = roleRepository.findById(request.getRolId())
                .orElseThrow(() -> new ResourceNotFoundException("Role not found"));

        User user = User.builder()
                .fish(request.getFish())
                .lavozim(request.getLavozim())
                .tashkilot(org)
                .rol(role)
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .telefon(request.getTelefon())
                .telegramUsername(request.getTelegramUsername())
                .status(UserStatus.ACTIVE)
                .build();
        return toResponse(userRepository.save(user));
    }

    public List<UserResponse> getAll() {
        return userRepository.findAll().stream().map(this::toResponse).collect(Collectors.toList());
    }

    public UserResponse getById(Long id) {
        return toResponse(userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + id)));
    }

    @Transactional
    public UserResponse updateStatus(Long id, UserStatus status) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + id));
        user.setStatus(status);
        return toResponse(userRepository.save(user));
    }

    public UserResponse toResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .fish(user.getFish())
                .lavozim(user.getLavozim())
                .email(user.getEmail())
                .telefon(user.getTelefon())
                .roleName(user.getRol().getRoleName())
                .roleDisplayName(user.getRol().getDisplayName())
                .tashkilotId(user.getTashkilot().getId())
                .tashkilotNomi(user.getTashkilot().getNomi())
                .status(user.getStatus().name())
                .build();
    }
}
