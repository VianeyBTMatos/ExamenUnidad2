package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Main extends Application {
    private Label label = new Label();
    private Label label2 = new Label();
    private Button botonCalcular = new Button("Calcular");
    private Button botonIniciar = new Button("Iniciar");
    private Boolean banderaFinal = false;
    @Override
    public void start(Stage primaryStage) throws Exception{
        BorderPane border = new BorderPane();
        primaryStage.setTitle("EJERCICIO 2 UNIDAD 2");

        GridPane gridp = addGrindPane();
        border.setCenter(gridp);

        botonCalcular.setDisable(true);
        this.botonIniciar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                iniciarHilo();
            }
        });

        primaryStage.setScene(new Scene(border, 300, 275));
        primaryStage.show();
    }
    public void iniciarHilo (){
        Runnable task = new Runnable() {
            @Override
            public void run() {
                correrHilo();
            }
        };
        // Run the task in a background thread
        Thread backgroundThread = new Thread(task);
        // Terminate the running thread if the application exits
        backgroundThread.setDaemon(true);
        // Start the thread
        backgroundThread.start();
    }
    public void correrHilo(){
        botonIniciar.setDisable(true);
        botonCalcular.setDisable(false);
        banderaFinal = false;
        Boolean bandC = false;


        Platform.runLater(new Runnable()
        {
            @Override
            public void run()
            {
                label.setText("Resultado:0.0");
            }
        });

        for(int i = 10; i > 0; i--)
        {
            try
            {
                if(banderaFinal){
                    final String statusFinal = "Ganaste!!";
                    // Update the Label on the JavaFx Application Thread
                    Platform.runLater(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            label2.setText(statusFinal);

                        }
                    });
                    bandC = true;
                    break;
                }else{
                    // Get the Status
                    final String status = "Tiempo:"+i;
                    // Update the Label on the JavaFx Application Thread
                    Platform.runLater(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            label2.setText(status);
                        }
                    });
                }
                Thread.sleep(1000);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
        if(!bandC){
            final String status = "Perdiste!!";
            Platform.runLater(new Runnable()
            {
                @Override
                public void run()
                {
                    label2.setText(status);

                }
            });
        }
        botonIniciar.setDisable(false);
        botonCalcular.setDisable(true);
    }
    public GridPane addGrindPane (){
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(0,10,0,10));

        Text Unidades = new Text("Unidad 2");
        Unidades.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        grid.add(Unidades, 1,0);

        grid.add(label, 1,1);
        //Label label2 = new Label();
        grid.add(label2, 1,2);

        botonIniciar.setPrefSize(150,20);
        grid.add(botonIniciar,0,2);

        botonCalcular.setPrefSize(150,20);
        grid.add(botonCalcular,0,1);

        botonCalcular.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                System.out.println("lanzar");

                int dado1 = (int)(Math.random()*6+1);
                int dado2 = (int)(Math.random()*6+1);

                int suma = dado1 + dado2;
                label.setText("Resultado:" + Double.toString(suma));
                if(suma == 7){
                   banderaFinal = true;
                }
                System.out.println(dado1);
                System.out.println(dado2);
            }
        });
        return grid;
    }

    public static void main(String[] args) {
        launch(args);
    }
}

