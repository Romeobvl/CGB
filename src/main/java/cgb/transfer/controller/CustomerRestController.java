package cgb.transfer.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import cgb.transfer.dto.BatchTransferRequest;
import cgb.transfer.dto.TransferRequest;
import cgb.transfer.entity.Account;
import cgb.transfer.entity.BatchTransfer;
import cgb.transfer.entity.Transfer;
import cgb.transfer.service.BatchTransferService;
import cgb.transfer.service.CustomerService;
import cgb.transfer.service.TransferService;
import cgb.transfer.exception.*;
import cgb.transfer.repository.AccountHistoricalRepository;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/reciepient")
public class CustomerRestController {

	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private AccountHistoricalService accountHistoricalService;


	@PostMapping("/add")
	public ResponseEntity<?> ajouterBeneficiaire(@RequestBody List<Account> listAcc) {


		Map<String, Object> response = new HashMap<>();
		response.put("successCount", ajouterBeneficiaire(listAcc)); 
		for (Account acc: listAcc) {
			response.put("IBAN", acc.getAccountNumber());
			response.put("Status", accountHistoricalService.getLastUpdate(acc.getAccountNumber());
		}
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
	}


}


