package gob.pe.essalud.trx.dto;

import lombok.Data;

import java.util.Date;

@Data
public class SeguridadClienteDetalleRequestDto {
    private Long idCliente;
    private int numIntento;
    private String refModulo;
    private String refCampo;
    private String refValor;
    private String refCampo2;
    private String refValor2;
    private String refNota;
    private Date fechaExpiracion;
}
