package gob.pe.essalud.client.dto.essi;

import lombok.Data;

@Data
public class EssiConfirmarSolicitudOperacionRequestDto extends gob.pe.essalud.client.base.BaseDto {
    private String codOriCas;
    private String codCas;
    private String numeroSolicitud;
    private String confOpera;
    private String usuarioExt;
}
