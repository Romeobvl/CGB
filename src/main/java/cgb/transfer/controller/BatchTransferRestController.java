package cgb.transfer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import cgb.transfer.dto.BatchTransferRequest;
import cgb.transfer.entity.BatchTransfer;
import cgb.transfer.entity.Transfer;
import cgb.transfer.service.BatchTransferService;
import cgb.transfer.service.TransferService;
import cgb.transfer.exception.*;

import java.time.LocalDate;
import java.util.Optional;

@RestController
@RequestMapping("/api/batch-transfers")
public class BatchTransferRestController {

	@Autowired
	private BatchTransferService batchTransferService;

	@PostMapping("/async")
	public ResponseEntity<?> createBatchTransfer(@RequestBody BatchTransferRequest batchTransferRequest) {

		try {
			BatchTransfer batch = batchTransferService.createBatchTransfer(
					batchTransferRequest.getRefLot(),
					batchTransferRequest.getSourceAccountNumber(),
					batchTransferRequest.getDescriptionLot(),
					batchTransferRequest.getListTransfer());
			return ResponseEntity.ok(batch);
	    } catch (TransferException e) {
	        TransferResponse errorResponse = new TransferResponse("FAILURE", e.getMessage());
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
	    }
	}

}
