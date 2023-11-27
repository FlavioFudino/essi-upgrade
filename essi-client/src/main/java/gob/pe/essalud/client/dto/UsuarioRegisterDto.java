package gob.pe.essalud.client.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class UsuarioRegisterDto extends gob.pe.essalud.client.base.BaseDto {
    @NotEmpty(message = "El tipo de documento es obligatorio")
    private String tipoDocIden;

    @NotEmpty(message = "El número de documento es obligatorio")
    private String numeroDocIden;
    private String codVerificador;
    private String fecEmision;
    @NotEmpty(message = "La fecha de nacimiento es obligatoria")
    private String fecNacimiento;
    @NotEmpty(message = "El nombre de usuario es obligatorio")
    private String username;
    @NotEmpty(message = "La contraseña es obligatoria")
    private String password;
    private String nombrePadre;
    private String nombreMadre;
    @NotEmpty(message = "El correo es obligatorio")
    private String correo;

}
