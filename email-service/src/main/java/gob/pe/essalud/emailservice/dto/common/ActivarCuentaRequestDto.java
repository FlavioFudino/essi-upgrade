package gob.pe.essalud.emailservice.dto.common;

import lombok.Data;

@Data
public class ActivarCuentaRequestDto {
    private String email;
    private String token;
}
