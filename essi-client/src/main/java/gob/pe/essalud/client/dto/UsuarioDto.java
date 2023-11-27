package gob.pe.essalud.client.dto;

import lombok.Data;

@Data
public class UsuarioDto extends gob.pe.essalud.client.base.BaseDto {
    private Long idUsuario;
    private String username;
    private Long idPaciente;
}
