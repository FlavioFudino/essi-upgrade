package gob.pe.essalud.client.dto.familiar;

import gob.pe.essalud.client.base.BaseDto;
import gob.pe.essalud.client.dto.ContactoDto;
import gob.pe.essalud.client.dto.DireccionDto;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class FamiliarRequestDto extends BaseDto {
    private Integer idFamiliar;
    private String tipoDocIdent;
    private String numeroDocIdent;

    @NotEmpty(message = "Debe ingresar el primer nombre del familiar")
    @Size(min = 3, max = 90, message = "El primer nombre debe contener entre 3 a 90 caracteres")
    private String primerNombre;

    @Size(max = 90, message = "El segundo nombre no puede contener mas de 90 caracteres")
    private String segundoNombre;

    @NotEmpty(message = "Debe ingresar el apellido paterno del familiar")
    @Size(min = 3, max = 90, message = "El apellido paterno debe contener entre 3 a 90 caracteres")
    private String apellidoPaterno;

    @NotEmpty(message = "Debe ingresar el apellido materno del familiar")
    @Size(min = 3, max = 90, message = "El apellido materno debe contener entre 3 a 90 caracteres")
    private String apellidoMaterno;

    private String fechaNacimiento;

    @NotEmpty(message = "El parentesco es obligatorio")
    private String parentesco;

    private ContactoDto contacto;

    private DireccionDto direccion;
}
