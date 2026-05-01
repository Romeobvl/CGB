package cgb.transfer.exemple;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cgb.transfer.entity.Account;
import cgb.transfer.entity.Customer;
import cgb.transfer.entity.Role;
import cgb.transfer.entity.UserCGB;
import cgb.transfer.repository.AccountRepository;
import cgb.transfer.repository.CustomerRepository;
import cgb.transfer.repository.UserCGBRepository;
import cgb.transfer.security.service.MyUserDetailsService;
import cgb.utils.IbanGenerator;
import jakarta.annotation.PostConstruct;

/**
 * Classe responsable de l'initialisation des données des comptes dans la base H2.
 */
@Component
public class DatabaseInitializer {

	/*
	 * 	@Autowired
	 * 	private final AccountRepository accountRepository;
	 * 
	 * Possibilité de faire une injection par l'attribut, mais il est recommander 
	 * de la faire par constructeur comme présenté ci-dessous
	 * 
	*/
    
	private final AccountRepository accountRepository;
	private final CustomerRepository customerRepository;
	private final UserCGBRepository userRepository;
	private final MyUserDetailsService userService;
	
    @Autowired
    public DatabaseInitializer(AccountRepository accountRepository, CustomerRepository customerRepository, UserCGBRepository userRepository, MyUserDetailsService userService) {
        this.accountRepository = accountRepository;
        this.customerRepository = customerRepository;
        this.userRepository = userRepository;
        this.userService = userService;
    }

	@PostConstruct
    public void init() {
        // Vérifiez si la base de données est vide avant d'insérer des données
        if (accountRepository.count() == 0) {
           insertSampleData(accountRepository, customerRepository, userService);
        }
    }

    public static void insertSampleDataOld(AccountRepository accountRepository, CustomerRepository customerRepository, UserCGBRepository userRepository) {
        // Insérer des comptes d'exemple
        Account account1 = new Account();
        account1.setAccountNumber("123456789");
        account1.setSolde(300.00);
        accountRepository.save(account1);

        Account account2 = new Account();
        account2.setAccountNumber("987654321");
        account2.setSolde(500.00);
        accountRepository.save(account2);

        Account account3 = new Account();
        account3.setAccountNumber("456789123");
        account3.setSolde(2000.00);
        accountRepository.save(account3);
    }
    
    /**
     * Fonction de valorisation de la base appellée si cette dernière est vide.
     * @param accountRepository  L'instance de Repository actuellement utilisée.
     */
public static void insertSampleData(AccountRepository accountRepository, CustomerRepository customerRepository, MyUserDetailsService userService) {
        
        Customer customerGSB = new Customer();
        customerGSB.setId(1L);
        customerGSB.setName("GSB");
        customerGSB.setAddress("123 Rue, La Ciotat");
        customerGSB.setLEI("969500A4D59I4L5P4V88");
        customerRepository.save(customerGSB);

        Customer customerGCORP = new Customer();
        customerGCORP.setId(2L);
        customerGCORP.setName("GCORP");
        customerGCORP.setAddress("45 Avenue, Paris");
        customerGCORP.setLEI("5493006MHB84DD0ZWW18");
        customerRepository.save(customerGCORP);

        UserCGB userGsb1 = new UserCGB();
        userGsb1.setId(1L);
        userGsb1.setUsername("utilisateur1");
        userGsb1.setPassword("P@ssw0rdUTILISATEUR");
        userGsb1.setEmail("utilisateur@gsb.fr");
        userGsb1.setRole(Role.USER);
        userGsb1.setCustomer(customerGSB);
        userService.registerUser(userGsb1);

        UserCGB admin = new UserCGB();
        admin.setId(2L);
        admin.setUsername("admin1");
        admin.setPassword("P@ssw0rdADMIN");
        admin.setEmail("admin@gsb.fr");
        admin.setRole(Role.ADMIN);
        admin.setCustomer(customerGSB);
        userService.registerUser(admin);

        UserCGB comptableGCORP = new UserCGB();
        comptableGCORP.setId(3L);
        comptableGCORP.setUsername("comptable2");
        comptableGCORP.setPassword("P@ssw0rdCOMPTABLE");
        comptableGCORP.setEmail("comptabilite@gcorp.com");
        comptableGCORP.setRole(Role.COMPTABLE);
        comptableGCORP.setCustomer(customerGCORP);
        userService.registerUser(comptableGCORP);

        UserCGB comptableGSB = new UserCGB();
        comptableGSB.setId(4L);
        comptableGSB.setUsername("comptable1");
        comptableGSB.setPassword("P@ssw0rdCOMPTABLE");
        comptableGSB.setEmail("comptabilite@gsb.com");
        comptableGSB.setRole(Role.COMPTABLE);
        comptableGSB.setCustomer(customerGSB);
        userService.registerUser(comptableGSB);

        
        Account account1 = new Account();
        account1.setAccountNumber(IbanGenerator.generateValidIban());
        account1.setSolde(300.00);
        account1.setCustomer(customerGSB);
        account1.addCustomer(customerGCORP); 
        account1.addCustomer(customerGSB);   
        accountRepository.save(account1);

        Account account2 = new Account();
        account2.setAccountNumber(IbanGenerator.generateValidIban());
        account2.setSolde(500.00);
        account2.setCustomer(customerGSB);
        account2.addCustomer(customerGCORP); 
        accountRepository.save(account2);

        Account account3 = new Account();
        account3.setAccountNumber(IbanGenerator.generateValidIban());
        account3.setSolde(2000.00);
        account3.setCustomer(customerGSB);
        accountRepository.save(account3);
        
        Account account4 = new Account();
        account4.setAccountNumber(IbanGenerator.generateValidIban());
        account4.setSolde(1000.00);
        account4.setCustomer(customerGSB);
        accountRepository.save(account4);
        
        Account account5 = new Account();
        account5.setAccountNumber(IbanGenerator.generateValidIban());
        account5.setSolde(1000.00);
        account5.setCustomer(customerGSB);
        accountRepository.save(account5);
        
        Account account6 = new Account();
        account6.setAccountNumber(IbanGenerator.generateValidIban());
        account6.setSolde(1000.00);
        account6.setCustomer(customerGSB);
        accountRepository.save(account6);
        
        Account account7 = new Account();
        account7.setAccountNumber(IbanGenerator.generateValidIban());
        account7.setSolde(1000.00);
        account7.setCustomer(customerGSB);
        accountRepository.save(account7);
        
        Account account8 = new Account();
        account8.setAccountNumber(IbanGenerator.generateValidIban());
        account8.setSolde(1000.00);
        account8.setCustomer(customerGSB);
        accountRepository.save(account8);

        Account account9 = new Account();
        account9.setAccountNumber(IbanGenerator.generateValidIban());
        account9.setSolde(1500.00);
        account9.setCustomer(customerGSB);
        accountRepository.save(account9);
        
        Account account10 = new Account();
        account10.setAccountNumber(IbanGenerator.generateValidIban());
        account10.setSolde(750.00);
        account10.setCustomer(customerGSB);
        accountRepository.save(account10);
        
        Account account11 = new Account();
        account11.setAccountNumber(IbanGenerator.generateValidIban());
        account11.setSolde(3200.00);
        account11.setCustomer(customerGCORP);
        account11.addCustomer(customerGSB);
        account11.addCustomer(customerGCORP);
        accountRepository.save(account11);
        
        Account account12 = new Account();
        account12.setAccountNumber(IbanGenerator.generateValidIban());
        account12.setSolde(450.00);
        account12.setCustomer(customerGCORP);
        account12.addCustomer(customerGSB);
        accountRepository.save(account12);
        
        Account account13 = new Account();
        account13.setAccountNumber(IbanGenerator.generateValidIban());
        account13.setSolde(2800.00);
        account13.setCustomer(customerGCORP);
        accountRepository.save(account13);
        
        Account account14 = new Account();
        account14.setAccountNumber(IbanGenerator.generateValidIban());
        account14.setSolde(1250.00);
        account14.setCustomer(customerGCORP);
        accountRepository.save(account14);
        
        Account account15 = new Account();
        account15.setAccountNumber(IbanGenerator.generateValidIban());
        account15.setSolde(800.00);
        account15.setCustomer(customerGCORP);
        accountRepository.save(account15);
        
        Account account16 = new Account();
        account16.setAccountNumber(IbanGenerator.generateValidIban());
        account16.setSolde(2100.00);
        account16.setCustomer(customerGCORP);
        accountRepository.save(account16);
        
        Account account17 = new Account();
        account17.setAccountNumber(IbanGenerator.generateValidIban());
        account17.setSolde(950.00);
        account17.setCustomer(customerGCORP);
        accountRepository.save(account17);
        
        Account account18 = new Account();
        account18.setAccountNumber(IbanGenerator.generateValidIban());
        account18.setSolde(3700.00);
        account18.setCustomer(customerGCORP);
        accountRepository.save(account18);
        
        Account account19 = new Account();
        account19.setAccountNumber(IbanGenerator.generateValidIban());
        account19.setSolde(600.00);
        account19.setCustomer(customerGCORP);
        accountRepository.save(account19);
        
        Account account20 = new Account();
        account20.setAccountNumber(IbanGenerator.generateValidIban());
        account20.setSolde(4300.00);
        account20.setCustomer(customerGCORP);
        accountRepository.save(account20);
     }
}