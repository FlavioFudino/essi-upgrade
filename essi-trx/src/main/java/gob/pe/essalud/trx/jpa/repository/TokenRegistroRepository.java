package gob.pe.essalud.trx.jpa.repository;

import gob.pe.essalud.trx.jpa.model.TokenRegistroModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenRegistroRepository extends JpaRepository<TokenRegistroModel, Long> {
    Optional<TokenRegistroModel> findTopByTokenAndTipoOrderByDateCreateDesc(String token, Integer tipo);

    Optional<TokenRegistroModel> findTopByTokenAndTipoAndCorreoOrderByIdTokenRegistroDesc(String token, Integer tipo, String correo);
}
