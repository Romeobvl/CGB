package cgb.transfer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import cgb.transfer.dto.TransferRequest;
import cgb.transfer.entity.Account;
import cgb.transfer.entity.Customer;
import cgb.transfer.entity.State;
import cgb.transfer.entity.Transfer;
import cgb.transfer.exception.*;
import cgb.transfer.exception.DeleteTransferException.FailureTransfert;
import cgb.transfer.repository.AccountRepository;
import cgb.transfer.repository.TransferRepository;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * La classe de Service permettant le lien entre Repository et Controller.
 */
@Service
public class TransferService {

	/**
	 * L'instance de Repository de comptes actuellement en cours.
	 */
	@Autowired
	private AccountRepository accountRepository;

	/**
	 * L'instance de Repository de tranferts actuellement en cours.
	 */
	@Autowired
	private TransferRepository transferRepository;


	/**
	 * @param sourceAccountNumber
	 * @param destinationAccountNumber
	 * @param amount
	 * @param transferDate
	 * @param description
	 * @return L'objet transfert qui a été sauvegarder
	 * @throws DateTransferException Si la date est antérieur à la date du jour
	 * @throws AmountTransferException Si le montant du transfert est inferieur ou égal à 0
	 * @throws InvalidAccountTransferException Si le compte n'existe pas
	 * @throws InsufficientFundsTransferException Si le solde du compte source est insuffisant 
	 */
	@Transactional
	public Transfer createTransfer(String sourceAccountNumber, String destinationAccountNumber,
			Double amount, LocalDate transferDate, String description) throws DateTransferException, AmountTransferException, InvalidAccountTransferException, InsufficientFundsTransferException, RecipientAccountTransferException {

		Transfer transfer = new Transfer();
		transfer.setSourceAccountNumber(sourceAccountNumber);
		transfer.setDestinationAccountNumber(destinationAccountNumber);
		transfer.setAmount(amount);
		transfer.setTransferDate(transferDate);
		transfer.setDescription(description);

		Optional<Account> sourceAccount = accountRepository.findById(sourceAccountNumber);
		Optional<Account> destinationAccount = accountRepository.findById(destinationAccountNumber);

		if(sourceAccount.isEmpty()){
			throw new InvalidAccountTransferException("Source");
		}

		if(destinationAccount.isEmpty()){
			throw new InvalidAccountTransferException("Destination");
		}

		if(transferDate.isBefore(LocalDate.now())) {
			throw new DateTransferException();
		}else if (amount <= 0) {
			throw new AmountTransferException();
		}else if (ableToTransfer(sourceAccountNumber, destinationAccountNumber) == false) {
			throw new RecipientAccountTransferException(sourceAccountNumber, destinationAccountNumber);
		}else if (sourceAccount.get().getSolde().compareTo(amount) < 0) {
			throw new InsufficientFundsTransferException();
		} else {
			sourceAccount.get().setSolde(sourceAccount.get().getSolde()-(amount)); 
			destinationAccount.get().setSolde(destinationAccount.get().getSolde()+(amount));

			accountRepository.save(sourceAccount.get());
			accountRepository.save(destinationAccount.get());

			return transferRepository.save(transfer);
		}

	}
	
	
	
	
	@Transactional
	public Transfer createTransferForBatch(String sourceAccountNumber, String destinationAccountNumber,
			Double amount, LocalDate transferDate, String description) {

		Transfer transfer = new Transfer();
		transfer.setSourceAccountNumber(sourceAccountNumber);
		transfer.setDestinationAccountNumber(destinationAccountNumber);
		transfer.setAmount(amount);
		transfer.setTransferDate(transferDate);
		transfer.setDescription(description);
		transfer.setState(State.WAITING.getNom());
		transferRepository.save(transfer);

		Optional<Account> sourceAccount = accountRepository.findById(sourceAccountNumber);
		Optional<Account> destinationAccount = accountRepository.findById(destinationAccountNumber);

		if(sourceAccount.isEmpty()){
			transfer.setState(State.FAILURE.getNom());
			transfer.setStatusReason("Invalid transfer: Source account doesn't exist");
			transferRepository.save(transfer);
			return transfer;
		}

		if(destinationAccount.isEmpty()){
			transfer.setState(State.FAILURE.getNom());
			transfer.setStatusReason("Invalid transfer: Destination account doesn't exist");
			transferRepository.save(transfer);
			return transfer;
		}


		if(transferDate.isBefore(LocalDate.now())) {
			transfer.setState(State.FAILURE.getNom());
			transfer.setStatusReason("Invalid transfer: Date prior to today");
			transferRepository.save(transfer);
			return transfer;
		}else if (amount <= 0) {
			transfer.setState(State.FAILURE.getNom());
			transfer.setStatusReason("Invalid transfer: Negative or null amount");
			transferRepository.save(transfer);
			return transfer;
		}else if (ableToTransfer(sourceAccountNumber, destinationAccountNumber) == false) {
			transfer.setState(State.FAILURE.getNom());
			transfer.setStatusReason("Transfer failed: Account " + destinationAccountNumber + " is not a registered beneficiary of account " + sourceAccountNumber);
			transferRepository.save(transfer);
			return transfer;
		}else if (sourceAccount.get().getSolde().compareTo(amount) < 0) {
			transfer.setState(State.CANCELLED.getNom());
			transfer.setStatusReason("Invalid transfer: Insufficient funds in the source account");
			transferRepository.save(transfer);
			return transfer;
		} else {
			sourceAccount.get().setSolde(sourceAccount.get().getSolde()-(amount)); 
			destinationAccount.get().setSolde(destinationAccount.get().getSolde()+(amount));

			accountRepository.save(sourceAccount.get());
			accountRepository.save(destinationAccount.get());

			transfer.setState(State.SUCCESS.getNom());

			return transferRepository.save(transfer);
		}

	}
	
	
	
	
	

	/**
	 * Fonction transactionnelle de suppression d'un transfert.
	 * 
	 * @param id  L'identifiant unique du transfert que l'on veut supprimer.
	 * @return  Le transfert supprimé si l'opération a réussi, sinon null.
	 * @throws DeleteTransferException
	 */
	@Transactional
	public Transfer deleteTransfer(Long id) throws DeleteTransferException {
		Optional<Transfer> otranfer=transferRepository.findById(id);
		transferRepository.deleteById(id);
		if (otranfer.isEmpty())throw new DeleteTransferException(FailureTransfert.OBJECT_NOT_FOUND); 
		return otranfer.orElse(null);
	}
	
	
	public List<Transfer> getTransferFromBatch(String refLot) {
		return transferRepository.getTransferFromBatch(refLot);
	}
	
	public List<Transfer> findByRefLotAndNotSuccess(String refLot) {
		return transferRepository.findByRefLotAndNotSuccess(refLot);
	}
	
	public List<Transfer> findByDateIntervalAndNotSuccess(LocalDate start, LocalDate end) {
		return transferRepository.findByDateIntervalAndNotSuccess(start, end);
	}
	
	public List<Transfer> findByDestAccountAndNotSuccess(String destinationAccountNumber) {
		return transferRepository.findByDestAccountAndNotSuccess(destinationAccountNumber);
	}
	
	public List<TransferRequest> findByRefLotAndCancelled(String refLot) {
		List<TransferRequest> trq = new ArrayList<TransferRequest>();
		List<Transfer> list = transferRepository.findByRefLotAndCancelled(refLot);
		for (Transfer t : list){
			TransferRequest temp = new TransferRequest();
	        temp.setDestinationAccountNumber(t.getDestinationAccountNumber());
	        temp.setAmount(t.getAmount());
	        temp.setDescription(t.getDescription());
	        trq.add(temp);
		}
		return trq;
	}
	
	public boolean ableToTransfer(String sourceAccountNumber, String destinationAccountNumber) {
	    Account sourceAccount = accountRepository.findById(sourceAccountNumber).orElse(null);
	    Account destinationAccount = accountRepository.findById(destinationAccountNumber).orElse(null);

	    if (sourceAccount == null || destinationAccount == null || destinationAccount.getCustomer() == null) {
	        return false;
	    }
	    
	    Long destinationCustomerId = destinationAccount.getCustomer().getId();

	    for (Customer beneficiary : sourceAccount.getRecipientAccounts()) {
	        if (beneficiary.getId().equals(destinationCustomerId)) {
	            return true;
	        }
	    }

	    return false;
	}
	
	
}


