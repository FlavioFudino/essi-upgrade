package gob.pe.essalud.trx.jpa.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity(name = "riesgo_diabetes")
@Table(name = "riesgo_diabetes")
@EqualsAndHashCode
public class RiesgoDiabetesModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_riesgo_diabetes", insertable = false, updatable = false, nullable = false)
    private Long idRiesgoDiabetes;

    @Column(name = "id_persona")
    private Integer idPersona;

    @Column(name = "puntos")
    private Integer puntos;

    @Column(name = "tipo_riesgo")
    private Integer tipoRiesgo;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_registro")
    private Date fechaRegistro;

}
