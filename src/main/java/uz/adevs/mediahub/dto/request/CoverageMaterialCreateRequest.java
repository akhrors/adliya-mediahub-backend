package uz.adevs.mediahub.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDate;

@Data
public class CoverageMaterialCreateRequest {
    @NotBlank private String nomi;
    @NotBlank private String materialTuri;
    @NotBlank private String platforma;
    private String havola;
    @NotNull private LocalDate sana;
    private Long hududTuzilmaId;
    private String mavzu;
    private Long korishlarSoni;
    private String izoh;
}
