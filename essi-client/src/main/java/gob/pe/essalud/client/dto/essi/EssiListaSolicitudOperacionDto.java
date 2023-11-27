package gob.pe.essalud.client.dto.essi;

import lombok.Data;

import java.util.List;

@Data
public class EssiListaSolicitudOperacionDto extends gob.pe.essalud.client.base.BaseDto {
    private String apeNomProfAsis;
    private String codCas;
    private String codOriCas;
    private String desCas;
    private String desEstadoSolicitud;
    private String desServicioHospitalario;
    private String fechaSolicitud;
    private String numeroSolicitud;
    private List<EssiItemSolicitudOperacionDto> vCodOpera;
}
