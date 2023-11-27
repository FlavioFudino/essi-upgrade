package gob.pe.essalud.client.dto.medico.listaSolicitudExamen;

import lombok.Data;

import java.util.List;

@Data
public class SolicitudExamenDto extends gob.pe.essalud.client.base.BaseDto {
    private String numSol;
    private String fecha;
    private String apeNom;
    private String actoMed;
    private Integer codEstado;
    private String estado;
    private String codOri;
    private String codCen;
    private String desCen;
    private List<ResultadoExamenDto> listaResExa;
}
