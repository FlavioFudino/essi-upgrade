package gob.pe.essalud.client.dto.bienvenida;

import lombok.Data;

@Data
public class BienvenidaListDto extends gob.pe.essalud.client.base.BaseDto {
    private Integer idBienvenida;
    private String imagenBase64;
}
