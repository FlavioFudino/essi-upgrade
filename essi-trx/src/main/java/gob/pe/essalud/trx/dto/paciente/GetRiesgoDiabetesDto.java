package gob.pe.essalud.trx.dto.paciente;

import lombok.Data;

@Data
public class GetRiesgoDiabetesDto {
    private Long idRiesgoDiabetes;
    private Integer tipoRiesgo;
    private String fecha;
}
