package cgb.transfer.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import cgb.transfer.dto.BatchTransferRequest;
import cgb.transfer.dto.TransferRequest;
import cgb.transfer.entity.BatchTransfer;
import cgb.transfer.entity.Transfer;
import cgb.transfer.service.BatchTransferService;
import cgb.transfer.service.TransferService;
import cgb.transfer.exception.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/batch-transfers")
public class BatchTransferRestController {

	@Autowired
	private BatchTransferService batchTransferService;
	
	@Autowired
	private TransferService transferService;

	@PostMapping("/async")
	public ResponseEntity<?> createBatchTransfer(@RequestBody BatchTransferRequest batchTransferRequest) throws InvalidAccountTransferException {
		try {
		
		String numLot = batchTransferService.RefLotDuBatch();
		
		batchTransferService.createBatchTransfer(
				batchTransferRequest.getSourceAccountNumber(),
				batchTransferRequest.getDescriptionLot(),
				batchTransferRequest.getListTransfer());


		Map<String, Object> response = new HashMap<>();
		response.put("numLot", numLot); 
		response.put("dateLancement", LocalDate.now().toString());
		response.put("message", "Traitement Lancé");
		response.put("etat", "received");

		return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
		
		} catch (TransferException e) {
			TransferResponse errorResponse = new TransferResponse("FAILURE", e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
		}
	}
	
	@GetMapping("/{refLot}")
	public ResponseEntity<?> getTransfer(@PathVariable String refLot) {
		BatchTransfer batch = batchTransferService.findBatchByRefLot(refLot);
		List<Transfer> list = transferService.getTransferFromBatch(refLot);
		batch.setListTransfer(list);
		
		return ResponseEntity.ok(batch);
	}
	
	@GetMapping("/replay/{refLot}")
	public ResponseEntity<?> getCancelledTransfer(@PathVariable String refLot) {
		BatchTransferRequest batch = batchTransferService.findBatchByRefLotReplay(refLot);
		List<TransferRequest> list = transferService.findByRefLotAndCancelled(refLot);
		batch.setListTransfer(list);
		
		
		return ResponseEntity.ok(batch);
	}


}


