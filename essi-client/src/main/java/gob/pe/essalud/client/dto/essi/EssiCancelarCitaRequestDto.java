package gob.pe.essalud.client.dto.essi;

import lombok.Data;

@Data
public class EssiCancelarCitaRequestDto extends gob.pe.essalud.client.base.BaseDto {
    private String codCentro;
    private String codTipDoc;
    private String numDoc;
    private String numCitaCreada; //citActMedNum
    private String motEli; // 05 = DISCONFORMIDAD_PACIENTE
}
