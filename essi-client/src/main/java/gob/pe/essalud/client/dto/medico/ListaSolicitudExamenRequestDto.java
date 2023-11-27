package gob.pe.essalud.client.dto.medico;

import lombok.Data;

@Data
public class ListaSolicitudExamenRequestDto extends gob.pe.essalud.client.base.BaseDto {
    private String tipDoc;
    private String numDoc;
    private String codOri;
    private String opcion;
    private String fecDesde;
}
