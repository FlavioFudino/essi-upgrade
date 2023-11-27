package gob.pe.essalud.client.dto;


import gob.pe.essalud.client.base.BaseDto;
import lombok.Data;

@Data
public class UbigeoRequestDto extends BaseDto {
    private String ubigeo;
    private String descripcion;
}
