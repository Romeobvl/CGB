package cgb.transfer;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import cgb.transfer.entity.Account;
import cgb.transfer.entity.Transfer;
import cgb.transfer.exception.AmountTransferException;
import cgb.transfer.exception.DateTransferException;
import cgb.transfer.exception.DeleteTransferException;
import cgb.transfer.exception.InsufficientFundsTransferException;
import cgb.transfer.exception.InvalidAccountTransferException;
import cgb.transfer.repository.AccountRepository;
import cgb.transfer.repository.TransferRepository;
import cgb.transfer.service.TransferService;

@ExtendWith(MockitoExtension.class)
class TransferServiceUnitTest {

	private static Transfer transfer;
	private static Account srcAccount;
	private static Account destAccount;

	@Mock
	private TransferRepository transferRepository;

	@Mock
	private AccountRepository accountRepository;

	@InjectMocks
	private TransferService transferService;

	@BeforeAll
	public static void init() {
		srcAccount = new Account();
		srcAccount.setAccountNumber("FR5554448575784477474466689");
		srcAccount.setSolde(300.00);

		destAccount = new Account();
		destAccount.setAccountNumber("FR5554448575784477474474989");
		destAccount.setSolde(500.00);

		transfer = new Transfer();
		transfer.setAmount(80.0);
		transfer.setDescription("Test");
		transfer.setSourceAccountNumber("FR5554448575784477474466689");
		transfer.setDestinationAccountNumber("FR5554448575784477474474989");
		transfer.setId(4l);
		transfer.setTransferDate(LocalDate.now());
	}

	@Test
	void testCreateTransfer() throws DateTransferException, AmountTransferException, InvalidAccountTransferException, InsufficientFundsTransferException { 
		
		when(accountRepository.findById("FR5554448575784477474466689"))
		.thenReturn(Optional.of(srcAccount));
		when(accountRepository.findById("FR5554448575784477474474989"))
		.thenReturn(Optional.of(destAccount));

		when(transferRepository.save(Mockito.any(Transfer.class)))
		.thenReturn(transfer);

		Transfer result = transferService.createTransfer("FR5554448575784477474466689", "FR5554448575784477474474989", 80.0, LocalDate.now(), "Test");

		assertEquals("FR5554448575784477474466689", result.getSourceAccountNumber());
		assertEquals("FR5554448575784477474474989", result.getDestinationAccountNumber());
		assertEquals(80.0, result.getAmount());
		assertEquals(LocalDate.now(), result.getTransferDate());
		assertEquals("Test", result.getDescription());
	}
	
	@Test
	void testDeleteTransfer() throws DeleteTransferException {
		
		when(transferRepository.findById(1L))
		.thenReturn(Optional.of(transfer));
		
		Transfer result = transferService.deleteTransfer(1L);
		
		assertEquals("FR5554448575784477474466689", result.getSourceAccountNumber());
		assertEquals("FR5554448575784477474474989", result.getDestinationAccountNumber());
		assertEquals(80.0, result.getAmount());
		assertEquals(LocalDate.now(), result.getTransferDate());
		assertEquals("Test", result.getDescription());

	}
}
