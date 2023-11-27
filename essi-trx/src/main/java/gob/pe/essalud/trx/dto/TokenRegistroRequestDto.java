package gob.pe.essalud.trx.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class TokenRegistroRequestDto {
    private Integer idUsuario; //
    private String token; //solo en validar token
    private String correo; //
    private String username; //solo en el recuperar clave
    private String origin; //solo en el recuperar clave
    private Integer tipo; //1 = confirmar registro EMAIL / 2 = recuperar clave / 3 = confirmar registro SMS
    private String numCelular; //Nuevo campo para usar en el SMS
}