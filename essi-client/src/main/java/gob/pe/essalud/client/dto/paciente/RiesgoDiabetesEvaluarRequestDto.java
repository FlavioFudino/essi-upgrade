package gob.pe.essalud.client.dto.paciente;

import lombok.Data;

import java.util.List;

@Data
public class RiesgoDiabetesEvaluarRequestDto {
    private String tipDoc;
    private String numDoc;
    private String genero;
    private List<String> respuestas;
}
