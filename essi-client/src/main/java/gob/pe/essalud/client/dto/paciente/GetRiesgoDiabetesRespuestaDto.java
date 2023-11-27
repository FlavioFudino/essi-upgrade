package gob.pe.essalud.client.dto.paciente;

import lombok.Data;

@Data
public class GetRiesgoDiabetesRespuestaDto {
    private int pregunta;
    private int tipo;
    private String valor;
    private String ref;
}
