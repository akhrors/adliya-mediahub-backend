package uz.adevs.mediahub.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class EventCreateRequest {
    @NotBlank private String nomi;
    @NotBlank private String turi;
    @NotNull private Long tashkilotchiId;
    private String hudud;
    @NotNull private LocalDateTime sana;
    @NotBlank private String joy;
    private String ishtirokchilar;
    private String kunTartibi;
    private String pressReliz;
    private Boolean fotoKerak = false;
    private Boolean videoKerak = false;
    private Boolean oavTaklif = false;
    @NotNull private Long masulId;
    private String izoh;
}
