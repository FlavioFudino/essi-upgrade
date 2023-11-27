package gob.pe.essalud.client.dto.farmacia;


import gob.pe.essalud.client.base.BaseDto;
import gob.pe.essalud.client.dto.PacienteDto;
import lombok.Data;

@Data
public class PacienteRequestDto extends BaseDto {
    private PacienteDto paciente;
    private DireccionDto direccionPaciente;
    private DireccionDto direccionFarmacia;
}
