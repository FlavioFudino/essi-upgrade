package gob.pe.essalud.client.dto;

import gob.pe.essalud.client.base.BaseDto;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class PacienteDto extends BaseDto {

    private Long idPaciente;
    private String tipoDocIdent;
    private String numeroDocIdent;
    private String fechaNacimiento;
    private String nroTelefonoFijo;
    private String nroCelular;
    private String email;
    private String token;
}
