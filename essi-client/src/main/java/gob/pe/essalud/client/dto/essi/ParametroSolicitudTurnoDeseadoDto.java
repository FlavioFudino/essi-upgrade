package gob.pe.essalud.client.dto.essi;

import lombok.Data;

@Data
public class ParametroSolicitudTurnoDeseadoDto extends gob.pe.essalud.client.base.BaseDto {
    private String codTurnoDeseado;
    private String desTurnoDeseado;
}
