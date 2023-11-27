package gob.pe.essalud.client.dto.essi_consulta;

import lombok.Data;

@Data
public class ListaSolicitudCittItemRespDto extends gob.pe.essalud.client.base.BaseDto {
    private String lisNumNit;
    private String lisFecSol;
    private String lisFecIni;
    private String lisFecFin;
    private String lisEstado;
}