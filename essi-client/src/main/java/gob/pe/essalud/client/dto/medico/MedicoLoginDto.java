package gob.pe.essalud.client.dto.medico;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class MedicoLoginDto extends gob.pe.essalud.client.base.BaseDto {
    @NotEmpty(message = "El código de usuario es obligatorio")
    private String usuario;
    @NotEmpty(message = "La contraseña es obligatorio")
    private String password;

}