package be.kapture.hl7sender.gui;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class Hl7App extends Application{
    private ConfigurableApplicationContext springContext;
    private Parent rootNode;
    private FXMLLoader fxmlLoader;

    public static void main(String[] args) {
        launch(args);
        //SpringApplication.run(Hl7App.class, args);
    }

    @Override
    public void init() throws Exception {
        springContext = SpringApplication.run(Hl7App.class);
        fxmlLoader = new FXMLLoader();
        fxmlLoader.setControllerFactory(springContext::getBean);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        fxmlLoader.setLocation(getClass().getResource("/fxml/view.fxml"));
        rootNode = fxmlLoader.load();

        primaryStage.setTitle("Hello World");
        Scene scene = new Scene(rootNode, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void stop() {
        springContext.stop();
    }

    /*@Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {
                System.out.println("Hello World");
            }
        };
    }*/
}

