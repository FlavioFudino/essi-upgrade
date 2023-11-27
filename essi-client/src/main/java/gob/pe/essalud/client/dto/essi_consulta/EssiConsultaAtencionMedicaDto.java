package gob.pe.essalud.client.dto.essi_consulta;

import lombok.Data;

@Data
public class EssiConsultaAtencionMedicaDto extends gob.pe.essalud.client.base.BaseDto {
    private String servHospitalario;
    private String oricen;
    private String fechaAtencion;
    private String tipdocprof;
    private String actomed;
    private String areaHospitalaria;
    private String codservhos;
    private String profAsistencial;
    private String cas;
    private String codarehos;
    private String cenasis;
    private String diagnostico;
    private String numdocprof;
}
