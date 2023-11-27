package gob.pe.essalud.emailservice.dto.miconsulta;

import lombok.Data;

@Data
public class RegistrarUsuarioRequestDto {
    private String email;
    private String token;
}
