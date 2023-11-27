package gob.pe.essalud.trx.dto.emailservice;

import lombok.Data;

@Data
public class RecuperarClaveMobileRequestDto {
    private String email;
    private String token;
}
