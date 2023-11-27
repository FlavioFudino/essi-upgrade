package gob.pe.essalud.trx.jpa.repository;

import gob.pe.essalud.trx.jpa.model.ContactoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ContactoRepository extends JpaRepository<ContactoModel, Integer> {
    ContactoModel findTopByIdPersona(Integer idPersona);

    Optional<ContactoModel> findByIdPersona(int idPersona);
}
