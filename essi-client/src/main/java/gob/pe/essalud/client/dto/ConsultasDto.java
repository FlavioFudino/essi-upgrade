package gob.pe.essalud.client.dto;

import lombok.Data;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class ConsultasDto extends RequestGenericDto {
    @Size(min = 4, max = 4, message = "El periodo es un dato obligatorio, verifique que contengan datos númericos y se encuentren en el rango de longitud configurada.")
    @Pattern(regexp = "^[0-9]*", message = "El periodo es un dato obligatorio, verifique que contengan datos númericos y se encuentren en el rango de longitud configurada.")
    private String anio;
}
