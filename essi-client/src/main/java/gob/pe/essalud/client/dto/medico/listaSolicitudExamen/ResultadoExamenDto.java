package gob.pe.essalud.client.dto.medico.listaSolicitudExamen;

import lombok.Data;

import java.util.List;

@Data
public class ResultadoExamenDto extends gob.pe.essalud.client.base.BaseDto {
    private String nroSol;
    private String codTipExam;
    private String desTipExam;
    private String codExamen;
    private String desExamen;
    private String informeRes;
    private String fechaRes;
    private String codRptaRap;
    private String desRptaRap;
    private Integer codEstado;
    private String desEstado;
    private List<ResultadoExamenDetDto> listaResExaDet;
}
