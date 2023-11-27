package gob.pe.essalud.client.dto.essi;

import lombok.Data;

@Data
public class ParametroSolicitudFechaDeseadaDto extends gob.pe.essalud.client.base.BaseDto {
    private String codFechaDeseada;
    private String desFechaDeseada;
}
