package gob.pe.essalud.client.dto.essi_consulta;

import lombok.Data;

@Data
public class DetalleSolicitudCittItemRespDto extends gob.pe.essalud.client.base.BaseDto {
    private String detSolNumNit;
    private String detSolNumCitt;
    private String detSolCas;
    private String detSolTipDoc;
    private String detSolNumDoc;
    private String detSolFecIni;
    private String detSolFecFin;
    private String detSolNumDia;
    private String detSolContg;
    private String detSolRuc;
    private String detSolEstado;
    private String detSolMotDen;
}
