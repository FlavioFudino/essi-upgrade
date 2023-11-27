package gob.pe.essalud.client.dto.medico;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class PacienteCitadosRequestDto extends gob.pe.essalud.client.base.BaseDto {
    @NotEmpty(message = "El código de origen obligatorio")
    private String codori;
    @NotEmpty(message = "El código de centro obligatorio")
    private String codcentro;
    @NotEmpty(message = "El código de área obligatorio")
    private String codarea;
    @NotEmpty(message = "El código de servicio obligatorio")
    private String codserv;
    @NotEmpty(message = "El código de actividad obligatorio")
    private String codact;
    @NotEmpty(message = "El código de sub actividad obligatorio")
    private String codsubact;
    @NotEmpty(message = "El tipo de documento obligatorio")
    private String tipdoc;
    @NotEmpty(message = "El número de documento obligatorio")
    private String numdoc;
    @NotEmpty(message = "La fecha de programación")
    private String fecprog;
    @NotEmpty(message = "Turno inicial")
    private String turini;
    @NotEmpty(message = "Turno Final")
    private String turfin;
}