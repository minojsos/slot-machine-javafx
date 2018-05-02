package com.minoj.slotmachine;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Statistics extends Application {

    private volatile Stage stage;
    private Button btnSave; // Button to call the writeToFile method
    private Label lblCreditsWon,lblCreditsBet, lblTotalSpin, lblAverage; // Labels to display the required data
    private double average; // Store the Average Number of Credits Won per game

    /**
     * Constructor for Statistics
     * If stage object is not null, start the stage.
     */
    public Statistics() {
        if(stage != null) {
            try {
                start(stage);
            } catch(Exception e) {
                System.err.println(e+" : An Error Occured!");
            }
        }
    }

    /**
     * Create a stage if the Stage object is null. This way only a single instance of Stage
     * will be created and therefore clicking on the statistics button in the game screen
     * will only open one Statistics window if window already open.
     * @return The created stage
     */
    public Stage create() {
        if(stage == null) {
            synchronized (Statistics.class) {
                if(stage == null) {
                    stage = new Stage();
                }
            }
        }
        return stage;
    }


    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Create the required layouts and add them to the stage.
     * Initialize the Labels and Buttons
     * @param primaryStage Stage
     */
    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();

        PieChart pieChart = new PieChart();
        pieChart.setData(getChartData());

        average = (double) (SlotMachine.getCreditsWon()-SlotMachine.getCreditsBet()) / SlotMachine.getTotalSpins();

        lblCreditsWon = new Label("Credits Won: "+String.valueOf(SlotMachine.getCreditsWon()));
        lblCreditsBet = new Label("Credits Bet: "+String.valueOf(SlotMachine.getCreditsBet()));
        lblTotalSpin = new Label("Total Spins: "+String.valueOf(SlotMachine.getTotalSpins()));
        lblAverage  = new Label("Average Credits Won: "+String.valueOf(average));
        btnSave = new Button("Save".toUpperCase());

        HBox stats = new HBox();
        stats.setAlignment(Pos.CENTER);
        stats.setPadding(new Insets(10,10,10,10));
        stats.setSpacing(50);
        stats.getChildren().addAll(lblCreditsBet,lblCreditsWon,lblTotalSpin);

        VBox data = new VBox();
        data.setAlignment(Pos.CENTER);
        data.setPadding(new Insets(10,10,10,10));
        data.getChildren().addAll(lblAverage,btnSave);
        data.setSpacing(50);
        btnSave.setOnAction((new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    writeToFile();
                } catch(IOException e) {
                    System.out.println("Some Error Occured! - "+e.getMessage());
                }
            }
        }));

        lblCreditsBet.getStyleClass().add("lblStats");
        lblCreditsWon.getStyleClass().add("lblStats");
        lblTotalSpin.getStyleClass().add("lblStats");
        lblAverage.getStyleClass().add("lblAverageCreds");
        btnSave.getStyleClass().add("btnSave");

        root.setTop(stats);
        root.setCenter(pieChart);
        root.setBottom(data);
        root.getStylesheets().add(SlotMachine.class.getResource("style.css").toExternalForm());
        Scene scene = new Scene(root,500,600);
        this.stage.setScene(scene);
        //scene.getStylesheets().add(Statistics.class.getResource("style.css").toExternalForm());
        this.stage.setTitle("Slot Machine Statistics");
        try {
            this.stage.getIcons().add(new Image(Statistics.class.getResource("images/icon.png").toExternalForm()));
        } catch(NullPointerException e) {
            System.out.print("Favicon Not Found : "+e.getMessage());
        }
        this.stage.setResizable(false);
        this.stage.show();
    }

    /**
     * Create an Object of Pie chart and add in the data to be displayed in the Piechart along
     * with the label for the data.
     * @return The list that contains the Piechart object - Two parts of the Piechart - Wins and Losses
     */
    private ObservableList<PieChart.Data> getChartData() {

        ObservableList<PieChart.Data> list = FXCollections.observableArrayList();
        list.addAll(
                new PieChart.Data("Wins: "+SlotMachine.getWins(), SlotMachine.getWins()),
                new PieChart.Data("Losses: "+SlotMachine.getLoss(), SlotMachine.getLoss())
        );
        return list;

    }

    /**
     * Write to a file in the default location.
     * Create a file with the current date and time as the file name.
     * Write the total winnings, losses, spins, credits bet, credits won and the average ctedits won per spin.
     * Show an Alert if the data has been successfully written to the newly created file.
     * @throws IOException
     */
    private void writeToFile() throws IOException {

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm-ss");
        LocalDateTime currentDateTime = LocalDateTime.now();

        FileWriter fw;

        fw = new FileWriter(dtf.format(currentDateTime)+".txt");
        fw.write("Winnings : "+SlotMachine.getWins()+System.getProperty("line.separator")+"Losses : "+SlotMachine.getLoss()+System.getProperty("line.separator")+"Credits Bet : "+SlotMachine.getCreditsBet()+System.getProperty("line.separator")+"Credits Won : "+SlotMachine.getCreditsWon()+System.getProperty("line.separator"));
        fw.write("Total Spins : "+SlotMachine.getTotalSpins()+System.getProperty("line.separator")+"Average Credits Won Per Spin : "+average);
        fw.close();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Successfully Saved");
        alert.setHeaderText(null);
        alert.setContentText("Statistics saved to file "+dtf.format(currentDateTime)+".txt");

        alert.showAndWait();
    }
}