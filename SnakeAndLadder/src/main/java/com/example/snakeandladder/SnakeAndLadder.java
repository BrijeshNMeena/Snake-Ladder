package com.example.snakeandladder;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class SnakeAndLadder extends Application {

    public static final int tileSize = 40, width = 10, height = 10;
    public static final int buttonLine = height*tileSize + 50, infoLine = buttonLine-30;

    //private static Dice dice = new Dice();
    private Player playerOne, playerTwo;

    //private boolean gameStarted = false, playerOneTurn = false, playerTwoTurn = false;
    private Pane createContent() {
        Pane root = new Pane();
        root.setPrefSize(width*tileSize, height*tileSize + 100);

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                Tile tile = new Tile(tileSize);
                tile.setTranslateX(i*tileSize);
                tile.setTranslateY(j*tileSize);
                root.getChildren().add(tile);
            }
        }

        Image img = new Image("E:\\BNM\\Projects\\SnakeAndLadder\\src\\main\\java\\snakeladder.jpg");
        ImageView board = new ImageView();
        board.setImage(img);
        board.setFitWidth(width*tileSize);
        board.setFitHeight(height*tileSize);
       // board.setTranslateX(10);
        //board.setTranslateY(10);

        Button playerOneButton = new Button("Player One");
        Button playerTwoButton = new Button("Player Two");
        Button startButton = new Button("Start the Game");

        playerOneButton.setTranslateX(20);
        playerOneButton.setTranslateY(buttonLine);
        playerTwoButton.setTranslateX(310);
        playerTwoButton.setTranslateY(buttonLine);
        startButton.setTranslateX(170);
        startButton.setTranslateY(buttonLine);

        playerOneButton.setDisable(true);
        playerTwoButton.setDisable(true);

        Label playerOneLabel = new Label("-");
        Label playerTwoLabel = new Label("-");
        Label diceLabel = new Label("-");

        playerOneLabel.setTranslateX(20);
        playerOneLabel.setTranslateY(infoLine);
        playerTwoLabel.setTranslateX(310);
        playerTwoLabel.setTranslateY(infoLine);
        diceLabel.setTranslateX(170);
        diceLabel.setTranslateY(infoLine);

        playerOne = new Player(tileSize, Color.WHITE, "Amit");
        playerTwo = new Player(tileSize - 5, Color.BLACK, "Aman");

        //Start button in action
        startButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                startButton.setText("Game started");
             //   gameStarted = true;
                diceLabel.setText("Roll the Dice");
                startButton.setDisable(true);
               // playerOneTurn = true;
                playerOneButton.setDisable(false);
                playerOneLabel.setText("Your turn : "+ playerOne.getName());
                playerOne.setStartingPosition();
                playerTwoLabel.setText("");
                playerTwo.setStartingPosition();

            }
        });

        //Players in Action
        playerOneButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                int diceValue = Dice.rollDice();
                diceLabel.setText("Dice value : " + diceValue);
                playerOne.movePlayer(diceValue);

                playerOneButton.setDisable(true);
                playerOneLabel.setText("");

                //winning condition
                if(playerOne.getCurrentPosition() == 100) {
                    diceLabel.setText(playerOne.getName() + " has won the game!");
                    startButton.setText("restart");
                    startButton.setDisable(false);
                    return;
                }

                playerTwoButton.setDisable(false);
                playerTwoLabel.setText("Your turn : " + playerTwo.getName());
            }
        });

        playerTwoButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                int diceValue = Dice.rollDice();
                diceLabel.setText("Dice value : " + diceValue);
                playerTwo.movePlayer(diceValue);

                playerTwoButton.setDisable(true);
                playerTwoLabel.setText("");

                //winning condition
                if(playerTwo.getCurrentPosition() == 100) {
                    diceLabel.setText(playerTwo.getName() + " has won the game!");
                    startButton.setText("restart");
                    startButton.setDisable(false);
                    return;
                }

                playerOneButton.setDisable(false);
                playerOneLabel.setText("Your turn : " + playerOne.getName());
            }
        });



        root.getChildren().addAll(board, playerOneButton, playerTwoButton, startButton, playerOneLabel, playerTwoLabel, diceLabel,
                                    playerOne.getCoin(), playerTwo.getCoin());

        //root.setPadding(new Insets(20));

        return root;
    }
    @Override
    public void start(Stage stage) throws IOException {
        Scene scene = new Scene(createContent());
        stage.setTitle("Snake & Ladder !");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}