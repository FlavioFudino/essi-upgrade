package gob.pe.essalud.trx.dto;

import lombok.Data;

@Data
public class ClaveRecoveryResponseDto {
    private Long idUsuario;
    private String username;
    private String correo;
    private Long idPaciente;
}
