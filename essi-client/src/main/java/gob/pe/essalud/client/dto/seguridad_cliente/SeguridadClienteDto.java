package gob.pe.essalud.client.dto.seguridad_cliente;

import lombok.Data;

import java.util.Date;

@Data
public class SeguridadClienteDto extends gob.pe.essalud.client.base.BaseDto {
    private Long idCliente;
    private String ipCliente;
    private Integer intentos;
    private String fechaUltimoIntento;
    private Boolean bloqueado;
    private Date fechaInicioBloqueo;
    private Date fechaFinBloqueo;
    private Boolean ignorar;
    private Date dateCreate;
    private Date dateModify;
}
