package gob.pe.essalud.client.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class RecetasRequestDto extends RequestGenericDto {
    private String fecIni;
    private String fecFin;
    private String estado;
}
