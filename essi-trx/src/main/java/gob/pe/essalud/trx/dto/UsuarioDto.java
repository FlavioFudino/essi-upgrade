package gob.pe.essalud.trx.dto;

import lombok.Data;

@Data
public class UsuarioDto {
    private Long idUsuario;
    private String username;
    private Long idPaciente;
}
