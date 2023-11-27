package gob.pe.essalud.trx.jpa.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

@Data
@EqualsAndHashCode
public class FarmaciaModelPk implements Serializable {
    @Id
    @Column(name = "id_farmacia")
    private String idFarmacia;

}
