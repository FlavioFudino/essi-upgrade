package gob.pe.essalud.trx.service;

import gob.pe.essalud.trx.dto.ClaveChangeRequestDto;
import gob.pe.essalud.trx.dto.ClaveRecoveryRequestDto;
import gob.pe.essalud.trx.dto.ClaveRecoveryResponseDto;

public interface ClaveService {
    ClaveRecoveryResponseDto recovery(ClaveRecoveryRequestDto model);

    ClaveRecoveryResponseDto save(ClaveChangeRequestDto model);
}
