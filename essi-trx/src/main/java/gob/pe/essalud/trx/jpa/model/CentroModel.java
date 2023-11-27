package gob.pe.essalud.trx.jpa.model;

import gob.pe.essalud.trx.jpa.model.key.CentroKey;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@Entity(name = "centro")
@Table(name = "centro")
@IdClass(CentroKey.class)
@EqualsAndHashCode
public class CentroModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ori_cen_asi", nullable = false)
    private String oriCenAsi;

    @Id
    @Column(name = "cen_asi_cod", nullable = false)
    private String cenAsiCod;

    @Basic
    @Column(name = "descripcion")
    private String descripcion;

    @Basic
    @Column(name = "cod_red")
    private String codRed;

    @Basic
    @Column(name = "user_create")
    private String userCreate;

    @Basic
    @Column(name = "date_create")
    private Date dateCreate;

    @Basic
    @Column(name = "user_modify")
    private String userModify;

    @Basic
    @Column(name = "date_modify")
    private Date dateModify;

    @Basic
    @Column(name = "ind_padomi")
    private boolean indPadomi;

    @Basic
    @Column(name = "ind_cita")
    private boolean indCita;

    @Basic
    @Column(name = "latitud")
    private String latitud;

    @Basic
    @Column(name = "longitud")
    private String longitud;

    @Transient
    private List<String> propertiesUpdate;
}
