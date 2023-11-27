package gob.pe.essalud.trx.dto.paciente;

import lombok.Data;

@Data
public class GetRiesgoDiabetesRespuestaDto {
    private int pregunta;
    private int tipo;
    private String valor;
    private String ref;
}
