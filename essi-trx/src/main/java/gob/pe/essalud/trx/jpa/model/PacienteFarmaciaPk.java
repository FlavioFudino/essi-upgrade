package gob.pe.essalud.trx.jpa.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

@Data
@EqualsAndHashCode
public class PacienteFarmaciaPk implements Serializable {

    @Id
    @Column(name = "id_farmacia")
    private String idFarmacia;

    @Id
    @Column(name = "id_paciente")
    private Integer idPaciente;
}
