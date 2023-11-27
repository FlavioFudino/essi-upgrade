package gob.pe.essalud.client.dto.medico.listaSolicitudExamen;

import lombok.Data;

@Data
public class ResultadoExamenDetDto extends gob.pe.essalud.client.base.BaseDto {
    private String nroSolicitud;
    private String codTipExam;
    private String codExamen;
    private String codMuestra;
    private String codOrdEle;
    private String desOrdEle;
    private String valResultado;
    private String valNormal;
    private String otrosValNor;
    private String otrasObs;
}
