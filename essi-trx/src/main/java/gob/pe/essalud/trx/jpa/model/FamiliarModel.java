package gob.pe.essalud.trx.jpa.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity(name = "familiar")
@Table(name = "familiar")
@EqualsAndHashCode
public class FamiliarModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id_familiar", nullable = false)
    private Integer idFamiliar;

    @Basic
    @Column(name = "date_create")
    private Date dateCreate;
}
