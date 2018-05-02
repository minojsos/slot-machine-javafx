package com.minoj.slotmachine;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Payout extends Application {

    private volatile Stage stage;
    private ImageView viewSave;
    private Button btnSave;
    private Label lblSave;
    private TextArea txtPayout;

    /**
     * Constructor for Payout
     * If stage object is not null, start the stage
     */
    public Payout() {
        if(stage != null) {
            try {
                start(stage);
            } catch (Exception e) {
                System.err.println(e+" : An Error Occured!");
            }
        }
    }

    /**
     * Create a stage if the Stage object is null. This way only a single instance of Stage
     * will be created and therefore clicking on the payout button in the game screen
     * will only open one Payout window if window already open.
     * @return The created stage
     */
    public Stage create() {
        if(stage == null) {
            synchronized (Payout.class) {
                if(stage == null) {
                    stage = new Stage();
                }
            }
        }
        return stage;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        BorderPane root = new BorderPane();

        viewSave = new ImageView(new Image(getClass().getResource("images/save.png").toExternalForm()));
        lblSave = new Label();
        lblSave.setGraphic(viewSave);
        btnSave = new Button(" ",lblSave);

        HBox topBar = new HBox();
        topBar.setAlignment(Pos.CENTER);
        topBar.setPadding(new Insets(10,10,10,10));
        topBar.setSpacing(50);
        topBar.getChildren().add(btnSave);

        txtPayout = new TextArea();
        double threeReel = (double)1/6 * (double)1/6 * (double)1/6;
        double threeReelPayout = (threeReel*7)+(threeReel*6)+(threeReel*5)+(threeReel*4)+(threeReel*3)+(threeReel*2);

        double twoReel = (double)1/6 * (double)1/6;
        double twoReelPayout = (twoReel*7)+(twoReel*6)+(twoReel*5)+(twoReel*4)+(twoReel*3)+(twoReel*2);

        String payout = "" +
                "3 Reels Matched\n" +
                "1/6 * 1/6 * 1/6 = "+threeReel+"\n" +
                threeReel+"*7 + "+threeReel+"*6 + "+threeReel+"*5 + "+threeReel+"*4 + "+threeReel+"*3 + "+threeReel+"*2 = \n" +
                ((threeReel*7)+(threeReel*6)+(threeReel*5)+(threeReel*4)+(threeReel*3)+(threeReel*2))+"\n" + threeReelPayout + "\n" +
                "\n2 Reels Matched\n" +
                "1/6 * 1/6 = "+twoReel+"\n" +
                twoReel+"*7 + "+twoReel+"*6 + "+twoReel+"*5 + "+twoReel+"*4 + "+twoReel+"*3 + "+twoReel+"*2 = "+twoReelPayout+"\n" +
                "\nTotal Payout Percentae = "+threeReelPayout+" + "+twoReelPayout+" = "+(threeReelPayout+twoReelPayout)+" * 100% = "+((twoReelPayout+threeReelPayout)*100)+"%";

        txtPayout.setText(payout);

        txtPayout.setEditable(false);

        btnSave.setOnAction((new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    writeToFile(payout);
                } catch(IOException e) {
                    System.out.println("Some Error Occured! - "+e.getMessage());
                }
            }
        }));

        root.setTop(topBar);
        root.setCenter(txtPayout);
        root.getStylesheets().add(SlotMachine.class.getResource("style.css").toExternalForm());
        Scene scene = new Scene(root,1280,500);
        this.stage.setScene(scene);
        this.stage.setTitle("Slot Machine Payout Information");
        try {
            this.stage.getIcons().add(new Image(Statistics.class.getResource("images/icon.png").toExternalForm()));
        } catch(NullPointerException e) {
            System.out.print("Favicon Not Found : "+e.getMessage());
        }
        this.stage.setResizable(false);
        this.stage.show();

    }

    /**
     * Write to a file in the default location.
     * Create a file with the current date and time as the file name.
     * Show an Alert if the data has been successfully written to the newly created file.
     * @throws IOException
     */
    private void writeToFile(String payout) throws IOException {

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm-ss");
        LocalDateTime currentDateTime = LocalDateTime.now();

        FileWriter fw;

        fw = new FileWriter(dtf.format(currentDateTime)+"-Payout.txt");
        fw.write(payout);
        fw.close();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Successfully Saved");
        alert.setHeaderText(null);
        alert.setContentText("Payout Information saved to file "+dtf.format(currentDateTime)+".txt");

        alert.showAndWait();
    }

    public static void main(String[] args) { launch(args) ;}
}
