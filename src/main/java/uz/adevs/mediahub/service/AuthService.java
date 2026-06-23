package uz.adevs.mediahub.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.adevs.mediahub.dto.request.LoginRequest;
import uz.adevs.mediahub.dto.response.AuthResponse;
import uz.adevs.mediahub.dto.response.UserResponse;
import uz.adevs.mediahub.model.User;
import uz.adevs.mediahub.repository.UserRepository;
import uz.adevs.mediahub.utils.JwtUtils;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;

    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        String accessToken = jwtUtils.generateToken(user.getEmail());
        String refreshToken = jwtUtils.generateRefreshToken(user.getEmail());

        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .tokenType("Bearer")
                .user(mapToUserResponse(user))
                .build();
    }

    public AuthResponse refreshToken(String refreshToken) {
        if (!jwtUtils.validateToken(refreshToken)) {
            throw new RuntimeException("Invalid refresh token");
        }
        String email = jwtUtils.getEmailFromToken(refreshToken);
        String newAccessToken = jwtUtils.generateToken(email);
        return AuthResponse.builder()
                .accessToken(newAccessToken)
                .refreshToken(refreshToken)
                .tokenType("Bearer")
                .build();
    }

    private UserResponse mapToUserResponse(User user) {
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
