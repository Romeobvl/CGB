package cgb.transfer.exemple;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cgb.transfer.entity.Account;
import cgb.transfer.repository.AccountRepository;
import jakarta.annotation.PostConstruct;

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
	
    @Autowired
    public DatabaseInitializer(AccountRepository accountRepository) {
		//super();
		this.accountRepository = accountRepository;
	}

	@PostConstruct
    public void init() {
        // Vérifiez si la base de données est vide avant d'insérer des données
        if (accountRepository.count() == 0) {
           insertSampleData(accountRepository);
        }
    }

    public static void insertSampleDataOld(AccountRepository accountRepository) {
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
    
    public static void insertSampleData(AccountRepository accountRepository) {
        // Insérer des comptes d'exemple
        Account account1 = new Account();
        account1.setAccountNumber("123456789");
        account1.setSolde(300.00);
        accountRepository.save(account1);

        Account account2 = new Account();
        account2.setAccountNumber("234567891");
        account2.setSolde(500.00);
        accountRepository.save(account2);

        Account account3 = new Account();
        account3.setAccountNumber("345678912");
        account3.setSolde(2000.00);
        accountRepository.save(account3);
        
        Account account4 = new Account();
        account4.setAccountNumber("456789123");
        account4.setSolde(1000.00);
        accountRepository.save(account4);
        
        Account account5 = new Account();
        account5.setAccountNumber("567891234");
        account5.setSolde(1000.00);
        accountRepository.save(account5);
        
        Account account6 = new Account();
        account6.setAccountNumber("678912345");
        account6.setSolde(1000.00);
        accountRepository.save(account6);
        
        Account account7 = new Account();
        account7.setAccountNumber("789123456");
        account7.setSolde(1000.00);
        accountRepository.save(account7);
        
        Account account8 = new Account();
        account8.setAccountNumber("891234568");
        account8.setSolde(1000.00);
        accountRepository.save(account8);
        
        Account account9 = new Account();
        account9.setAccountNumber("912345679");
        account9.setSolde(1500.00);
        accountRepository.save(account9);
        
        Account account10 = new Account();
        account10.setAccountNumber("123456780");
        account10.setSolde(750.00);
        accountRepository.save(account10);
        
        Account account11 = new Account();
        account11.setAccountNumber("234567892");
        account11.setSolde(3200.00);
        accountRepository.save(account11);
        
        Account account12 = new Account();
        account12.setAccountNumber("345678913");
        account12.setSolde(450.00);
        accountRepository.save(account12);
        
        Account account13 = new Account();
        account13.setAccountNumber("456789124");
        account13.setSolde(2800.00);
        accountRepository.save(account13);
        
        Account account14 = new Account();
        account14.setAccountNumber("567891235");
        account14.setSolde(1250.00);
        accountRepository.save(account14);
        
        Account account15 = new Account();
        account15.setAccountNumber("678912346");
        account15.setSolde(800.00);
        accountRepository.save(account15);
        
        Account account16 = new Account();
        account16.setAccountNumber("789123457");
        account16.setSolde(2100.00);
        accountRepository.save(account16);
        
        Account account17 = new Account();
        account17.setAccountNumber("891234569");
        account17.setSolde(950.00);
        accountRepository.save(account17);
        
        Account account18 = new Account();
        account18.setAccountNumber("912345671");
        account18.setSolde(3700.00);
        accountRepository.save(account18);
        
        Account account19 = new Account();
        account19.setAccountNumber("123456781");
        account19.setSolde(600.00);
        accountRepository.save(account19);
        
        Account account20 = new Account();
        account20.setAccountNumber("234567893");
        account20.setSolde(4300.00);
        accountRepository.save(account20);
    }
}
