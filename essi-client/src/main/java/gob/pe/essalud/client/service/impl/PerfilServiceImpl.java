package gob.pe.essalud.client.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import gob.pe.essalud.client.base.BaseService;
import gob.pe.essalud.client.client.essi.EssiClient;
import gob.pe.essalud.client.client.essi.model.DataContactoPac;
import gob.pe.essalud.client.client.trx.TrxClient;
import gob.pe.essalud.client.common.constants.Constantes;
import gob.pe.essalud.client.common.constants.EssiCode;
import gob.pe.essalud.client.common.dto.ResponseDto;
import gob.pe.essalud.client.common.util.ArrayUtil;
import gob.pe.essalud.client.common.util.Util;
import gob.pe.essalud.client.components.EssiComponent;
import gob.pe.essalud.client.dto.ContactoDto;
import gob.pe.essalud.client.dto.DireccionDto;
import gob.pe.essalud.client.dto.FichaDto;
import gob.pe.essalud.client.dto.PacienteDto;
import gob.pe.essalud.client.dto.UserSessionDto;
import gob.pe.essalud.client.dto.essi.EssiPacienteRequestDto;
import gob.pe.essalud.client.dto.essi.EssiResponseDto;
import gob.pe.essalud.client.dto.essi.PacienteEssiDto;
import gob.pe.essalud.client.dto.usuario.UsuarioPerfilDto;
import gob.pe.essalud.client.service.PerfilService;
import gob.pe.essalud.client.service.ServiceException;
import gob.pe.essalud.client.service.SessionService;

@Service
public class PerfilServiceImpl extends BaseService implements PerfilService {

    private final EssiClient essiClient;
    //private final BusqActivaClient busqActivaClient;
    private final TrxClient trxClient;
    private final SessionService session;
    private final EssiComponent essiComponent;

    @Autowired
    public PerfilServiceImpl(EssiClient essiClient,
                             TrxClient trxClient,
                             SessionService session,
                             EssiComponent essiComponent) {

        this.essiClient = essiClient;
        this.trxClient = trxClient;
        this.session = session;
        this.essiComponent = essiComponent;
    }

    @Override
    public UsuarioPerfilDto get(String tipoDocumento, String numeroDocumento) {
        this.loggerInfo("get perfil", formatterHour.format(new Date()));

        UserSessionDto user = session.get();

        UsuarioPerfilDto model = trxClient.getPerfil(user.getTipoDocumento(), user.getNumeroDocumento());

        //Traer de ESSI: NumCelular, Correo
        PacienteEssiDto pacienteEssiDto = essiComponent.getPacienteEssi(tipoDocumento, numeroDocumento, model.getFechaNacimiento());
        if (pacienteEssiDto != null) {
            model.getContacto().setNroCelular(pacienteEssiDto.getNumCelular());
            model.getContacto().setEmail(pacienteEssiDto.getEmail());
        }

        /*if (model != null && !StringUtils.isEmpty(user.getNumeroDocumento())) {
            AseguradoRequestDto asegurado = busqActivaClient.getAseguradoByNumero(user.getNumeroDocumento());
            if (asegurado != null) {
                model.setListCondicionSalud(asegurado.getListCondicionSalud());
                model.setCondicionHogar(asegurado.getCondicionHogar());
                model.setListFamiliar(asegurado.getListFamiliar());
            }
        }*/
        return model;
    }

    @Override
    public PacienteDto save(UsuarioPerfilDto perfil) {
        this.loggerInfo("Inicio save perfil", formatterHour.format(new Date()));
        ResponseDto response = trxClient.savePerfil(perfil);

        if (!StringUtils.isEmpty(response.getMessage()))
            throw new ServiceException(response.getMessage());

        boolean isValid = ArrayUtil.allNotEmpty(
                perfil.getTipoDocIdent(),
                perfil.getNumeroDocIdent(),
                perfil.getFechaNacimiento());

        if (isValid) {
            PacienteEssiDto paciente = getPacienteEssi(
                    perfil.getTipoDocIdent(),
                    perfil.getNumeroDocIdent(),
                    perfil.getFechaNacimiento());

            //saveAseguradoBusq(perfil, paciente);
            updateDataContactPacEssi(perfil, paciente.getCodCentro());
        }

        this.loggerInfo("Fin save perfil", formatterHour.format(new Date()));
        return Util.objectToObject(PacienteDto.class, response.getData());
    }

    private void updateDataContactPacEssi(UsuarioPerfilDto perfil, String codCentro) {
        try {
            ContactoDto contact = perfil.getContacto();
            DireccionDto address = perfil.getDireccion();

            DataContactoPac request = new DataContactoPac();
            request.setCodTipDoc(perfil.getTipoDocIdent());
            request.setNumDoc(perfil.getNumeroDocIdent());
            request.setCodCentro(codCentro);

            if (contact != null) {
                request.setNumTelefono(contact.getNroTelefonoFijo());
                request.setNumCelular(contact.getNroCelular());
                request.setCodOpeTel(contact.getOperador());
                request.setEmail(contact.getEmail());
            }

            if (address != null) {
                request.setCodUbigeo(address.getUbigeo());
                request.setCodTipVia(address.getIdTipoVia());
                request.setDireccion(address.getDescripcion());
                request.setDirreferencia(address.getReferencia());
                request.setCoordX(address.getNroLatitud());
                request.setCoordY(address.getNroLongitud());
            }

            EssiResponseDto<Void> response = essiClient.actualizarDatosContactoPac(request);
            if (!response.getCodError().equals(EssiCode.SUCCESS)) {
                loggerError("Actualizar Datos Paciente ESSI", response.toString());
            }

        } catch (Exception ex) {
            loggerException("Actualizar Datos Paciente ESSI", ex);
        }
    }

    @Override
    public FichaDto saveFicha(FichaDto request) {
        /*UsuarioPerfilDto perfil = get(request.getTipoDocIdent(), request.getNumDocIdent());
        if (perfil != null) {
            // Guardar condición de hogar
            CondicionHogarDto condicionHogarUpdated = request.getCondicionHogar();
            if (condicionHogarUpdated != null) {
                CondicionHogarRegisterDto condicionHogar = new CondicionHogarRegisterDto();
                condicionHogar.setTipoDocIdent(request.getTipoDocIdent());
                condicionHogar.setNumeroDocIdent(request.getNumDocIdent());
                condicionHogar.setCantAmbientes(condicionHogarUpdated.getCantAmbientes());
                condicionHogar.setIndAgua(condicionHogarUpdated.getIndAgua());
                condicionHogar.setIndSshh(condicionHogarUpdated.getIndSshh());
                busqActivaClient.saveCondicion(condicionHogar);
            }
            CondicionSaludRegisterDto condicionSaludRegister = new CondicionSaludRegisterDto();
            condicionSaludRegister.setTipoDocIdent(request.getTipoDocIdent());
            condicionSaludRegister.setNumeroDocIdent(request.getNumDocIdent());
            condicionSaludRegister.setFechaNacimiento(perfil.getFechaNacimiento());
            condicionSaludRegister.setCondicionSaludList(request.getListCondicionSalud());
            busqActivaClient.saveCondicionSalud(condicionSaludRegister);
            // Guardar datos de contacto
            DireccionDto addressOriginal = perfil.getDireccion();
            DireccionDto addressUpdated = request.getDireccion();
            if (addressOriginal != null && addressUpdated != null) {
                addressOriginal.setUbigeo(addressUpdated.getUbigeo());
                addressOriginal.setDescripcion(addressUpdated.getDescripcion());
                addressOriginal.setReferencia(addressUpdated.getReferencia());
            }
            save(perfil);
        } else {
            throw new ServiceException("Usuario no registrado");
        }
        return request;*/
        return null;
    }

    private void saveAseguradoBusq(UsuarioPerfilDto perfil, PacienteEssiDto paciente) {
        /*AseguradoRequestDto asegurado = new AseguradoRequestDto();
        asegurado.setContacto(perfil.getContacto());
        asegurado.setDireccion(perfil.getDireccion());
        asegurado.setTipoDocIdent(perfil.getTipoDocIdent());
        asegurado.setNumeroDocIdent(perfil.getNumeroDocIdent());
        asegurado.setPrimerNombre(paciente.getPriNombre());
        asegurado.setSegundoNombre(paciente.getSegNombre());
        asegurado.setApellidoPaterno(paciente.getApePaterno());
        asegurado.setApellidoMaterno(paciente.getApeMaterno());
        asegurado.setFechaNacimiento(paciente.getFecNac());
        asegurado.setCodSexo(paciente.getCodGenero());
        boolean indPadomi = perfil.getIndPadomi() == null ? false : perfil.getIndPadomi();
        asegurado.setIndPadomi(indPadomi);
        asegurado.setListCondicionSalud(perfil.getListCondicionSalud());
        busqActivaClient.saveAseguradoAndSendEssiPaciente(asegurado);*/
    }

    private PacienteEssiDto getPacienteEssi(String tipoNumDocumento,
                                            String numDocumento,
                                            String fechaNacimiento) {
        PacienteEssiDto paciente = null;
        EssiPacienteRequestDto request = new EssiPacienteRequestDto();
        request.setCodOpcion(Constantes.COD_OPCION);
        request.setCodTipDoc(tipoNumDocumento);
        request.setNumDoc(numDocumento);
        request.setFecNacimiento(fechaNacimiento);

        EssiResponseDto<PacienteEssiDto[]> essiResp = essiClient.loginMovil(request);
        PacienteEssiDto[] result = essiResp.getvDataItem();
        if (result != null && result.length > 0) {
            paciente = result[0];
        } else {
            String message = "T. Doc.: " + tipoNumDocumento + ", DNI: " + numDocumento +
                    ", Fecha Nacimiento: " + fechaNacimiento;
            loggerError("No se encontró datos en ESSI", message);
        }
        return paciente;
    }
}
