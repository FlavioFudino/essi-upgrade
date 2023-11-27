package gob.pe.essalud.trx.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCentroPacienteRequestDto {
    private Long idPaciente;
    private String codCentro;
}