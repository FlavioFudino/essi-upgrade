package gob.pe.essalud.trx.dto;

import lombok.Data;

import java.util.Date;

@Data
public class SeguridadClienteRequestDto {
    private Long idCliente;
    private String ipCliente;
    private Integer intentos;
    private Date fechaUltimoIntento;
    private Boolean bloqueado;
    private Date fechaInicioBloqueo;
    private Date fechaFinBloqueo;
    private Boolean ignorar;
}
