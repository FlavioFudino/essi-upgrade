package gob.pe.essalud.trx.jpa.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

@Data
@EqualsAndHashCode
public class PacienteFamiliarPk implements Serializable {

    @Id
    @Column(name = "id_familiar")
    private Integer idFamiliar;

    @Id
    @Column(name = "id_paciente")
    private Integer idPaciente;
}
