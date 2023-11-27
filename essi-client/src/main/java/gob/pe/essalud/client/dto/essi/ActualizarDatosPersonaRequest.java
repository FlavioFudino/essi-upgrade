package gob.pe.essalud.client.dto.essi;

import lombok.Data;

@Data
public class ActualizarDatosPersonaRequest extends gob.pe.essalud.client.base.BaseDto {
    private String codTipDoc;
    private String numDoc;
    private String numCelular;
    private String email;
}
