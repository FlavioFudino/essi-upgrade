package gob.pe.essalud.client.dto.asegurado;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class AseguradoBasicRegisterDto extends gob.pe.essalud.client.base.BaseDto {
    @NotEmpty(message = "El tipo de documento es obligatorio")
    private String codTipDoc;
    @NotEmpty(message = "El número de documento es obligatorio")
    private String numDoc;
    private String fecNacimiento;
    private String userLogin;
}
