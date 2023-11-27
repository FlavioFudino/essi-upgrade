package gob.pe.essalud.trx.jpa.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity(name = "seguridad_cliente")
@Table(name = "seguridad_cliente")
@EqualsAndHashCode
public class SeguridadClienteModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "LOG_SEGURIDAD_CLIENTE_KEY")
    @SequenceGenerator(name = "LOG_SEGURIDAD_CLIENTE_KEY", sequenceName = "seguridad_cliente_id_cliente_seq", allocationSize = 1)
    @Column(name = "id_cliente", nullable = false)
    private Long idCliente;

    @Column(name = "ip_cliente", nullable = false, unique = true)
    private String ipCliente;

    @Column(name = "intentos", nullable = false)
    private Integer intentos;

    @Column(name = "fecha_ultimo_intento")
    private Date fechaUltimoIntento;

    @Column(name = "bloqueado", nullable = false)
    private Boolean bloqueado;

    @Column(name = "fecha_inicio_bloqueo")
    private Date fechaInicioBloqueo;

    @Column(name = "fecha_fin_bloqueo")
    private Date fechaFinBloqueo;

    @Column(name = "ignorar", nullable = false)
    private Boolean ignorar;

    @Basic
    @Column(name = "date_create")
    private Date dateCreate;

    @Basic
    @Column(name = "date_modify")
    private Date dateModify;
}
