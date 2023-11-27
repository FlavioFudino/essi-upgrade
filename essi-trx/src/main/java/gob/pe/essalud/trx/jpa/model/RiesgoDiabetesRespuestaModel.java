package gob.pe.essalud.trx.jpa.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Data
@Entity(name = "riesgo_diabetes_respuesta")
@Table(name = "riesgo_diabetes_respuesta")
@EqualsAndHashCode
public class RiesgoDiabetesRespuestaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_riesgo_diabetes_respuesta", insertable = false, updatable = false, nullable = false)
    private Long idRiesgoDiabetesRespuesta;

    @Column(name = "id_riesgo_diabetes")
    private Long idRiesgoDiabetes;

    @Column(name = "pregunta")
    private Integer pregunta;

    @Column(name = "tipo")
    private Integer tipo;

    @Column(name = "valor")
    private String valor;

    @Column(name = "ref")
    private String ref;

    @Column(name = "puntos")
    private Integer puntos;

}
