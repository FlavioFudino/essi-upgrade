package gob.pe.essalud.trx.dto;

import gob.pe.essalud.trx.base.BaseDto;
import lombok.Data;

@Data
public class PacienteRequestDto extends BaseDto {
    private PacienteDto paciente;
    private DireccionDto direccionPaciente;
    private DireccionDto direccionFarmacia;
}
