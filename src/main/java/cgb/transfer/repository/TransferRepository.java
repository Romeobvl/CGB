package cgb.transfer.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import cgb.transfer.entity.Transfer;

/**
 * Classe représentant la table des tranferts dans la DB.
 * Possiblité de rajouter des requêtes comme vu en cours.
 */
@Repository
public interface TransferRepository extends JpaRepository<Transfer, Long> {
	@Query("SELECT t FROM Transfer t JOIN BatchTransfer b ON t.batch = b WHERE b.refLot = :refLot")
	public List<Transfer> getTransferFromBatch(@Param("refLot") String refLot);
}