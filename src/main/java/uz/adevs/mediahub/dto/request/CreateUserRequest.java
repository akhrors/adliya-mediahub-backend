package uz.adevs.mediahub.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class CreateUserRequest {
    @NotBlank private String fish;
    private String lavozim;
    @NotNull private Long tashkilotId;
    @NotNull private Long rolId;
    @NotBlank @Email private String email;
    @NotBlank private String password;
    private String telefon;
    private String telegramUsername;
}
