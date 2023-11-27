package gob.pe.essalud.trx.jpa.model;

import gob.pe.essalud.trx.jpa.model.key.CitaKey;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity(name = "cita")
@Table(name = "cita")
@IdClass(CitaKey.class)
@EqualsAndHashCode
public class CitaModel implements Serializable  {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "act_med_num", nullable = false)
    private String actMedNum;

    @Id
    @Column(name = "cen_asi_cod", nullable = false)
    private String cenAsiCod;

    @Id
    @Column(name = "ori_cen_asi", nullable = false)
    private String oriCenAsi;

    @Id
    @Column(name = "ind_confirmado", nullable = false)
    private boolean indConfirmado;

    @Column(name = "tipo_doc_ident")
    private String tipoDocIdent;

    @Column(name = "numero_doc_ident")
    private String numeroDocIdent;

    @Column(name = "date_create")
    private Date dateCreate;

}
