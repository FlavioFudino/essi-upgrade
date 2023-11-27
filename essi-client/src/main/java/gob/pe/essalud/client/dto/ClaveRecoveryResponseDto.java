package gob.pe.essalud.client.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClaveRecoveryResponseDto extends gob.pe.essalud.client.base.BaseDto {
    private String correo; //correo protegido
}
