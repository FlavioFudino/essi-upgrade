package gob.pe.essalud.trx.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class UsuarioRegisterDto {
    @NotEmpty(message = "El codigo de activacion es obligatorio")
    private String codigo;

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
    private String numCelular;
    private String numTelefono;
    @NotEmpty(message = "El correo es obligatorio")
    private String correo;

    private String apellidoMaterno;
    private String apelidoPaterno;
    private String primerNombre;
    private String segundoNombre;
    private String codCentro;

    private Integer tipoConfirmacion; //1 = por EMAIL / 3 = por SMS
}
