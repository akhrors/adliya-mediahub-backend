package uz.adevs.mediahub.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class ContentCalendarCreateRequest {
    @NotBlank private String kontentNomi;
    @NotBlank private String format;
    @NotBlank private String platforma;
    private Long masulId;
    @NotNull private LocalDate muddat;
    private LocalDateTime elonVaqti;
    private String izoh;
}
