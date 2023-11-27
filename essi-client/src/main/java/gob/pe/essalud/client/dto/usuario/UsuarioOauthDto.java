package gob.pe.essalud.client.dto.usuario;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class UsuarioOauthDto implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long idUsuario;
    private String username;
    private String password;
    private Boolean estado;
    private List<RolOauthDto> roles;

}
