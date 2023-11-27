package gob.pe.essalud.trx.jpa.model;

import gob.pe.essalud.trx.jpa.model.key.SeguridadClienteDetalleKey;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity(name = "seguridad_cliente_detalle")
@Table(name = "seguridad_cliente_detalle")
@IdClass(SeguridadClienteDetalleKey.class)
@EqualsAndHashCode
public class SeguridadClienteDetalleModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "fecha", nullable = false)
    private Date fecha;

    @Id
    @Column(name = "id_cliente", nullable = false)
    private Long idCliente;

    @Id
    @Column(name = "num_intento", nullable = false)
    private Long numIntento;

    @Column(name = "ref_modulo", nullable = false)
    private String refModulo;

    @Column(name = "ref_campo")
    private String refCampo;

    @Column(name = "ref_valor")
    private String refValor;

    @Column(name = "ref_campo2")
    private String refCampo2;

    @Column(name = "ref_valor2")
    private String refValor2;

    @Column(name = "ref_nota")
    private String refNota;

    @Column(name = "fecha_expiracion", nullable = false)
    private Date fechaExpiracion;
}
