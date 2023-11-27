package gob.pe.essalud.client.dto.essi;

import lombok.Data;

@Data
public class EssiListaSolicitudOperacionRequestDto extends gob.pe.essalud.client.base.BaseDto {
    private String tipDoc;
    private String numDoc;
    private String fechaIni;
    private String fechaFin;
    private String codEstado;
}
