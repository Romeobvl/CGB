package cgb.transfer.service;

import org.springframework.stereotype.Service;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class LogService {

    private static final String NOM_FICHIER = "log.txt";
    // Formatteur pour avoir une date propre dans le fichier log
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * Enregistre un message dans le fichier log.txt avec un timestamp
     */
    public void log(String message) {
        // Le try-with-resources ferme automatiquement fw et pw
        try (FileWriter fw = new FileWriter(NOM_FICHIER, true);
             PrintWriter pw = new PrintWriter(fw)) {
            
            String timestamp = LocalDateTime.now().format(formatter);
            pw.println(timestamp + " | " + message);
            
        } catch (IOException e) {
            // On affiche l'erreur en console si l'écriture fichier échoue
            System.err.println("ERREUR CRITIQUE LOG : " + e.getMessage());
        }
    }
}