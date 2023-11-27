package gob.pe.essalud.trx.dto.emailservice;

import lombok.Data;

@Data
public class RegistrarUsuarioRequestDto {
    private String email;
    private String token;
}