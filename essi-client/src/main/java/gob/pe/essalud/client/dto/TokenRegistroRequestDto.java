package gob.pe.essalud.client.dto;

import lombok.Data;

@Data
public class TokenRegistroRequestDto extends gob.pe.essalud.client.base.BaseDto {
    private long idUsuario;
    private String token;
    private String correo;
    private String username;
    private Integer tipo; //1 = confirmar registro EMAIL / 2 = recuperar clave / 3 = confirmar registro SMS
    private String numCelular; //Nuevo campo para usar en el SMS
}