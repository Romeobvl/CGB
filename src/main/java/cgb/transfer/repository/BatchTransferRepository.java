package cgb.transfer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cgb.transfer.entity.BatchTransfer;

@Repository
public interface BatchTransferRepository extends JpaRepository<BatchTransfer, Long> {

}
