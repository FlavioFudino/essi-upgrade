package gob.pe.essalud.client.dto.citt;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class PersonaBuscarReqDto extends gob.pe.essalud.client.base.BaseDto {

    @NotEmpty(message = "El tipo de documento es obligatorio")
    @Size(min = 1, max = 1, message = "El tipo de documento es incorrecto")
    private String tipoDoc;

    @NotEmpty(message = "El numero de documento es obligatorio")
    @Size(min = 8, max = 12, message = "El número de documento debe tener 8 a 12 caracteres")
    private String numDoc;

}