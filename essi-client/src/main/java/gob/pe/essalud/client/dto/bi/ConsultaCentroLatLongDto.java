package gob.pe.essalud.client.dto.bi;

import lombok.Data;

@Data
public class ConsultaCentroLatLongDto extends gob.pe.essalud.client.base.BaseDto {
    private String descripcion;
    private String latitud;
    private String longitud;
}
