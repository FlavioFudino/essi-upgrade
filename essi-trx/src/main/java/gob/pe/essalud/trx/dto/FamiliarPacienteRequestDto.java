package gob.pe.essalud.trx.dto;

import gob.pe.essalud.trx.base.BaseDto;
import lombok.Data;

@Data
public class FamiliarPacienteRequestDto extends BaseDto {
    private String tipoDocIdent;
    private String numeroDocIdent;
    private Integer idPaciente;
    private FamiliarRequestDto familiar;
}
