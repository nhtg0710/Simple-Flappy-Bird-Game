import bagel.*;
import bagel.Font;
import bagel.Image;
import bagel.Window;
import org.lwjgl.system.CallbackI;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.util.Random;

public class Pipe {
    private final Image pipe = new Image("res/pipe.png");
    private final int GAP = 168;
    private final int WIDTH = Window.getWidth();
    private final int HEIGHT = Window.getHeight();
    private final int UPPER_PIPE_END = (HEIGHT - GAP)/2;
    private final int LOWER_PIPE_START = UPPER_PIPE_END + GAP;
    private Point upperPipe;
    private Point lowerPipe;
    private final int SPEED = 5;
    private final int UPPER_PIPE_TOP_LEFT_Y = 0;
    private final int LOWER_PIPE_TOP_LEFT_Y = (HEIGHT + GAP)/2;

    public Pipe(){
        upperPipe = new Point (WIDTH, UPPER_PIPE_END - (int) pipe.getHeight()/2);
        lowerPipe = new Point (WIDTH, LOWER_PIPE_START + (int) pipe.getHeight()/2);
    }

    //below is just a series of getters

    public int getUPPER_PIPE_TOP_LEFT_Y() {
        return UPPER_PIPE_TOP_LEFT_Y;
    }

    public int getLOWER_PIPE_TOP_LEFT_Y() {
        return LOWER_PIPE_TOP_LEFT_Y;
    }

    public int getGAP() {
        return GAP;
    }

    public int getSPEED() {
        return SPEED;
    }

    public void update(){
        DrawOptions option = new DrawOptions();
        pipe.draw(upperPipe.x, upperPipe.y);
        pipe.draw(lowerPipe.x, lowerPipe.y,
                option.setRotation(Math.PI));

        // make the pipes move every update call
        upperPipe.x = upperPipe.x - SPEED;
        lowerPipe.x = lowerPipe.x - SPEED;
    }

    // to determine where the pipe is along the window
    public int getPipePosition(){
        return upperPipe.x;
    }
}
