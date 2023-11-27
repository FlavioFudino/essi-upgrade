package gob.pe.essalud.client.dto.seguridad_cliente;

import lombok.Data;

import java.util.Date;

@Data
public class SeguridadClienteDetalleRequestDto extends gob.pe.essalud.client.base.BaseDto {
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