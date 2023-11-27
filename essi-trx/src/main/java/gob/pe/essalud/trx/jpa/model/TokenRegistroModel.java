package gob.pe.essalud.trx.jpa.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@EqualsAndHashCode
@Entity(name = "token_registro")
@Table(name = "token_registro")
@ToString
public class TokenRegistroModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TOKEN_REGISTRO_KEY")
    @SequenceGenerator(name = "TOKEN_REGISTRO_KEY", sequenceName = "token_registro_id_token_registro_seq", allocationSize = 1)
    @Column(name = "id_token_registro", nullable = false)
    private Long idTokenRegistro;

    @Column(name = "token")
    private String token;

    @Basic
    @Column(name = "date_create")
    private Date dateCreate;

    @Basic
    @Column(name = "correo")
    private String correo;

    @Basic
    @Column(name = "date_expiration")
    private Date dateExpiration;

    @Basic
    @Column(name = "ind_confirmado")
    private boolean indConfirmado;

    @Basic
    @Column(name = "id_usuario")
    private Integer idUsuario;

    @Basic
    @Column(name = "tipo")
    private Integer tipo;
}
