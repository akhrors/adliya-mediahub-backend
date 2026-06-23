package uz.adevs.mediahub.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class MediaRequestCreateRequest {
    @NotNull private Long oavId;
    @NotBlank private String mavzu;
    @NotBlank private String yonalish;
    @NotNull private LocalDateTime sana;
    @NotBlank private String joy;
    private Integer kerakliMutaxassislarSoni = 1;
    @NotNull private Long tarkibiyTuzilmaId;
    @NotNull private Integer taymerMuddatMinut;
    private String izoh;
}
