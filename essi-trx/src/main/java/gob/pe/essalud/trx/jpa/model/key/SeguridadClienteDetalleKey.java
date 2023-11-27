package gob.pe.essalud.trx.jpa.model.key;

import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

@EqualsAndHashCode
public class SeguridadClienteDetalleKey implements Serializable {
    private Date fecha;
    private Long idCliente;
    private Long numIntento;

    public SeguridadClienteDetalleKey() {

    }

    public SeguridadClienteDetalleKey(Date fecha, Long idCliente, Long numIntento) {
        this.fecha = fecha;
        this.idCliente = idCliente;
        this.numIntento = numIntento;
    }
}
