package gob.pe.essalud.trx.jpa.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@Entity(name = "contacto")
@Table(name = "contacto")
@EqualsAndHashCode
public class ContactoModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "LOG_CONTACTO_KEY")
    @SequenceGenerator(name = "LOG_CONTACTO_KEY", sequenceName = "contacto_id_contacto_seq", allocationSize = 1)
    @Column(name = "id_contacto", nullable = false)
    private Integer idContacto;

    @Basic
    @Column(name = "id_persona")
    private Integer idPersona;

    @Basic
    @Column(name = "nro_telefono_fijo")
    private String nroTelefonoFijo;

    @Basic
    @Column(name = "nro_celular")
    private String nroCelular;

    @Basic
    @Column(name = "id_operador_movil")
    private String operador;

    @Basic
    @Column(name = "id_estado")
    private String estado;

    @Basic
    @Column(name = "email")
    private String email;

    @Basic
    @Column(name = "date_create")
    private Date dateCreate;

    @Basic
    @Column(name = "date_modify")
    private Date dateModify;

    @Transient
    private List<String> propertiesUpdate;
}
