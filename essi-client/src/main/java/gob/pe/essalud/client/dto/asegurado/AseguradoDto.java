package gob.pe.essalud.client.dto.asegurado;

import gob.pe.essalud.client.base.BaseDto;
import lombok.Data;

import java.util.Date;

@Data
public class AseguradoDto extends BaseDto {

    private Integer idAsegurado;
    private String tipoDocIdent;
    private String numeroDocIdent;
    private String nombres;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String codSexo;
    private int edad;
    private String fechaNacimiento;
    private Date dateCreate;
    private Date dateModify;

}
