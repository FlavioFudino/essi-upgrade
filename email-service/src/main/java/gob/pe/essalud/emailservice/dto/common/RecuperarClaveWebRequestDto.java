package gob.pe.essalud.emailservice.dto.common;

import lombok.Data;

@Data
public class RecuperarClaveWebRequestDto {
    private String email;
    private String url;
}
