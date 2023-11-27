package gob.pe.essalud.client.dto.essi_consulta;

import lombok.Data;

@Data
public class DetalleSolicitudCittRespDto extends gob.pe.essalud.client.base.BaseDto {
    private String codError;
    private String desError;
    private DetalleSolicitudCittItemRespDto detalleSolDatos;
}
