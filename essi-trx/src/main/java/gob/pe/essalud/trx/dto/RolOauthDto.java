package gob.pe.essalud.trx.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class RolOauthDto implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long idRol;
    private String descripcion;


}
