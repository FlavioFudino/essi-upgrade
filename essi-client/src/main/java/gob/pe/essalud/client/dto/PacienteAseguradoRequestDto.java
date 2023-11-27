package gob.pe.essalud.client.dto;

import lombok.Data;

@Data
public class PacienteAseguradoRequestDto extends gob.pe.essalud.client.base.BaseDto {
    private String tipoDoc;
    private String numDoc;
    private String fecNac;
}