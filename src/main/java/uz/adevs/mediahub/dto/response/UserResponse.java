package uz.adevs.mediahub.dto.response;

import lombok.*;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class UserResponse {
    private Long id;
    private String fish;
    private String lavozim;
    private String email;
    private String telefon;
    private String roleName;
    private String roleDisplayName;
    private Long tashkilotId;
    private String tashkilotNomi;
    private String status;
}
