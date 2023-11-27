package gob.pe.essalud.trx.dto;

import gob.pe.essalud.trx.base.BaseDto;
import lombok.Data;

@Data
public class FamiliarRequestDto extends BaseDto {
    private Integer idFamiliar;
    private String tipoDocIdent;
    private String numeroDocIdent;
    private String primerNombre;
    private String segundoNombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String fechaNacimiento;
    private String parentesco;
    private ContactoDto contacto;
    private DireccionDto direccion;
}
