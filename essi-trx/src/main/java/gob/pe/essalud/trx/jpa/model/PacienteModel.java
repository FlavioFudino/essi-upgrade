package gob.pe.essalud.trx.jpa.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity(name = "paciente")
@Table(name = "paciente")
@EqualsAndHashCode
public class PacienteModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id_paciente", nullable = false)
    private Integer idPaciente;

    @Column(name = "ind_padomi")
    private Boolean indPadomi;


    @Column(name = "cod_centro")
    private String codCentro;

    @Column(name = "ruc_empl")
    private String rucEmpl;

    @Basic
    @Column(name = "date_create")
    private Date dateCreate;

    @Basic
    @Column(name = "date_modify")
    private Date dateModify;


}
