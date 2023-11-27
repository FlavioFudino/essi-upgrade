package gob.pe.essalud.client.dto.usuario;

import lombok.Data;

import java.io.Serializable;

@Data
public class RolOauthDto implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long idRol;
    private String descripcion;


}
