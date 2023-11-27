package gob.pe.essalud.client.dto;

import gob.pe.essalud.client.common.constants.PatternConstants;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class DireccionDto extends gob.pe.essalud.client.base.BaseDto {
    private Integer idDireccion;
    private Integer idPaciente;
    private Integer idFamiliar;
    private String idFarmacia;

    @NotEmpty(message = "La direccion es obligatorio")
    @Size(min = 1, max = 255, message = "La direccion no puede tener mas de 255 caracteres.")
    @Pattern(regexp = PatternConstants.ALPHANUMERIC_SPECIAL, message = "La direccion solo puede tener caracteres alfanumericos")
    private String descripcion;

    @Pattern(regexp = PatternConstants.ALPHANUMERIC_SPECIAL, message = "La referencia solo puede tener caracteres alfanumericos")
    private String referencia;

    @NotEmpty(message = "El ubigeo (departamento,provincia,distrito) es obligatorio")
    private String ubigeo;

    private String nroLatitud;
    private String nroLongitud;
    private String idTipoVia;
}
