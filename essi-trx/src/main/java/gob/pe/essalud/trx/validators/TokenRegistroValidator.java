package gob.pe.essalud.trx.validators;

import gob.pe.essalud.trx.dto.TokenRegistroDto;
import gob.pe.essalud.trx.dto.TokenRegistroRequestDto;

public interface TokenRegistroValidator {
    void validate(TokenRegistroRequestDto request);

    TokenRegistroDto validateActivation(TokenRegistroRequestDto request, Boolean validUser);
}
