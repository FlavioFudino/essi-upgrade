package gob.pe.essalud.client.dto.essi;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class EssiPacienteRequestDto extends gob.pe.essalud.client.base.BaseDto {
    private String codOpcion;
    @NotEmpty(message = "El tipo de documento es obligatorio")
    private String codTipDoc;
    @NotEmpty(message = "El número de documento es obligatorio")
    private String numDoc;
    private String fecNacimiento;
}
