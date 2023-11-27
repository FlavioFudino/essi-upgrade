package gob.pe.essalud.trx.jpa.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity(name = "paciente_familiar")
@Table(name = "paciente_familiar")
@IdClass(PacienteFamiliarPk.class)
public class PacienteFamiliarModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id_familiar")
    private Integer idFamiliar;

    @Id
    @Column(name = "id_paciente")
    private Integer idPaciente;

    @Basic
    @Column(name = "id_parentesco")
    private String parentesco;

    @Basic
    @Column(name = "id_estado")
    private String estado;

    @Basic
    @Column(name = "date_create")
    private Date dateCreate;

    @Basic
    @Column(name = "date_modify")
    private Date dateModify;

}
