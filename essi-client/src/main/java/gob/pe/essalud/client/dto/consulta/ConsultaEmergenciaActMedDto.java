package gob.pe.essalud.client.dto.consulta;

import lombok.Data;

@Data
public class ConsultaEmergenciaActMedDto extends gob.pe.essalud.client.base.BaseDto {
    private String actoMedicoAten;
    private String diagnost;
    private Integer actoSecuenAten;
    private String fechaAten;
    private String numDocProf;
    private String desTopicoAten;
    private String desActHosProf;
    private String tipDocProf;
    private String desTipoAtenEme;
    private String desServHosProf;
    private String desAreaHosProf;
    private String apeNomProf;
    private String codTopicoAten;
    private String horaAten;
}
