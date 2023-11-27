package gob.pe.essalud.client.dto;

import gob.pe.essalud.client.common.constants.PatternConstants;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class ContactoDto extends gob.pe.essalud.client.base.BaseDto {
    private Integer idContacto;
    private Integer idPersona;
    private Integer idFamiliar;

    @Pattern(regexp = PatternConstants.DIGITS, message = "El numero de telefono solo puede contener numeros")
    @Size(max = 20, message = "El numero de telefono no puede tener mas de 20 caracteres")
    private String nroTelefonoFijo;

    @Pattern(regexp = PatternConstants.DIGITS, message = "El numero de celular solo puede contener numeros")
    @Size(max = 9, message = "El numero de celular no puede tener mas de 20 caracteres")
    private String nroCelular;

    @NotEmpty(message = "El operador es obligatorio")
    private String operador;

    @NotEmpty(message = "El correo es obligatorio")
    @Size(min = 1, max = 90, message = "La direccion no puede tener mas de 90 caracteres.")
    @Email(message = "El correo ingresado no es valido")
    private String email;
}
