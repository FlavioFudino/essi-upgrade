package gob.pe.essalud.client.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class ClaveRecoveryRequestDto extends gob.pe.essalud.client.base.BaseDto {
    @NotEmpty(message = "El número de documento es obligatorio")
    @Size(min = 6, max = 20, message = "El numero de documento debe tener 6 a 20 caracteres")
    private String numeroDocIden;
    private String origin;
}
