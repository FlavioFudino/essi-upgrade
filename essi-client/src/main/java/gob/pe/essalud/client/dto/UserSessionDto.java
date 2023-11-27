package gob.pe.essalud.client.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserSessionDto extends gob.pe.essalud.client.base.BaseDto {
    private String tipoDocumento;
    private String numeroDocumento;
}
