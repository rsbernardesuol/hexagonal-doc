package conta.tela;

import conta.prd.Build4;
import javafx.application.Application;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

// Responsável por fazer o ponto de inicio de execução
public class AdaptadorJavaFx extends Application {

    private ApplicationContext spring;

    @Override
    public void init() {
        System.out.println("iniciando spring..");
        //spring = new AnnotationConfigApplicationContext(Build2.class);
        //spring = new AnnotationConfigApplicationContext(Build3.class);
        spring = new AnnotationConfigApplicationContext(Build4.class);
    }

    @Override
    public void start(Stage stage) {
        var form = spring.getBean(TransferenciaFrm.class);
        form.mostrar(stage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
