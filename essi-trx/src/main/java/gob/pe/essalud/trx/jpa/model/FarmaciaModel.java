package gob.pe.essalud.trx.jpa.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@EqualsAndHashCode
@Entity(name = "farmacia")
@Table(name = "farmacia")
@IdClass(FarmaciaModelPk.class)
public class FarmaciaModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id_farmacia")
    private String idFarmacia;

    @Column(name = "nombre_farmacia")
    private String nombreFarmacia;

    @Column(name = "id_estado")
    private String idEstado;

    @Column(name = "date_create")
    private Date dateCreate;

    @Column(name = "date_modify")
    private Date dateModify;
}