package gob.pe.essalud.trx.jpa.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Data
@Entity(name = "direccion")
@Table(name = "direccion")
@EqualsAndHashCode
public class DireccionModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "LOG_DIRECCION_KEY")
    @SequenceGenerator(name = "LOG_DIRECCION_KEY", sequenceName = "direccion_id_direccion_seq", allocationSize = 1)
    @Column(name = "id_direccion", nullable = false)
    private Integer idDireccion;

    @Basic
    @Column(name = "id_persona")
    private Integer idPersona;

    @Basic
    @Column(name = "descripcion")
    private String descripcion;

    @Basic
    @Column(name = "referencia")
    private String referencia;

    @Basic
    @Column(name = "id_ubigeo")
    private String idUbigeo;

    @Basic
    @Column(name = "nro_latitud")
    private String nroLatitud;

    @Basic
    @Column(name = "nro_longitud")
    private String nroLongitud;

    @Basic
    @Column(name = "id_estado")
    private String idEstado;

    @Basic
    @Column(name = "id_tipo_via")
    private String idTipoVia;

    @Basic
    @Column(name = "date_create")
    private Date dateCreate;

    @Basic
    @Column(name = "date_modify")
    private Date dateModify;

    @Transient
    private List<String> propertiesUpdate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DireccionModel)) return false;
        DireccionModel that = (DireccionModel) o;
        return Objects.equals(getIdDireccion(), that.getIdDireccion()) &&
                Objects.equals(getDescripcion(), that.getDescripcion()) &&
                Objects.equals(getReferencia(), that.getReferencia()) &&
                Objects.equals(getIdUbigeo(), that.getIdUbigeo()) &&
                Objects.equals(getNroLatitud(), that.getNroLatitud()) &&
                Objects.equals(getNroLongitud(), that.getNroLongitud()) &&
                Objects.equals(getIdEstado(), that.getIdEstado()) &&
                Objects.equals(getIdTipoVia(), that.getIdTipoVia()) &&
                Objects.equals(getDateCreate(), that.getDateCreate()) &&
                Objects.equals(getDateModify(), that.getDateModify()) &&
                Objects.equals(getPropertiesUpdate(), that.getPropertiesUpdate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIdDireccion(), getDescripcion(), getReferencia(), getIdUbigeo(), getNroLatitud(), getNroLongitud(), getIdEstado(), getIdTipoVia(), getDateCreate(), getDateModify(), getPropertiesUpdate());
    }
}
