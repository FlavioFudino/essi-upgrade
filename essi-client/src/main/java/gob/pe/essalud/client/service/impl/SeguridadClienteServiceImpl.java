package gob.pe.essalud.client.service.impl;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gob.pe.essalud.client.client.trx.TrxClient;
import gob.pe.essalud.client.common.util.DateUtil;
import gob.pe.essalud.client.common.util.Util;
import gob.pe.essalud.client.config.SeguridadClienteConfig;
import gob.pe.essalud.client.dto.seguridad_cliente.SeguridadClienteDetalleRequestDto;
import gob.pe.essalud.client.dto.seguridad_cliente.SeguridadClienteDto;
import gob.pe.essalud.client.dto.seguridad_cliente.SeguridadClienteRequestDto;
import gob.pe.essalud.client.service.SeguridadClienteService;
import gob.pe.essalud.client.service.ServiceException;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class SeguridadClienteServiceImpl implements SeguridadClienteService {

    private int INTENTOS_ILIMITADOS = -1;

    private final HttpServletRequest httpServletRequest;
    private final TrxClient trxClient;
    private final SeguridadClienteConfig seguridadClienteConfig;

    @Autowired
    public SeguridadClienteServiceImpl(
            HttpServletRequest httpServletRequest,
            TrxClient trxClient,
            SeguridadClienteConfig seguridadClienteConfig) {

        this.httpServletRequest = httpServletRequest;
        this.trxClient = trxClient;
        this.seguridadClienteConfig = seguridadClienteConfig;
    }

    @Override
    public void verificarAcceso() {
        if (!seguridadClienteConfig.isEnabled())
            return;

        String ipCliente = getClientIP();
        SeguridadClienteDto seguridadClienteDto = trxClient.getSeguridadClienteByIpCliente(ipCliente);
        boolean existeRegistro = (seguridadClienteDto != null);

        if (!existeRegistro)
            return;

        if (seguridadClienteDto.getIgnorar())
            return;

        if (seguridadClienteDto.getBloqueado())
            throw new ServiceException("Se excedió el limite de solicitudes máxima");
    }

    @Override
    public int incrementarIntento(
            String modulo,
            String refCampo,
            String refValor,
            String refCampo2,
            String refValor2,
            String refNota) {
        try {
            if (!seguridadClienteConfig.isEnabled())
                return INTENTOS_ILIMITADOS;

            String ipCliente = getClientIP();
            SeguridadClienteDto seguridadClienteDto = trxClient.getSeguridadClienteByIpCliente(ipCliente);
            boolean existeRegistro = (seguridadClienteDto != null);

            if (existeRegistro) {
                if (seguridadClienteDto.getIgnorar()) //WhiteList
                    return INTENTOS_ILIMITADOS;

                if (seguridadClienteDto.getBloqueado()) //BlackList
                    throw new ServiceException("Se excedió el limite de solicitudes máxima");
            }

            Date currentDate = trxClient.getCurrentDate();
            SeguridadClienteRequestDto requestDto = null;

            if (!existeRegistro) {
                //Si no existe el registro lo creamos
                requestDto = new SeguridadClienteRequestDto();
                requestDto.setIdCliente(null);
                requestDto.setIpCliente(ipCliente);
                requestDto.setIntentos(1);
                requestDto.setFechaUltimoIntento(currentDate);
                requestDto.setBloqueado(false);
                requestDto.setFechaInicioBloqueo(null);
                requestDto.setFechaFinBloqueo(null);
                requestDto.setIgnorar(false);
            }
            else {
                //si ya existe aumentamos intento y verificamos
                requestDto = Util.objectToObject(SeguridadClienteRequestDto.class, seguridadClienteDto);
                requestDto.setIntentos(requestDto.getIntentos() + 1);
                requestDto.setFechaUltimoIntento(currentDate);

                //verificar si excede el limite de intentos
                if (requestDto.getIntentos() >= seguridadClienteConfig.getMaxAttempts()) {
                    requestDto.setBloqueado(true);
                    requestDto.setIntentos(requestDto.getIntentos());
                    requestDto.setFechaInicioBloqueo(currentDate);
                    requestDto.setFechaFinBloqueo(DateUtil.addMinutes(currentDate,seguridadClienteConfig.getBlockTimeInMinutes()));
                }
            }

            SeguridadClienteDto responseDto = trxClient.saveSeguridadCliente(requestDto);

            if (responseDto == null)
                throw new ServiceException("Ocurrio un error al procesar la operación");

            SeguridadClienteDetalleRequestDto requestDetalleDto = new SeguridadClienteDetalleRequestDto();
            requestDetalleDto.setIdCliente(responseDto.getIdCliente());
            requestDetalleDto.setNumIntento(responseDto.getIntentos());
            requestDetalleDto.setRefModulo(modulo);
            requestDetalleDto.setRefCampo(refCampo);
            requestDetalleDto.setRefValor(refValor);
            requestDetalleDto.setRefCampo2(refCampo2);
            requestDetalleDto.setRefValor2(refValor2);
            requestDetalleDto.setRefNota(refNota);
            requestDetalleDto.setFechaExpiracion(DateUtil.addHours(currentDate,seguridadClienteConfig.getDetalleExpireInHours()));
            trxClient.saveSeguridadClienteDetalle(requestDetalleDto);

            if (responseDto.getBloqueado())
                throw new ServiceException("Se excedió el limite de solicitudes máxima.");

            int intentosRestantes = (seguridadClienteConfig.getMaxAttempts() - responseDto.getIntentos());
            return intentosRestantes;
        }
        catch (Exception ex) {
            throw ex;
        }
    }

    @Override
    public void reiniciarIntentos() {
        if (!seguridadClienteConfig.isEnabled())
            return;

        String ipCliente = getClientIP();
        SeguridadClienteDto seguridadClienteDto = trxClient.getSeguridadClienteByIpCliente(ipCliente);
        boolean existeRegistro = (seguridadClienteDto != null);

        if (!existeRegistro)
            return;

        SeguridadClienteRequestDto requestDto = Util.objectToObject(SeguridadClienteRequestDto.class, seguridadClienteDto);
        requestDto.setIntentos(0);

        SeguridadClienteDto responseDto = trxClient.saveSeguridadCliente(requestDto);

        Date currentDate = trxClient.getCurrentDate();
        SeguridadClienteDetalleRequestDto requestDetalleDto = new SeguridadClienteDetalleRequestDto();
        requestDetalleDto.setIdCliente(responseDto.getIdCliente());
        requestDetalleDto.setNumIntento(0);
        requestDetalleDto.setRefModulo("-");
        requestDetalleDto.setRefCampo(null);
        requestDetalleDto.setRefValor(null);
        requestDetalleDto.setRefNota("Reinicio de Intentos");
        requestDetalleDto.setFechaExpiracion(DateUtil.addHours(currentDate,seguridadClienteConfig.getDetalleExpireInHours()));
        trxClient.saveSeguridadClienteDetalle(requestDetalleDto);
    }

    @Override
    public int obtenerIntentosRestantes() {
        if (!seguridadClienteConfig.isEnabled())
            return INTENTOS_ILIMITADOS;

        String ipCliente = getClientIP();
        SeguridadClienteDto seguridadClienteDto = trxClient.getSeguridadClienteByIpCliente(ipCliente);
        boolean existeRegistro = (seguridadClienteDto != null);

        if (!existeRegistro)
            return seguridadClienteConfig.getMaxAttempts();

        int intentosRestantes = (seguridadClienteConfig.getMaxAttempts() - seguridadClienteDto.getIntentos());
        return intentosRestantes;
    }

    private static final String IP_REGEX = "^\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}$";

    @Override
    public String getClientIP() {
        String ipAddress = httpServletRequest.getHeader("X-Real-IP");
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = httpServletRequest.getHeader("X-Forwarded-For");
        }
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = httpServletRequest.getRemoteAddr();
        }
        if (ipAddress != null) {
            String[] ipAddressList = ipAddress.split(",");
            ipAddress = ipAddressList[0].trim();

            if (!ipAddress.matches(IP_REGEX) || ipAddress.length() > 15) {
                log.info("[getClientIP]: Dirección IP No Valida" + ":" + ipAddress);
                //throw new RuntimeException("Dirección IP no valida");
                return ipAddress;
            }
        }
        return ipAddress;
    }
}
