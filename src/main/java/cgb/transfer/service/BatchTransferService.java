
package cgb.transfer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import cgb.transfer.dto.TransferRequest;
import cgb.transfer.entity.Account;
import cgb.transfer.entity.BatchTransfer;
import cgb.transfer.entity.Transfer;
import cgb.transfer.exception.*;
import cgb.transfer.exception.DeleteTransferException.FailureTransfert;
import cgb.transfer.repository.AccountRepository;
import cgb.transfer.repository.BatchTransferRepository;
import cgb.transfer.repository.TransferRepository;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class BatchTransferService {

    @Autowired
    private AccountRepository accountRepository;
    
    @Autowired
    private BatchTransferRepository batchTransferRepository;
    
    @Autowired
    private TransferRepository transferRepository;
    
    @Autowired
    private TransferService transferService;
    
    @Async
    @Transactional
    public BatchTransfer createBatchTransfer(String refLot, String sourceAccountNumber, String descriptionLot, List<TransferRequest> listTransfer) throws InvalidAccountTransferException {
    	  if (!accountRepository.findById(sourceAccountNumber).isPresent()) {
    		  throw new InvalidAccountTransferException("Source");
    	  }
  				
    	  BatchTransfer batch = new BatchTransfer();
    	  batch.setRefLot(refLot);
    	  batch.setDescriptionLot(descriptionLot);
    	  batch.setSourceAccountNumber(sourceAccountNumber);
    	  batch.setDate(LocalDate.now());
    	  batchTransferRepository.save(batch);
    	  
    	  for (TransferRequest transferRequest: listTransfer) {
    		Transfer transfer = transferService.createTransferForBatch(sourceAccountNumber, transferRequest.getDestinationAccountNumber(), transferRequest.getAmount(), LocalDate.now(), transferRequest.getDescription());
    		transfer.setBatch(batch);
    		batch.addTransfer(transfer);
    		transferRepository.save(transfer);
    		batchTransferRepository.save(batch);
    	  }
    	
    	return batchTransferRepository.save(batch);
    }
}
