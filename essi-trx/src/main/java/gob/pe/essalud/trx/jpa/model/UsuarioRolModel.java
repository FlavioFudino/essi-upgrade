package gob.pe.essalud.trx.jpa.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.Serializable;

@Data
@EqualsAndHashCode
@Entity(name = "usuario_rol")
@Table(name = "usuario_rol")
@IdClass(UsuarioRolPk.class)
public class UsuarioRolModel implements Serializable {

    @Id
    @Column(name = "id_usuario")
    private Integer idUsuario;

    @Id
    @Column(name = "id_rol")
    private Integer idRol;

}
