package gob.pe.essalud.client.dto.paciente;

import lombok.Data;

import java.util.List;

@Data
public class GetRiesgoDiabetesResponseDto {
    private int tipoRiesgo;
    private String fecha;
    private List<GetRiesgoDiabetesRespuestaDto> respuestas;
}
