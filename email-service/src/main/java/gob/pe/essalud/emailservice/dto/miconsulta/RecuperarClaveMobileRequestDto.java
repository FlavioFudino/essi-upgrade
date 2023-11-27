package gob.pe.essalud.emailservice.dto.miconsulta;

import lombok.Data;

@Data
public class RecuperarClaveMobileRequestDto {
    private String email;
    private String token;
}
