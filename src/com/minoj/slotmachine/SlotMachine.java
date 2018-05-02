package com.minoj.slotmachine;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.util.List;

public class SlotMachine extends Application {

    private AnchorPane anchorPane; // Root Pane
    private HBox bottomTopPane, bottomBtmPane,centerPane; // Bottom Top Pane - All Buttons Bottom Bottom Pane - Contains Reset Button CenterPane - Contains the three Reels
    private VBox bottomSplit,centerSplit; // Bottom Split - Contains the Bottom Content [Buttons] and Center Split - Contains the Reels and Error Labels
    private GridPane topGrid; // Contains the Betting Area and the Credits Area

    //Buttons
    private Button btnAddCredit, btnBetOne, btnSpin, btnBetMax, btnStats, btnReset, btnPayout;

    //Labels - Credit Label, Bet Amount Label, Reel Image One, Two and Three, Error Label and Spin Information Label
    private Label lblCredits, lblBetAmt, lblImgOne, lblImgTwo, lblImgThree, lblError, lblSpinInfo;

    //ImageViewer - Contains the Image for Reel One, Two and THree
    private ImageView imageViewOne, imageViewTwo, imageViewThree;

    //Reels - Stores all the three Reels
    private Reel[] reels; // Store an instance of the Reels used in the Game

    //Statistics Window
    private Statistics s; // Store an instance of the Statistics Class

    //Payout Window
    private Payout p; // Store an instance of the Payout Class

    /*
     * amtBet - Store the Amount the User has chosen to Bet (MAX : 3)
     * Credit - Amount of Credits the user has (Initially : 10)
     * tempAmtBet - Store the Amount of Credits up for Bet when the Reels begin to Spin
     * winnings - Amount of credits the user has won by spinning the reels once.
     */
    private volatile int amtBet, credit, tempAmtBet, winnings;
    private volatile boolean isSpinning; // Store if all the Reels are spinning or Not (True - All Reels or Some spinning, False - None are Spinning)

    private static final int MAX_BET_AMT = 3; // Maximum Number of Credits per Bet
    private static final int MIN_CREDITS = 0; // Minimum Number of Credits in Credit Area to be able to bet
    private static final int INITIAL_CRED = 10; // Initialize Number of Credits
    private static final int NUM_OF_REELS = 3; // Number of Reels present in the game

    /*
     * wins - Store the Number of times the user has won
     * loss - Store the Number of times the user has lost
     * creditsWon - Number of Credits the user has won playing the game
     * creditsBet - Number of Credits the user has bet playing the game
     * totalSpins - Total Number of times the user has played the games (Spun the Reels)
     */
    private static int wins, loss, totalSpins, creditsWon, creditsBet;

    @Override
    public void start(Stage primaryStage) throws Exception {

        isSpinning = false;
        s = new Statistics();
        p = new Payout();

        credit = INITIAL_CRED;

        anchorPane = new AnchorPane();
        bottomTopPane = new HBox();
        bottomTopPane.setPadding(new Insets(15,12,15,12));
        bottomTopPane.setSpacing(50);
        bottomTopPane.setAlignment(Pos.CENTER);

        bottomSplit = new VBox();
        bottomSplit.setPadding(new Insets(15,12,15,12));
        bottomSplit.setSpacing(10);

        centerSplit = new VBox();
        centerSplit.setPadding(new Insets(15,12,15,12));
        centerSplit.setSpacing(10);

        centerPane = new HBox();
        centerPane.setPadding(new Insets(15,12,15,12));
        centerPane.setSpacing(50);

        btnAddCredit = new Button("Add Credit".toUpperCase());
        btnBetOne = new Button("Bet One".toUpperCase());
        btnSpin = new Button("Spin".toUpperCase());
        btnBetMax = new Button("Bet Max".toUpperCase());
        btnStats = new Button("Statistics".toUpperCase());
        btnReset = new Button("Reset".toUpperCase());
        btnPayout = new Button("Payout".toUpperCase());

        bottomBtmPane = new HBox();
        bottomBtmPane.setAlignment(Pos.CENTER);

        //Bet Amount Label
        lblBetAmt = new Label("Bet Amount: $"+amtBet);
        lblBetAmt.setAlignment(Pos.CENTER_LEFT);
        lblBetAmt.setMinSize(Label.USE_PREF_SIZE, Label.USE_PREF_SIZE);
        //Credits Available Label
        lblCredits = new Label("Credits: $"+credit);
        lblCredits.setAlignment(Pos.CENTER_RIGHT);
        lblCredits.setMinSize(Label.USE_PREF_SIZE, Label.USE_PREF_SIZE);

        lblError = new Label();
        lblSpinInfo = new Label();

        topGrid = new GridPane();
        topGrid.setHgap(320);
        topGrid.setVgap(50);
        topGrid.setPadding(new Insets(20,20,20,20));

        setReel();
        Styling();
        actions();
        addElements();

        Scene scene = new Scene(anchorPane,900,700);
        scene.getStylesheets().add(SlotMachine.class.getResource("style.css").toExternalForm());
        primaryStage.setScene(scene);
        //Set the Stage Size to the Size Of the Scene
        primaryStage.sizeToScene();
        primaryStage.setTitle("Slot Machine");

        try {
            primaryStage.getIcons().add(new Image(SlotMachine.class.getResource("images/icon.png").toExternalForm()));
        } catch(NullPointerException e) {
            System.out.print("Favicon Not Found : "+e.getMessage());
        }

        primaryStage.show();
        //Set the Minimum Height and Width of the Primary Stage to prevent the stage being made smaller
        primaryStage.setMinHeight(primaryStage.getHeight());
        primaryStage.setMinWidth(primaryStage.getWidth());
    }

    /**
     * Set the Elements on to the three Reels
     * The Image View contains the Image to be displayed on the Reel. The Image View is then
     * set to the Label.
     */
    private void setReel() {
        reels = new Reel[NUM_OF_REELS];
        reels[0] = new Reel();
        reels[1] = new Reel();
        reels[2] = new Reel();

        try {
            imageViewOne = new ImageView(new Image(getClass().getResource(reels[0].getSymbol(7).getImage()).toExternalForm()));
            imageViewTwo = new ImageView(new Image(getClass().getResource(reels[1].getSymbol(7).getImage()).toExternalForm()));
            imageViewThree = new ImageView(new Image(getClass().getResource(reels[2].getSymbol(7).getImage()).toExternalForm()));
        } catch (NullPointerException e) {
            System.err.println("Image Not Found!");
        }

        lblImgOne = new Label();
        lblImgTwo  = new Label();
        lblImgThree = new Label();

        lblImgOne.setGraphic(imageViewOne);
        lblImgTwo.setGraphic(imageViewTwo);
        lblImgThree.setGraphic(imageViewThree);

    }

    /**
     * Adding the Elements into the appropriate panes
     * Bet Amount Label and Credits Label are added into the top grid
     * The Center is split into two. The upper contains the three reels and the
     * lower contains the label to display the Error Message (lblError) and Game Message (lblSpinInfo)
     * The Bottom is Split into two where all the buttons are in the upper and Reset Button is in
     * the lower.
     */
    private void addElements() {
        topGrid.add(lblBetAmt,0,0);
        topGrid.add(lblCredits,2,0);

        centerSplit.getChildren().add(centerPane);
        centerSplit.getChildren().addAll(lblError,lblSpinInfo);
        centerPane.getChildren().addAll(lblImgOne,lblImgTwo,lblImgThree);

        bottomTopPane.getChildren().addAll(btnAddCredit,btnBetOne,btnSpin,btnBetMax,btnStats);
        bottomBtmPane.getChildren().addAll(btnReset,btnPayout);
        bottomSplit.getChildren().add(bottomTopPane);
        bottomSplit.getChildren().add(bottomBtmPane);

        VBox vBox = new VBox();
        vBox.getChildren().add(topGrid);
        vBox.getChildren().add(centerSplit);
        vBox.getChildren().add(bottomSplit);
        anchorPane.getChildren().add(vBox);
    }

    /**
     * The Classes containing the styling for every element is set to the
     * respective elements.
     */
    private void Styling() {

        anchorPane.getStyleClass().add("background");
        anchorPane.setStyle("-fx-text-fill: #fff");

        lblImgOne.getStyleClass().add("lblImg");
        lblImgTwo.getStyleClass().add("lblImg");
        lblImgThree.getStyleClass().add("lblImg");

        bottomSplit.getStyleClass().add("bottomSplit");
        bottomBtmPane.getStyleClass().add("background");
        bottomTopPane.getStyleClass().add("background");
        centerPane.getStyleClass().add("background");
        centerSplit.getStyleClass().add("background");
        topGrid.getStyleClass().add("topGrid");

        btnSpin.getStyleClass().add("btnSpin");
        btnReset.getStyleClass().add("btnReset");

        lblCredits.getStyleClass().add("lblTop");
        lblBetAmt.getStyleClass().add("lblTop");
        lblError.getStyleClass().add("lblError");
        lblSpinInfo.getStyleClass().add("lblSpinInfo");

        try {
            imageViewOne.setFitHeight(200);
            imageViewOne.setFitWidth(200);
            imageViewOne.setPreserveRatio(true);
            imageViewOne.fitHeightProperty().bind(centerPane.heightProperty());

            imageViewTwo.setFitHeight(200);
            imageViewTwo.setFitWidth(200);
            imageViewTwo.setPreserveRatio(true);
            imageViewTwo.fitHeightProperty().bind(centerPane.heightProperty());

            imageViewThree.setFitHeight(200);
            imageViewThree.setFitWidth(200);
            imageViewThree.setPreserveRatio(true);
            imageViewThree.fitHeightProperty().bind(centerPane.heightProperty());
        } catch (NullPointerException e) {
            System.err.println(e.getMessage()+" - Image View Not Yet Set!");
        }


        centerSplit.setAlignment(Pos.CENTER);
        centerPane.setAlignment(Pos.CENTER);

        anchorPane.widthProperty().addListener((observable, oldValue, newValue) -> {
            centerSplit.setMinWidth(newValue.doubleValue());
        });

        topGrid.setAlignment(Pos.CENTER);

    }

    /**
     * Action Listeners for the Buttons and Reel Labels
     */
    private void actions() {



        /*
         * Increase the Bet Amount by 1. Limit for Bet Amount is 3.
         * Reduce the Credit from the Credit Amount
         * Update the Bet Amount and Credit Amount Labels
         * If Insufficient Credits, display an error message. If Bet Limit Reached, display an error message
         */
        btnBetOne.setOnAction((new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                if(credit>MIN_CREDITS) {
                    amtBet += 1;
                    credit -= 1;
                    lblBetAmt.setText("Bet Amount: $" + amtBet);
                    lblCredits.setText("Credits: $" + credit);
                    lblError.setText("");
                    lblSpinInfo.setText("");
                } else {
                    lblError.setText("Insufficient Credits!");
                    lblSpinInfo.setText("");
                }
            }
        }));

        /*
         * Bet the Maximum amount of Credits. Maximum Amount of Credits Per Bet is Set to 3.
         * If the Bet Amount has reached the Limit - Error Displayed to the User
         * if Insufficient Credits - Error displayed to the User
         * If any amount is present in the Bet Amount, reassign it to Credits Amount,
         * Set Bet Amount to Maximum amount and deduct maximum amount from Credits Amount.
         * Update Bet Amount and Credit Amount Labels
         */
        btnBetMax.setOnAction((new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (credit >= MAX_BET_AMT) {
                    credit += amtBet;
                    amtBet = MAX_BET_AMT;
                    credit -= MAX_BET_AMT;
                    lblBetAmt.setText("Bet Amount: $" + amtBet);
                    lblCredits.setText("Credits: $" + credit);
                    lblError.setText("");
                    lblSpinInfo.setText("");
                    btnBetMax.setDisable(true);
                } else {
                    lblError.setText("Insufficient Credits!");
                    lblSpinInfo.setText("");
                }
            }
        }));

        /*
         * Add a Credit to your credits.
         * Any amount of Credits can be added
         * Credits Label is updated
         */
        btnAddCredit.setOnAction((new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                credit += 1;
                lblCredits.setText("Credits: $"+credit);
                lblError.setText("");
                lblSpinInfo.setText("");
            }
        }));

        /*
         * Reset the Bet Amount. Amount currently in the Betting Area is refunded
         * back to credits.
         * Bet Amount and Credits Label is updated
         */
        btnReset.setOnAction((new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(!isSpinning) {
                    credit += amtBet;
                    amtBet = 0;
                    lblBetAmt.setText("Bet Amount: $" + amtBet);
                    lblCredits.setText("Credits: $" + credit);
                    lblError.setText("");
                    lblSpinInfo.setText("");
                    //btnBetMax.setDisable(false);
                } else {
                    lblError.setText("Unable to Reset!");
                }
            }
        }));

        /*
         * The Reels begin to spin. Can only be executed if none of the reels are
         * already spinning. Stop Variables of the reel are set to false and isSpinning
         * is set to true to indicate that all the reels are currently spinning
         * Total Number of Spins is incremented. The Current Bet Amount is Stored in a temporary
         * variable and Bet Amount is updated to 0.
         */
        btnSpin.setOnAction((new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(!isSpinning) {
                        if (amtBet > 0) {
                            reels[0].setStop(false);
                            reels[1].setStop(false);
                            reels[2].setStop(false);
                            isSpinning = true;
                            totalSpins++;
                            tempAmtBet = amtBet;
                            amtBet = 0;
                            lblError.setText("");
                            lblSpinInfo.setText("");
                            lblBetAmt.setText("Bet Amount: $" + amtBet);
                            reels();
                        } else {
                            lblSpinInfo.setText("");
                            lblError.setText("Please Add Credits to Bet!");
                        }
                } else {
                    lblSpinInfo.setText("");
                    lblError.setText("Already Spinning!");
                }
            }
        }));

        /*
         * Statistics window is opened if the Spin button was clicked at least once.
         * Error Message displayed to the user if the game wasn't played for at least once.
         */
        btnStats.setOnAction((new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(totalSpins != 0 && !isSpinning) {
                    try {
                        s.start(s.create());
                    } catch (Exception e) {
                        System.out.println(e.getMessage()+" : An Error Occurred!");
                    }
                } else {
                    lblError.setText("No statistics to show!");
                }
            }
        }));

        btnPayout.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(!isSpinning) {
                    try {
                        System.out.println("HERE!");
                        p.start(p.create());
                    } catch (Exception e) {
                        System.out.println(e.getMessage() + " : An Error Occurred!");
                    }
                } else {
                    lblError.setText("Stop Reels!");
                }
            }
        });


        /*
         * When Label containing the Image for the First Reel is clicked the Stop variable of
         * Reel one is set to True to indicate Reel one has stopped. If others are also stopped,
         * the program stores that all reels have stopped spinning. - isSpinning = false;
         */
        lblImgOne.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                reels[0].setStop(true);
                if(reels[0].isStop() && reels[1].isStop() && reels[2].isStop()) {
                    isSpinning = false;
                }
            }
        });

        /*
         * When Label containing the Image for the Second Reel is clicked the Stop variable of
         * Reel two is set to True to indicate Reel two has stopped. If others are also stopped,
         * the program stores that all reels have stopped spinning. - isSpinning = false;
         */
        lblImgTwo.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                reels[1].setStop(true);
                if(reels[0].isStop() && reels[1].isStop() && reels[2].isStop()) {
                    isSpinning = false;
                }
            }
        });

        /*
         * When Label containing the Image for the Third Reel is clicked the Stop variable of
         * Reel three is set to True to indicate Reel three has stopped. If others are also stopped,
         * the program stores that all reels have stopped spinning
         */
        lblImgThree.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                reels[2].setStop(true);
                if(reels[0].isStop() && reels[1].isStop() && reels[2].isStop()) {
                    isSpinning = false;
                }
            }
        });

    }

    /**
     * The threads required by each reel are created and are started
     * The run method contains the call to the spin() method.
     */
    private void reels() {
        Thread tOne = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    spin(reels[0], lblImgOne);
                } catch (NullPointerException e) {
                    System.err.println(e.getMessage()+"\nImage Not Found!");
                }
            }
        });

        Thread tTwo = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    spin(reels[1], lblImgTwo);
                } catch (NullPointerException e) {
                    System.err.println(e.getMessage()+"\nImage Not Found!");
                }
            }
        });

        Thread tThree = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    spin(reels[2], lblImgThree);
                } catch (NullPointerException e) {
                    System.err.println(e.getMessage()+"\nImage Not Found!");
                }
            }
        });
        tOne.setName("Reel One");
        tTwo.setName("Reel Two");
        tThree.setName("Reel Three");
        tOne.start();
        tTwo.start();
        tThree.start();
    }

    /**
     * The Reel Spinning simulation is performed by this method.
     * The List of Symbols are retrieved via the spin() method in the Reel class.
     * The reel continues to spin until the stop variable of the reel is set to true.
     * The For Loop is used to traverse the List of Symbols. The Symbols displayed in the Labels are
     * continuously replaced. The Value of the Symbol is retrieved and stored in the respective variable.
     * The Thread is put to sleep for 20ms
     * @param r - Reel Object (Reel One, Two or Three)
     * @param lbl - Label corresponding to the Reel.
     */
    private void spin(Reel r, Label lbl) {
        List<Symbol> l;

        while(!r.isStop()) {
            l = r.spin();

            for(int i = 0; i < l.size(); i++) {
                try {
                    ImageView finalImgV = new ImageView(new Image(getClass().getResource(l.get(i).getImage()).toExternalForm()));
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            lbl.setGraphic(finalImgV);
                        }
                    });

                    finalImgV.setFitHeight(200);
                    finalImgV.setFitWidth(200);
                } catch (NullPointerException e) {
                    System.err.println(e.getMessage()+"Image Not Found!");
                }

                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                r.setValue(l.get(i).getValue());
            }
        }


        if(!isSpinning) {
            winnings = 0;
            creditsBet += tempAmtBet;

            btnBetMax.setDisable(false);

            switch(win(reels)) {
                // If Two Reels Matched (0 & 1 || 0 & 2)
                case 0: {
                    winnings = reels[0].getValue() * tempAmtBet;
                    credit += winnings;
                    creditsWon += winnings;
                    wins++;

                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            lblCredits.setText("Credits: $"+credit);
                            lblError.setText("");
                            lblSpinInfo.setText("You've Won "+winnings+" Credits!");
                        }
                    });
                    break;
                }

                // If Two Reels Matched (1 && 2)
                case 1: {
                    winnings = reels[1].getValue() * tempAmtBet;
                    credit += winnings;
                    creditsWon += winnings;
                    wins++;

                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            lblCredits.setText("Credits: $"+credit);
                            lblError.setText("");
                            lblSpinInfo.setText("You've Won "+winnings+" Credits!");
                        }
                    });
                    break;
                }
                // If None of the Reels Matched
                default: {
                    loss++;

                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            lblSpinInfo.setText("Better luck next time!");
                            lblError.setText("");
                        }
                    });
                    break;
                }
            }
        }
    }

    /**
     * Decide if the User wins or not depending on the number of reels that have
     * matched.
     * @param reels Array Containing the Reels (3 Reels)
     * @return 0 - If All three reels match, 1 - If either One and Two or One and Three match
     * 2 - If Two and Three match, -1 - If none of the reels match
     */
    private int win(Reel[] reels) {

        if(reels[0].compareTo(reels[1]) == 0 || reels[0].compareTo(reels[2]) == 0) {
            return 0;
        } else if( reels[1].compareTo(reels[2]) == 0) {
            return 1;
        } else {
            return -1;
        }

    }

    /**
     * Retrieve the Total Number of Credits Won playing the game
     * @return Number of Credits Won playing the game
     */
    public static int getCreditsWon() {
        return creditsWon;
    }

    /**
     * Retrieve the Total Number of Credits Bet while playing the game
     * @return Number of Credits Bet in the game
     */
    public static int getCreditsBet() {
        return creditsBet;
    }

    /**
     * Obtain the total number of times the game has been won
     * @return Number of Times the game has beeAn won
     */
    public static int getWins() {
        return wins;
    }

    /**
     * Obtain the total number of times the game has been lost
     * @return Number of Times the game has been lost
     */
    public static int getLoss() {
        return loss;
    }

    /**
     * Retrieve the Total number of times the game has been played
     * @return Number of Times the game has been played
     */
    public static int getTotalSpins() {
        return totalSpins;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
