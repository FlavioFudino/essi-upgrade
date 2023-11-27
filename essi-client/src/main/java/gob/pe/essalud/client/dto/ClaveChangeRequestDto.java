package gob.pe.essalud.client.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class ClaveChangeRequestDto extends gob.pe.essalud.client.base.BaseDto {
    @NotEmpty(message = "el token es obligatorio")
    private String token;

    @NotEmpty(message = "La nueva contraseña es obligatorio")
    @Size(min = 6, max = 25, message = "La nueva contraseña debe tener 6 a 25 caracteres")
    private String nuevaClave;
}
