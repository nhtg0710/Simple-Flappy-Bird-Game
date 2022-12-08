import bagel.*;
import bagel.Font;
import bagel.Image;
import bagel.Window;

import java.awt.*;
import java.security.Key;

/**
 * Skeleton Code for SWEN20003 Project 1, Semester 2, 2021
 *
 * Please filling your name below
 * @author: Truong Giang Hoang - 1166323
 */
public class ShadowFlap extends AbstractGame {
    private final Image background;
    private final Font font = new Font("res/slkscr.ttf", 48);
    private final String START_MESSAGE = "PRESS SPACE TO START";
    private final String OVER_MESSAGE = "GAME OVER";
    private final String FINAL_MESSAGE = "FINAL SCORE: ";
    private final String CONGRATS_MESSAGE = "CONGRATULATIONS!";
    private final String SCORE = "SCORE: ";
    private final int WIDTH = Window.getWidth();
    private final int HEIGHT = Window.getHeight();
    private int score = 0;
    private boolean isStart = false;
    private Pipe pipes;
    private Bird bird;
    private int frameCount = 0;
    private int frameSinceFalling = 0;

    // coordinates to print messages in correct positions
    private final Point START_MESSAGE_LOCATION =
            new Point((int) ((WIDTH - font.getWidth(START_MESSAGE))/2),
                    HEIGHT/2);
    private final Point OVER_MESSAGE_LOCATION =
            new Point((int) ((WIDTH - font.getWidth(OVER_MESSAGE))/2),
                    HEIGHT/2);
    private Point FINAL_MESSAGE_LOCATION =
            new Point((int) ((WIDTH - font.getWidth(FINAL_MESSAGE + score))/2),
                    75 + HEIGHT/2);
    private final Point CONGRATS_MESSAGE_LOCATION =
            new Point((int) ((WIDTH - font.getWidth(CONGRATS_MESSAGE))/2),
                    HEIGHT/2);
    private final Point SCORE_POSITION = new Point (100,100);

    public ShadowFlap() {
        super(1024, 768, "Flappy bird");
        background = new Image("res/background.png");
        pipes = new Pipe();
        bird = new Bird();
    }

    /**
     * The entry point for the program.
     */
    public static void main(String[] args) {
        ShadowFlap game = new ShadowFlap();
        game.run();
    }

    /**
     * Performs a state update.
     * allows the game to exit when the escape key is pressed.
     */

    @Override
    public void update(Input input) {
        drawBackground();
        boolean collision = false;
        boolean outOfBound = false;
        if (input.wasPressed(Keys.ESCAPE)){
            Window.close();
        } else {
            if (input.wasPressed(Keys.SPACE)) {
                // the bird flies up so we need to reset falling speed
                frameSinceFalling = 0;
                isStart = true;
            }
        }
        // print start message if player hasn't pressed SPACE to start the game
        if (!isStart){
            printStartScreen();
        } else {
            int birdHeight = bird.getBirdHeight();
            int pipePosition = pipes.getPipePosition();
            // check collision and out-of-bound so the program halts correctly
            collision = bird.checkCollision(birdHeight, pipePosition);
            outOfBound = bird.isOutOfBound(birdHeight);

            // game over if collided or out of bound
            if (collision || outOfBound){
                printScreen(false, score);
            } else {
                // when the pipes completely leave the screen, player wins
                // print out winning message
                if (score == 1){
                    printScreen(true, score);
                } else {
                    // bird is still in play
                    // continue updating the frames
                    frameCount += 1;
                    int speed = bird.calculateSpeed(frameSinceFalling);
                    frameSinceFalling += 1;
                    if (bird.hasScored(pipes.getPipePosition())){
                        score += 1;
                    }
                    pipes.update();
                    printCurrentScore(score);
                    bird.update(frameCount, speed);
                }
            }
        }
    }

    public void printStartScreen(){
        font.drawString(START_MESSAGE,
                START_MESSAGE_LOCATION.x, START_MESSAGE_LOCATION.y);
    }

    //print lose or win screen, depends
    public void printScreen(boolean gameOver, int score){
        if (gameOver){
            font.drawString(CONGRATS_MESSAGE,
                    CONGRATS_MESSAGE_LOCATION.x, CONGRATS_MESSAGE_LOCATION.y);
        } else {
        font.drawString(OVER_MESSAGE,
                OVER_MESSAGE_LOCATION.x, OVER_MESSAGE_LOCATION.y);
        }
        font.drawString(FINAL_MESSAGE + score,
                FINAL_MESSAGE_LOCATION.x, FINAL_MESSAGE_LOCATION.y);
    }

    public void printCurrentScore(int score){
        font.drawString(SCORE + score, SCORE_POSITION.x, SCORE_POSITION.y);
    }

    public void drawBackground(){
        background.drawFromTopLeft(0,0);
    }
}
