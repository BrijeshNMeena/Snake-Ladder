package com.example.snakeandladder;

import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class Player {
    private Circle coin;
    private int currentPosition;
    private String name;

    private static Board gameBoard = new Board();

    public Player(int tileSize, Color coinColor,  String name) {
        coin = new Circle(tileSize/2);
        coin.setFill(coinColor);
        currentPosition = 0;
        movePlayer(1);
        this.name = name;
    }

    public void movePlayer(int diceValue) {
        if(currentPosition+diceValue <= 100) {
            currentPosition += diceValue;
//            int x = gameBoard.getXCoordinates(currentPosition);
//            int y = gameBoard.getYCoordinates(currentPosition);
//            coin.setTranslateX(x);
//            coin.setTranslateY(y);
            TranslateTransition firstAnimation = transitionAnimation(diceValue), secondAnimation = null;
            int newPosition = gameBoard.getNewPosition(currentPosition);
            if(newPosition != -1 && newPosition != currentPosition) {
                currentPosition = newPosition;
                secondAnimation = transitionAnimation(5);
            }

            if(secondAnimation == null) {
                firstAnimation.play();
            }
            else {
                SequentialTransition sequentialTransition = new SequentialTransition(firstAnimation,
                        new PauseTransition(Duration.millis(300)), secondAnimation);
                sequentialTransition.play();
            }

        }
    }

    private TranslateTransition transitionAnimation(int diceValue) {
        TranslateTransition animate = new TranslateTransition(Duration.millis(200*diceValue), coin);
        animate.setToX(gameBoard.getXCoordinates(currentPosition));
        animate.setToY(gameBoard.getYCoordinates(currentPosition));
        animate.setAutoReverse(false);
        return animate;
    }

    public void setStartingPosition() {
        currentPosition = 0;
        movePlayer(1);
    }

    public Circle getCoin() {
        return coin;
    }

    public int getCurrentPosition() {
        return currentPosition;
    }

    public String getName() {
        return name;
    }
}
