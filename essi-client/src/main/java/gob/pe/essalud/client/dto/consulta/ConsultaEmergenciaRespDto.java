package gob.pe.essalud.client.dto.consulta;

import lombok.Data;

import java.util.List;

@Data
public class ConsultaEmergenciaRespDto extends gob.pe.essalud.client.base.BaseDto {
    private String horaAltAdm;
    private String codPriAten;
    private String codAreaHosp;
    private String flgEgreEmer;
    private String fechaAltAdm;
    private String desPriAten;
    private String fechaAdmision;
    private String codOriCas;
    private String horaAltMed;
    private String desAreaHosp;
    private String flgSosCovid;
    private String desTipoAcc;
    private String nomApeAcom;
    private String codEmer;
    private String obserAdmision;
    private String desEmer;
    private String fechaAltMed;
    private String numActoMed;
    private String horaAdmision;
    private String codCenAsi;
    private String obsAltAdm;
    private List<ConsultaEmergenciaActMedDto> vActosMedicos;
}
