package gob.pe.essalud.client.dto.farmacia;

import gob.pe.essalud.client.base.BaseDto;
import lombok.Data;

@Data
public class DireccionDto extends BaseDto {
    private String idFarmacia;
    private String nombreFarmacia;
    private String descripcion;
    private String ubigeo;
    private String nroLatitud;
    private String nroLongitud;
}
