package gob.pe.essalud.client.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApplicationUserDto extends gob.pe.essalud.client.base.BaseDto {
    private String usuarioCod;
    private String tipoDocumento;
    private String numDocumento;
    private String primerNombre;
    private String segNombre;
    private String apPaterno;
    private String apMaterno;
}