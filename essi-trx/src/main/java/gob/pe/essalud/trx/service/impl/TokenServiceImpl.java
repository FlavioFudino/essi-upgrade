package gob.pe.essalud.trx.service.impl;

import gob.pe.essalud.trx.base.BaseService;
import gob.pe.essalud.trx.dto.NotificacionTokenDto;
import gob.pe.essalud.trx.dto.PacienteTokenDto;
import gob.pe.essalud.trx.repository.TokenRepository;
import gob.pe.essalud.trx.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class TokenServiceImpl extends BaseService implements TokenService {

    private final TokenRepository tokenRepository;

    @Autowired
    public TokenServiceImpl(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    @Transactional
    public void sendToken(PacienteTokenDto pacienteTokenDto) {
        String token = tokenRepository.getTokenUsuario(pacienteTokenDto);
        if (token != null) {
            tokenRepository.updTokenUsuario(pacienteTokenDto);
        } else {
            tokenRepository.insTokenUsuario(pacienteTokenDto);
        }
    }

    @Override
    public List<NotificacionTokenDto> notificacionList() {
        return tokenRepository.notificacionList();
    }

}
