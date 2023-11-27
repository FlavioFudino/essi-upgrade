package gob.pe.essalud.trx.jpa.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity(name = "paciente_farmacia")
@Table(name = "paciente_farmacia")
@IdClass(PacienteFarmaciaPk.class)
public class PacienteFarmaciaModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id_farmacia")
    private String idFarmacia;

    @Id
    @Column(name = "id_paciente")
    private Integer idPaciente;

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
