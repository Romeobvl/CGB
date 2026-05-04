
package cgb.transfer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import cgb.transfer.dto.BatchTransferRequest;
import cgb.transfer.dto.TransferRequest;
import cgb.transfer.entity.Account;
import cgb.transfer.entity.AccountHistorical;
import cgb.transfer.entity.BatchTransfer;
import cgb.transfer.entity.Customer;
import cgb.transfer.entity.State;
import cgb.transfer.entity.Transfer;
import cgb.transfer.exception.*;
import cgb.transfer.repository.AccountHistoricalRepository;
import cgb.transfer.repository.AccountRepository;
import cgb.transfer.repository.BatchTransferRepository;
import cgb.transfer.repository.CustomerRepository;
import cgb.transfer.repository.TransferRepository;
import cgb.utils.CGBIbanValidator;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
	private AccountHistoricalRepository accountHistoricalRepository;

	@Autowired
    private AccountHistorical historical;
	
	@Autowired
    private LogService logger;
	
	CGBIbanValidator cgbIbanValidator = CGBIbanValidator.getInstanceValidator();
	
	@Transactional
	public int ajouterBeneficiaire(List<Account> listAcc) {
		int successCount = 0;
		String action = "add";
		String status;
		//user token
		
		Customer customer = new Customer();
		
		List<Account> listExistante = customer.getList();
		
		for (Account acc: listAcc) {
				if (cgbIbanValidator.isIbanValidWE(acc.getAccountNumber())) {
					if (accountRepository.findByAccountNumber(acc.getAccountNumber()) != null) {
						if (listExistante.contains(acc)) {
							successCount += 1;
							customer.add(acc);
							
							status ="Success";
							
							customerRepository.save(customer);
						} else {
							status ="Failed already exist";
						}
					} else {
						status ="Failed account doesn't exist";
					}
				} else {
					status ="Failed Iban structure not valid";
				}
				setLog(action, status, customer, acc);
		}
		return successCount;
	}

	private void setLog(String action, String status, Customer customer, Account acc) {
		historical.setAccountNumber(acc.getAccountNumber());
		historical.setDate(LocalDate.now());
		historical.setAction(action);
		historical.setStatus(status);
		accountHistoricalRepository.save(historical);
		
		
		logger.log(status + acc.getAccountNumber() + " to Customer :" + customer.getName());
	}
	
	
	
}
