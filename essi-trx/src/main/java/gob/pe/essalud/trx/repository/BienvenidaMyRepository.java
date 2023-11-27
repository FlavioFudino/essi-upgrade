package gob.pe.essalud.trx.repository;

import gob.pe.essalud.trx.dto.BienvenidaListDto;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BienvenidaMyRepository {
    List<BienvenidaListDto> getBienvenidaList();
}
