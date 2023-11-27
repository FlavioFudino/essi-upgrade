package gob.pe.essalud.trx.dto;

import gob.pe.essalud.trx.base.BaseDto;
import lombok.Data;

import java.util.Date;

@Data
public class FamiliarDto extends BaseDto {

    private Integer idPaciente;
    private Integer idFamiliar;
    private String tipoDocIdent;
    private String numeroDocIdent;
    private String primerNombre;
    private String segundoNombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String parentesco;
    private String nroTelefonoFijo;
    private String nroCelular;
    private String email;
    private String operador;
    private Date dateCreate;
    private Date dateModify;

}
