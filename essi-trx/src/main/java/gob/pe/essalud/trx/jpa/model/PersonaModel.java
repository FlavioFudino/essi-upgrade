package gob.pe.essalud.trx.jpa.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@Entity(name = "persona")
@Table(name = "persona")
@EqualsAndHashCode
public class PersonaModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "LOG_PERSONA_KEY")
    @SequenceGenerator(name = "LOG_PERSONA_KEY", sequenceName = "persona_id_persona_seq", allocationSize = 1)
    @Column(name = "id_persona", nullable = false)
    private Integer idPersona;

    @Basic
    @Column(name = "tipo_doc_ident")
    private String tipoDocIdent;

    @Basic
    @Column(name = "numero_doc_ident")
    private String numeroDocIdent;

    @Basic
    @Column(name = "primer_nombre")
    private String primerNombre;

    @Basic
    @Column(name = "segundo_nombre")
    private String segundoNombre;

    @Basic
    @Column(name = "apellido_paterno")
    private String apellidoPaterno;

    @Basic
    @Column(name = "apellido_materno")
    private String apellidoMaterno;

    @Basic
    @Column(name = "fecha_nacimiento")
    private String fechaNacimiento;


    @Basic
    @Column(name = "date_create")
    private Date dateCreate;

    @Basic
    @Column(name = "date_modify")
    private Date dateModify;

    @Basic
    @Column(name = "tipo")
    private Integer tipo;

    @Basic
    @Column(name = "id_usuario_registra")
    private Integer idUsuarioRegistra;

    @Transient
    private List<String> propertiesUpdate;
}
