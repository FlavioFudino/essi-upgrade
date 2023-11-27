package gob.pe.essalud.trx.repository;

import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface TokenRegistroMyRepository {

    Integer getValidarToken(Map params);

    Integer getExisteTokenActivo(Map params);

}
