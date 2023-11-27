package gob.pe.essalud.trx.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class ClaveRecoveryRequestDto {
    @NotEmpty(message = "El número de documento es obligatorio")
    private String numeroDocIden;
    private String origin;
}
