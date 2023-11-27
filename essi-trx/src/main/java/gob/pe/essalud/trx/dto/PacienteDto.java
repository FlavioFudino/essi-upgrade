package gob.pe.essalud.trx.dto;

import gob.pe.essalud.trx.base.BaseDto;
import lombok.Data;

import java.util.Date;

@Data
public class PacienteDto extends BaseDto {

    private Integer idPaciente;
    private String tipoDocIdent;
    private String numeroDocIdent;
    private String fechaNacimiento;
    private String nroTelefonoFijo;
    private String nroCelular;
    private String email;
    private String operador;
    private String token;
    private Date dateCreate;
    private Date dateModify;

}
