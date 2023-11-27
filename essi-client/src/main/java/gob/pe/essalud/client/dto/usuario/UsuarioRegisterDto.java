package gob.pe.essalud.client.dto.usuario;

import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@ToString
public class UsuarioRegisterDto extends gob.pe.essalud.client.base.BaseDto {

    @NotEmpty(message = "El codigo de activacion es obligatorio")
    @Size(min = 4, max = 4, message = "El codigo de activacion debe contener 4 digitos")
    private String codigo;

    @NotEmpty(message = "El tipo de documento es obligatorio")
    @Size(min = 1, max = 1, message = "El tipo de documento es incorrecto")
    private String tipoDocIden;

    @NotEmpty(message = "El número de documento es obligatorio")
    @Size(min = 6, max = 20, message = "El número de documento debe tener 6 a 20 caracteres")
    private String numeroDocIden;

    @NotEmpty(message = "El caracter verificador es obligatorio")
    @Size(min = 1, max = 1, message = "El caracter verificador es obligatorio")
    private String codVerificador;

    @NotEmpty(message = "La fecha de nacimiento es obligatoria")
    @Size(min = 10, max = 10, message = "La fecha debe tener el formato DD/MM/YYYY")
    private String fecNacimiento;

    @NotEmpty(message = "El nombre de usuario es obligatorio")
    @Size(min = 6, max = 20, message = "El usuario debe tener 6 a 20 caracteres")
    private String username;

    @NotEmpty(message = "La contraseña es obligatoria")
    @Size(min = 6, max = 25, message = "La contraseña debe tener 6 a 25 caracteres")
    private String password;

    @NotEmpty(message = "El correo es obligatorio")
    @Email(message = "Debe ingresar un correo valido")
    private String correo;

    @NotEmpty(message = "El numero de celular es obligatorio")
    @Size(min = 9, max = 9, message = "El numero de celular debe tener 9 caracteres")
    private String numCelular;

    @NotNull(message = "Debe aceptar los Términos y Condiciones de Uso")
    private Boolean terminosCondiciones;

    @NotNull(message = "Debe aceptar la Autorización para el Tratamiento de Datos Personales")
    private Boolean autorizacionDatosPersonales;

    private String numTelefono;
    private String fecEmision;
    private String apellidoMaterno;
    private String apelidoPaterno;
    private String primerNombre;
    private String segundoNombre;
    private String codCentro;

    private Integer tipoConfirmacion; //1 = por EMAIL / 3 = por SMS
}