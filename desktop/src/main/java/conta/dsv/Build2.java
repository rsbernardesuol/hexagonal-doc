package conta.dsv;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

// Responsável por configurar os serviços do spring
@Configuration
@ComponentScan({
        // adptadores front-end javafx
        "conta.dsv",
        "conta.tela",
        // core do sistema
        "conta.sistema",
        // adptadores falsos
        "conta.adaptador"})
public class Build2 {
    // Build 2 - Adaptador JavaFX -> Sistema <- Adaptadores Mocks
}
