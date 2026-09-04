package biagioCota.biagio.payloads.users;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class AdminPayload extends UserBasePayload {

    @NotNull(message = "La data di assunzione è obbligatoria")
    @PastOrPresent(message = "La data di assunzione non può essere nel futuro")
    private LocalDate dataAssunzione;
}
