package gob.pe.essalud.client.dto.medico;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class ProgramacionRequestDto extends gob.pe.essalud.client.base.BaseDto {
    @NotEmpty(message = "El tipo de documento es obligatorio")
    private String tipdoc;
    @NotEmpty(message = "El número de documento es obligatorio")
    private String numdoc;
    @NotEmpty(message = "La fecha de programación es obligatoria")
    private String fecprog;
}