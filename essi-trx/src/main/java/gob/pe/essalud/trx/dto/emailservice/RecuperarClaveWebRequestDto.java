package gob.pe.essalud.trx.dto.emailservice;

import lombok.Data;

@Data
public class RecuperarClaveWebRequestDto {
    private String email;
    private String url;
}