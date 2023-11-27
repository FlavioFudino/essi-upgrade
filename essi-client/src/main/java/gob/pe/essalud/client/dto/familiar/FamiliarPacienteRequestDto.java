package gob.pe.essalud.client.dto.familiar;

import gob.pe.essalud.client.base.BaseDto;
import lombok.Data;

@Data
public class FamiliarPacienteRequestDto extends BaseDto {
    private String tipoDocIdent;
    private String numeroDocIdent;
    private Integer idPaciente;
    private FamiliarRequestDto familiar;
}
