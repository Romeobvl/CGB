package cgb.utils;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

public class CGBIdanValidatorTest {
	
	public static CGBIbanValidator ibanValidator;

	@BeforeAll
	public static void oneTimeSetUp() {
		ibanValidator = CGBIbanValidator.getInstanceValidator();
		System.out.println("BeforeAll - CGBIbanValidatorTest");
	}
	
	@Test
	public void testGetInstance() {
		assertNotNull(ibanValidator);
		assertInstanceOf(CGBIbanValidator.class, ibanValidator);
	}
	
	@Test
	public void testIsIbanStructureValid() {
		assertInstanceOf(Boolean.class, ibanValidator.isIbanStructureValid("FR7712694732976829990161303"));
		assertTrue(ibanValidator.isIbanStructureValid("FR7712694732976829990161303"));
		assertFalse(ibanValidator.isIbanStructureValid("FR77126945515732976829990161303"));
		assertFalse(ibanValidator.isIbanStructureValid("FR771269455157329768299930"));
		assertFalse(ibanValidator.isIbanStructureValid("test"));
		assertFalse(ibanValidator.isIbanStructureValid("FR77126945515732976829990AZE303"));
	}
	
	@Test
	public void testIsIbanValid() {
		assertInstanceOf(Boolean.class, ibanValidator.isIbanValid("FR7712694732976829990161303"));
		assertTrue(ibanValidator.isIbanValid("FR7712694732976829990161303"));
		assertFalse(ibanValidator.isIbanValid("FR77126945515732976829990161303"));
		assertFalse(ibanValidator.isIbanValid("FR771269455157329768299930"));
		assertFalse(ibanValidator.isIbanValid("test"));
		assertFalse(ibanValidator.isIbanValid("FR77126945515732976829990AZE303"));
	}
	
	@Test
	public void testGetCountryCode() {
		assertInstanceOf(String.class, ibanValidator.getCountryCode("FR7712694732976829990161303"));
		assertEquals("FR", ibanValidator.getCountryCode("FR7712694732976829990161303"));
		assertNotEquals("Test", ibanValidator.getCountryCode("FR7712694732976829990161303"));
	}
	
	@Test
	public void testGetCheckDigits() {
		assertInstanceOf(String.class, ibanValidator.getCheckDigits("FR7712694732976829990161303"));
		assertEquals("77", ibanValidator.getCheckDigits("FR7712694732976829990161303"));
		assertNotEquals("88", ibanValidator.getCheckDigits("FR7712694732976829990161303"));
		assertNotEquals("Nn", ibanValidator.getCheckDigits("FR7712694732976829990161303"));
	}
	
	@Test
	public void testGetBBAN() {
		assertInstanceOf(String.class, ibanValidator.getBBAN("FR7712694732976829990161303"));
		assertEquals("12694732976829990161303", ibanValidator.getBBAN("FR7712694732976829990161303"));
		assertNotEquals("126947329768299901613038674", ibanValidator.getBBAN("FR7712694732976829990161303"));
		assertNotEquals("Nnxrfjugdikgnfsdq", ibanValidator.getBBAN("FR7712694732976829990161303"));
		assertNotEquals("11613038674", ibanValidator.getBBAN("FR7712694732976829990161303"));
	}
}
