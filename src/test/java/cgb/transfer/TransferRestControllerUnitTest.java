package cgb.transfer;

import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;

import cgb.transfer.controller.TransferRestController;
import cgb.transfer.entity.Transfer;
import cgb.transfer.service.TransferService;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(TransferRestController.class)
class TransferRestControllerUnitTest {

	private static Transfer transfer;
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockitoBean
	private TransferService transferService;


	@BeforeAll
	public static void init() {
		transfer = new Transfer();
		transfer.setAmount(80.0);
		transfer.setDescription("Test");
		transfer.setSourceAccountNumber("FR5554448575784477474466689");
		transfer.setDestinationAccountNumber("FR5554448575784477474474989");
		transfer.setId(4l);
		transfer.setTransferDate(LocalDate.now());
	}
	
	@Test
	void testCreateTransfer() throws Exception{ 
		
		when(transferService.createTransfer(
				Mockito.any(String.class),
				Mockito.any(String.class),
				Mockito.any(Double.class),
				Mockito.any(LocalDate.class),
				Mockito.any(String.class))).thenReturn(transfer);

		mockMvc.perform(post("/api/transfers").contentType(MediaType.APPLICATION_JSON).content(asJsonString(transfer)))
		.andExpect(status().isOk())
		.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());
	}
	
	public static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().findAndRegisterModules().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	@Test
	void testDeleteTransfer() throws Exception {
		when(transferService.deleteTransfer(Mockito.anyLong())).thenReturn(transfer);
	    Long id = 7L;
	    mockMvc.perform(delete("/api/transfers")
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(String.valueOf(id)))
	            .andExpect(status().isOk())
	            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
	            .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("SUCCESS"))
	            .andExpect(MockMvcResultMatchers.jsonPath("$.message").exists());

	}
	
}
