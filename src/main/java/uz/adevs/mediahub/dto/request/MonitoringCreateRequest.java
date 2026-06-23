package uz.adevs.mediahub.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;
import uz.adevs.mediahub.constants.MonitoringDaraja;
import java.time.LocalDateTime;

@Data
public class MonitoringCreateRequest {
    private String turi = "TANQIDIY";
    private String link;
    @NotBlank private String platforma;
    private String muallifManba;
    @NotBlank private String mavzu;
    private Long hududTuzilmaId;
    @NotNull private MonitoringDaraja daraja;
    private Long masulOrgId;
    private LocalDateTime deadline;
}
