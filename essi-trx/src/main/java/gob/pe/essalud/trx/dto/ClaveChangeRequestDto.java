package gob.pe.essalud.trx.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class ClaveChangeRequestDto {
    @NotEmpty(message = "el token es obligatorio")
    private String token;

    @NotEmpty(message = "La nueva contraseña es obligatorio")
    private String nuevaClave;
}
