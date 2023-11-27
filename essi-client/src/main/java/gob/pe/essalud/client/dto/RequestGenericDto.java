package gob.pe.essalud.client.dto;

import gob.pe.essalud.client.base.BaseDto;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class RequestGenericDto extends BaseDto {
    @NotNull(message = "El número de documento es un dato obligatorio, verifique que contengan datos númericos y se encuentren en el rango de longitud configurada.")
    @Size(min = 6, max = 20, message = "El número de documento es un dato obligatorio, verifique que contengan datos númericos y se encuentren en el rango de longitud configurada.")
    @Pattern(regexp = "^[0-9]*", message = "El número de documento es un dato obligatorio, verifique que contengan datos númericos y se encuentren en el rango de longitud configurada.")
    private String numDoc;

    @NotNull(message = "El tipo de documento es un dato obligatorio, verifique que contengan datos númericos y se encuentren en el rango de longitud configurada.")
    @Size(min = 1, max = 1, message = "El tipo de documento es un dato obligatorio, verifique que contengan datos númericos y se encuentren en el rango de longitud configurada.")
    @Pattern(regexp = "^[1-5]*", message = "El tipo de documento es un dato obligatorio, verifique que contengan datos númericos y se encuentren en el rango de longitud configurada.")
    private String tipDoc;

}
